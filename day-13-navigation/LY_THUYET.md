# Day 13: Navigation - Điều hướng giữa các màn hình

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu cách **Navigation** hoạt động trong Compose
2. Thiết lập **NavController** và **NavHost**
3. Định nghĩa **Routes** và điều hướng giữa màn hình
4. Truyền **Arguments** giữa các màn hình
5. Tạo **Bottom Navigation** và **Drawer**

---

## PHẦN 1: THIẾT LẬP NAVIGATION

### 1.1 Thêm Dependency

Trong `build.gradle.kts` (app level):
```kotlin
dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.6")
}
```

### 1.2 Các thành phần chính

| Component | Chức năng |
|-----------|-----------|
| `NavController` | Quản lý navigation stack |
| `NavHost` | Container chứa các màn hình |
| `composable()` | Định nghĩa một màn hình |
| `navigate()` | Chuyển đến màn hình khác |

---

## PHẦN 2: NAVHOST VÀ ROUTES

### 2.1 Định nghĩa Routes

```kotlin
// Cách 1: Dùng object (Khuyên dùng)
object Routes {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
}

// Cách 2: Dùng sealed class (Type-safe)
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    object Profile : Screen("profile")
}
```

### 2.2 Tạo NavHost

```kotlin
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Routes.HOME  // Màn hình đầu tiên
    ) {
        // Định nghĩa các màn hình
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        
        composable(Routes.DETAIL) {
            DetailScreen(navController)
        }
        
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }
    }
}
```

### 2.3 Điều hướng cơ bản

```kotlin
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen", fontSize = 24.sp)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = {
            navController.navigate(Routes.DETAIL)
        }) {
            Text("Đi đến Detail")
        }
        
        Button(onClick = {
            navController.navigate(Routes.PROFILE)
        }) {
            Text("Đi đến Profile")
        }
    }
}
```

---

## PHẦN 3: TRUYỀN ARGUMENTS

### 3.1 Arguments trong Route

```kotlin
// Định nghĩa route với argument
composable(
    route = "detail/{productId}",
    arguments = listOf(
        navArgument("productId") { type = NavType.IntType }
    )
) { backStackEntry ->
    val productId = backStackEntry.arguments?.getInt("productId") ?: 0
    DetailScreen(productId = productId, navController = navController)
}

// Navigate với argument
navController.navigate("detail/123")
```

### 3.2 Optional Arguments

```kotlin
composable(
    route = "search?query={query}",
    arguments = listOf(
        navArgument("query") {
            type = NavType.StringType
            defaultValue = ""
            nullable = true
        }
    )
) { backStackEntry ->
    val query = backStackEntry.arguments?.getString("query") ?: ""
    SearchScreen(query)
}

// Navigate
navController.navigate("search?query=kotlin")
navController.navigate("search")  // Dùng default value
```

### 3.3 Nhiều Arguments

```kotlin
composable(
    route = "product/{id}/{category}",
    arguments = listOf(
        navArgument("id") { type = NavType.IntType },
        navArgument("category") { type = NavType.StringType }
    )
) { backStackEntry ->
    val id = backStackEntry.arguments?.getInt("id") ?: 0
    val category = backStackEntry.arguments?.getString("category") ?: ""
    ProductScreen(id, category)
}

navController.navigate("product/42/electronics")
```

---

## PHẦN 4: ĐIỀU HƯỚNG NÂNG CAO

### 4.1 popBackStack - Quay lại

```kotlin
// Quay lại màn hình trước
navController.popBackStack()

// Quay lại màn hình cụ thể
navController.popBackStack(Routes.HOME, inclusive = false)
```

### 4.2 navigate với options

```kotlin
navController.navigate(Routes.HOME) {
    // Xóa tất cả stack và về Home
    popUpTo(Routes.HOME) {
        inclusive = true
    }
    
    // Tránh tạo nhiều instance của cùng màn hình
    launchSingleTop = true
    
    // Restore state khi quay lại
    restoreState = true
}
```

### 4.3 Ví dụ: Login → Home (Xóa Login khỏi stack)

```kotlin
// Sau khi login thành công
navController.navigate(Routes.HOME) {
    popUpTo(Routes.LOGIN) {
        inclusive = true  // Xóa cả Login khỏi stack
    }
}
```

---

## PHẦN 5: BOTTOM NAVIGATION

### 5.1 Định nghĩa Bottom Nav Items

```kotlin
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Search,
    BottomNavItem.Cart,
    BottomNavItem.Profile
)
```

### 5.2 Tạo Bottom Navigation Bar

```kotlin
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Search.route) { SearchScreen() }
            composable(BottomNavItem.Cart.route) { CartScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen() }
        }
    }
}
```

---

## PHẦN 6: TOP APP BAR VỚI NAVIGATION

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, title: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, "Share")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Detail Content")
        }
    }
}
```

---

## PHẦN 7: VÍ DỤ HOÀN CHỈNH

```kotlin
// Routes
object AppRoutes {
    const val HOME = "home"
    const val PRODUCT_LIST = "products/{category}"
    const val PRODUCT_DETAIL = "product/{id}"
    
    fun productList(category: String) = "products/$category"
    fun productDetail(id: Int) = "product/$id"
}

// Navigation Graph
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = AppRoutes.HOME) {
        composable(AppRoutes.HOME) {
            HomeScreen(
                onCategoryClick = { category ->
                    navController.navigate(AppRoutes.productList(category))
                }
            )
        }
        
        composable(
            route = AppRoutes.PRODUCT_LIST,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ProductListScreen(
                category = category,
                onProductClick = { productId ->
                    navController.navigate(AppRoutes.productDetail(productId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(
            route = AppRoutes.PRODUCT_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("id") ?: 0
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
```

---

## 📝 TÓM TẮT

| Function | Chức năng |
|----------|-----------|
| `rememberNavController()` | Tạo NavController |
| `NavHost(navController, startDestination)` | Container chứa screens |
| `composable(route) { }` | Định nghĩa một màn hình |
| `navigate(route)` | Chuyển đến màn hình |
| `popBackStack()` | Quay lại |
| `navArgument(name)` | Định nghĩa argument |

---

## ➡️ NGÀY MAI
**Day 14: Scaffold & Material Components**
- Scaffold: TopBar, BottomBar, FAB, Drawer
- Material Design 3 Components
- Snackbar, Dialog
- ModalBottomSheet
