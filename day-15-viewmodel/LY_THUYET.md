# Day 15: ViewModel Basics - Quản lý UI State

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **ViewModel** là gì và tại sao cần dùng
2. Nắm vững lifecycle của ViewModel
3. Tạo và sử dụng ViewModel trong Compose
4. Kết hợp ViewModel với State

---

## PHẦN 1: VẤN ĐỀ KHÔNG DÙNG VIEWMODEL

### 1.1 Vấn đề: Mất state khi xoay màn hình

```kotlin
@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    
    Column {
        Text("Count: $count")
        Button(onClick = { count++ }) {
            Text("+1")
        }
    }
}
// Xoay màn hình → count reset về 0!
```

### 1.2 Vấn đề: Logic lẫn với UI

```kotlin
@Composable
fun UserListScreen() {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    // ❌ Logic fetch data lẫn trong UI
    LaunchedEffect(Unit) {
        try {
            users = api.getUsers()
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }
    
    // UI code...
}
```

---

## PHẦN 2: VIEWMODEL LÀ GÌ?

### 2.1 Định nghĩa

**ViewModel** là class đặc biệt được thiết kế để:
- **Lưu trữ và quản lý UI state**
- **Sống sót qua configuration changes** (xoay màn hình)
- **Tách biệt logic khỏi UI**

### 2.2 Lifecycle của ViewModel

```
Activity/Fragment tạo → ViewModel tạo
                         ↓
Activity xoay         → ViewModel VẪN TỒN TẠI
                         ↓
Activity/Fragment huỷ → ViewModel huỷ
```

```kotlin
// ViewModel tồn tại lâu hơn Activity/Composable
┌─────────────────────────────────────────────────┐
│               ViewModel Scope                    │
│  ┌─────────────┐     ┌─────────────┐           │
│  │ Activity 1  │ →→→ │ Activity 2  │           │
│  │ (Portrait)  │  ↑  │ (Landscape) │           │
│  └─────────────┘  │  └─────────────┘           │
│                   │                             │
│            Configuration Change                 │
└─────────────────────────────────────────────────┘
```

---

## PHẦN 3: THÊM DEPENDENCY

### 3.1 Trong build.gradle.kts (app)

```kotlin
dependencies {
    // ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Optional: LiveData (nếu cần)
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0")
}
```

---

## PHẦN 4: TẠO VIEWMODEL

### 4.1 ViewModel đơn giản

```kotlin
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    // State
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    
    // Actions
    fun increment() {
        _count.value++
    }
    
    fun decrement() {
        _count.value--
    }
    
    fun reset() {
        _count.value = 0
    }
}
```

### 4.2 ViewModel với mutableStateOf (Đơn giản hơn)

```kotlin
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class CounterViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set  // Chỉ ViewModel được sửa
    
    fun increment() { count++ }
    fun decrement() { count-- }
    fun reset() { count = 0 }
}
```

### 4.3 Sử dụng trong Composable

```kotlin
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel()  // Tự động tạo/lấy ViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Count: ${viewModel.count}",
            fontSize = 48.sp
        )
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.decrement() }) { Text("-") }
            Button(onClick = { viewModel.reset() }) { Text("Reset") }
            Button(onClick = { viewModel.increment() }) { Text("+") }
        }
    }
}
```

---

## PHẦN 5: UI STATE CLASS

### 5.1 Tại sao cần UI State class?

Khi screen có nhiều state → gom thành 1 data class.

```kotlin
// ❌ Nhiều state riêng lẻ - Khó quản lý
class UserViewModel : ViewModel() {
    var users by mutableStateOf(emptyList<User>())
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var searchQuery by mutableStateOf("")
}

// ✅ Một UI State class - Dễ quản lý
data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class UserViewModel : ViewModel() {
    var uiState by mutableStateOf(UserUiState())
        private set
        
    fun search(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }
    
    fun loadUsers() {
        uiState = uiState.copy(isLoading = true)
        // Fetch users...
        uiState = uiState.copy(users = result, isLoading = false)
    }
}
```

### 5.2 Ví dụ: Todo App với UI State

```kotlin
// UI State
data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val inputText: String = "",
    val filter: TodoFilter = TodoFilter.ALL
)

enum class TodoFilter { ALL, ACTIVE, COMPLETED }

data class Todo(
    val id: Int,
    val text: String,
    val completed: Boolean = false
)

// ViewModel
class TodoViewModel : ViewModel() {
    var uiState by mutableStateOf(TodoUiState())
        private set
    
    private var nextId = 0
    
    val filteredTodos: List<Todo>
        get() = when (uiState.filter) {
            TodoFilter.ALL -> uiState.todos
            TodoFilter.ACTIVE -> uiState.todos.filter { !it.completed }
            TodoFilter.COMPLETED -> uiState.todos.filter { it.completed }
        }
    
    fun updateInput(text: String) {
        uiState = uiState.copy(inputText = text)
    }
    
    fun addTodo() {
        if (uiState.inputText.isBlank()) return
        
        val newTodo = Todo(id = nextId++, text = uiState.inputText)
        uiState = uiState.copy(
            todos = uiState.todos + newTodo,
            inputText = ""
        )
    }
    
    fun toggleTodo(id: Int) {
        uiState = uiState.copy(
            todos = uiState.todos.map { todo ->
                if (todo.id == id) todo.copy(completed = !todo.completed)
                else todo
            }
        )
    }
    
    fun deleteTodo(id: Int) {
        uiState = uiState.copy(
            todos = uiState.todos.filter { it.id != id }
        )
    }
    
    fun setFilter(filter: TodoFilter) {
        uiState = uiState.copy(filter = filter)
    }
}
```

### 5.3 UI sử dụng ViewModel

```kotlin
@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val uiState = viewModel.uiState
    
    Column(modifier = Modifier.padding(16.dp)) {
        // Input
        Row {
            OutlinedTextField(
                value = uiState.inputText,
                onValueChange = { viewModel.updateInput(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Thêm todo...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.addTodo() }) {
                Text("Add")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Filter tabs
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TodoFilter.values().forEach { filter ->
                FilterChip(
                    selected = uiState.filter == filter,
                    onClick = { viewModel.setFilter(filter) },
                    label = { Text(filter.name) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Todo list
        LazyColumn {
            items(viewModel.filteredTodos, key = { it.id }) { todo ->
                TodoItem(
                    todo = todo,
                    onToggle = { viewModel.toggleTodo(todo.id) },
                    onDelete = { viewModel.deleteTodo(todo.id) }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.completed,
            onCheckedChange = { onToggle() }
        )
        Text(
            text = todo.text,
            modifier = Modifier.weight(1f),
            textDecoration = if (todo.completed) 
                TextDecoration.LineThrough else TextDecoration.None
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, "Delete")
        }
    }
}
```

---

## PHẦN 6: VIEWMODEL VỚI NAVIGATION

```kotlin
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            // ViewModel cho màn hình này
            val viewModel: ProductListViewModel = viewModel()
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navController.navigate("detail/$productId")
                }
            )
        }
        
        composable("detail/{id}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id") ?: ""
            // ViewModel cho màn hình này (khác với list)
            val viewModel: ProductDetailViewModel = viewModel()
            ProductDetailScreen(viewModel = viewModel, productId = productId)
        }
    }
}
```

---

## PHẦN 7: SO SÁNH CÁC CÁCH QUẢN LÝ STATE

| Cách | Sống qua xoay | Scope | Dùng khi |
|------|---------------|-------|----------|
| `remember` | ❌ | Composable | State tạm, animation |
| `rememberSaveable` | ✅ | Composable | Form input |
| `ViewModel` | ✅ | Screen | Business logic, API |

---

## 📝 TÓM TẮT

| Khái niệm | Mô tả |
|-----------|-------|
| ViewModel | Class quản lý UI state và logic |
| `viewModel()` | Function lấy/tạo ViewModel trong Compose |
| UI State | Data class chứa tất cả state của screen |
| Lifecycle | ViewModel sống qua configuration changes |

---

## ➡️ NGÀY MAI
**Day 16: StateFlow & Coroutines**
- Flow và StateFlow
- Coroutines trong ViewModel
- viewModelScope
- Collect Flow trong Compose
