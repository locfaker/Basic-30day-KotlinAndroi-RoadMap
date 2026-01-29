# Bài tập Day 13: Navigation

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Navigation cơ bản (Dễ)
Tạo app với 3 màn hình:
- Home (có button đến Screen A và Screen B)
- Screen A (có button quay lại Home)
- Screen B (có button quay lại Home)

---

### Bài 2: Truyền Argument (Trung bình)
Tạo app với:
- HomeScreen: Danh sách 5 sản phẩm (dùng LazyColumn)
- DetailScreen: Nhận productId, hiển thị "Chi tiết sản phẩm #ID"

Click vào sản phẩm → navigate đến DetailScreen với ID tương ứng.

---

### Bài 3: Bottom Navigation (Trung bình)
Tạo app với Bottom Navigation gồm 4 tab:
- 🏠 Home
- 🔍 Search  
- 🛒 Cart
- 👤 Profile

Mỗi tab hiển thị tên tab ở giữa màn hình.

---

### Bài 4: Login Flow (Khó)
Tạo flow đăng nhập:
```
SplashScreen → LoginScreen → HomeScreen
                    ↓
              RegisterScreen
```
- SplashScreen: Delay 2s rồi đến Login
- LoginScreen: Form + button Login + link Register
- RegisterScreen: Form + button Register → quay lại Login
- Sau Login thành công: Xóa Login khỏi stack, vào Home
- Nhấn Back ở Home không quay lại Login

---

### Bài 5: Product App hoàn chỉnh (Khó)
Tạo E-commerce app với cấu trúc:
```
Bottom Nav:
├── Home (Danh sách categories)
│   └── ProductList (Sản phẩm theo category)
│       └── ProductDetail (Chi tiết SP)
├── Search
├── Cart
└── Profile
    └── Settings (Về About)
```

Features:
- Category truyền vào ProductList
- ProductId truyền vào ProductDetail
- Top bar có nút back khi không ở root screen
- Badge số lượng trên icon Cart

---

### Bài 6: Quiz App với Navigation (Nâng cao)
Tạo app Quiz:
```
HomeScreen → QuizScreen (câu 1/5) → ... → ResultScreen
```
- 5 câu hỏi, mỗi câu là một "page"
- Truyền question index qua argument
- Nút Tiếp theo / Quay lại
- ResultScreen nhận điểm số và hiển thị kết quả
- Nút "Chơi lại" về HomeScreen (xóa tất cả stack)

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **NavController là gì và tại sao cần dùng?**
   > Gợi ý: Quản lý back stack, navigation state.

2. **popUpTo và inclusive dùng khi nào?**
   > Gợi ý: Login flow, prevent going back.

3. **launchSingleTop giải quyết vấn đề gì?**
   > Gợi ý: Tránh tạo nhiều instance cùng màn hình.

4. **Làm sao check màn hình hiện tại để highlight bottom nav?**
   > Gợi ý: currentBackStackEntryAsState.

5. **Truyền object phức tạp qua navigation như thế nào?**
   > Gợi ý: Serialize to JSON hoặc chỉ truyền ID rồi fetch.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Composable
fun BasicNavApp() {
    val navController = rememberNavController()
    
    NavHost(navController, startDestination = "home") {
        composable("home") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Home Screen")
                Button(onClick = { navController.navigate("screenA") }) {
                    Text("Go to A")
                }
                Button(onClick = { navController.navigate("screenB") }) {
                    Text("Go to B")
                }
            }
        }
        composable("screenA") {
            Column {
                Text("Screen A")
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back to Home")
                }
            }
        }
        composable("screenB") {
            Column {
                Text("Screen B")
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back to Home")
                }
            }
        }
    }
}
```

**Bài 2:**
```kotlin
composable(
    route = "detail/{productId}",
    arguments = listOf(navArgument("productId") { type = NavType.IntType })
) { backStackEntry ->
    val productId = backStackEntry.arguments?.getInt("productId") ?: 0
    Text("Chi tiết sản phẩm #$productId")
}

// Navigate
navController.navigate("detail/$productId")
```

**Bài 4 - Login Flow:**
```kotlin
// Sau login thành công
navController.navigate("home") {
    popUpTo("login") { inclusive = true }
}

// Splash → Login
LaunchedEffect(Unit) {
    delay(2000)
    navController.navigate("login") {
        popUpTo("splash") { inclusive = true }
    }
}
```

**Bài 5 - Badge trên Cart:**
```kotlin
NavigationBarItem(
    icon = {
        BadgedBox(badge = {
            if (cartCount > 0) {
                Badge { Text("$cartCount") }
            }
        }) {
            Icon(Icons.Default.ShoppingCart, null)
        }
    },
    ...
)
```
