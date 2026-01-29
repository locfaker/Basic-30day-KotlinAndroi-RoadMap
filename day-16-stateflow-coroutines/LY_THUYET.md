# Day 16: StateFlow & Coroutines - Xử lý bất đồng bộ

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **Coroutines** và tại sao cần dùng
2. Sử dụng **StateFlow** trong ViewModel
3. Nắm vững **viewModelScope**
4. Collect Flow trong Compose với `collectAsState`

---

## PHẦN 1: COROUTINES LÀ GÌ?

### 1.1 Vấn đề: Blocking Main Thread

```kotlin
// ❌ KHÔNG ĐƯỢC - Block UI thread
fun loadUsers() {
    val users = api.getUsers()  // Mất 3 giây
    // UI bị đông cứng trong 3 giây!
}
```

### 1.2 Giải pháp: Coroutines

Coroutines cho phép chạy code **bất đồng bộ** mà không block thread.

```kotlin
// ✅ ĐÚNG - Không block UI
fun loadUsers() {
    viewModelScope.launch {
        val users = api.getUsers()  // Chạy background
        // UI vẫn mượt mà
    }
}
```

### 1.3 Thêm Dependencies

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
}
```

---

## PHẦN 2: CƠ BẢN VỀ COROUTINES

### 2.1 launch - Fire and forget

```kotlin
viewModelScope.launch {
    // Code chạy bất đồng bộ
    delay(1000)  // Đợi 1 giây (không block)
    println("Done!")
}
```

### 2.2 suspend functions

```kotlin
// suspend = có thể tạm dừng và tiếp tục
suspend fun fetchUser(id: Int): User {
    delay(1000)  // Giả lập network delay
    return User(id, "User $id")
}

// Gọi suspend function từ coroutine
viewModelScope.launch {
    val user = fetchUser(1)
    println(user.name)
}
```

### 2.3 Dispatchers - Chạy trên thread nào?

```kotlin
viewModelScope.launch(Dispatchers.IO) {
    // IO operations: Network, Database
    val data = api.fetchData()
    
    withContext(Dispatchers.Main) {
        // Cập nhật UI (Main thread)
        updateUI(data)
    }
}
```

| Dispatcher | Dùng cho |
|------------|----------|
| `Main` | Cập nhật UI |
| `IO` | Network, Database, File |
| `Default` | CPU-intensive (sort, parse) |

---

## PHẦN 3: STATEFLOW

### 3.1 StateFlow là gì?

StateFlow là **observable state holder** - giữ một giá trị và thông báo khi thay đổi.

```kotlin
// Tạo StateFlow
private val _count = MutableStateFlow(0)
val count: StateFlow<Int> = _count.asStateFlow()

// Thay đổi giá trị
_count.value = 5

// Collect giá trị (trong Compose)
val count by viewModel.count.collectAsState()
```

### 3.2 MutableStateFlow vs StateFlow

```kotlin
class CounterViewModel : ViewModel() {
    // Private MutableStateFlow - có thể thay đổi
    private val _count = MutableStateFlow(0)
    
    // Public StateFlow - chỉ đọc
    val count: StateFlow<Int> = _count.asStateFlow()
    
    fun increment() {
        _count.value++
    }
}
```

### 3.3 So sánh mutableStateOf vs StateFlow

| | mutableStateOf | StateFlow |
|-|----------------|-----------|
| Dùng trong | Compose only | Anywhere |
| Thread-safe | Compose runtime | Yes |
| Collect | Tự động | collectAsState |
| Khi nào | UI state đơn giản | Complex, async |

---

## PHẦN 4: VIEWMODEL VỚI STATEFLOW

### 4.1 Ví dụ: User List với Loading

```kotlin
data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                delay(2000)  // Giả lập network
                val users = listOf(
                    User(1, "An"),
                    User(2, "Bình"),
                    User(3, "Cường")
                )
                _uiState.value = _uiState.value.copy(
                    users = users,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    
    fun refresh() {
        loadUsers()
    }
}
```

### 4.2 UI sử dụng StateFlow

```kotlin
@Composable
fun UserListScreen(viewModel: UserViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Lỗi: ${uiState.error}")
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Thử lại")
                    }
                }
            }
            else -> {
                LazyColumn {
                    items(uiState.users) { user ->
                        Text(user.name, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
```

---

## PHẦN 5: UPDATE STATEFLOW

### 5.1 update function (Thread-safe)

```kotlin
// ❌ Không thread-safe
_uiState.value = _uiState.value.copy(count = _uiState.value.count + 1)

// ✅ Thread-safe với update
_uiState.update { currentState ->
    currentState.copy(count = currentState.count + 1)
}
```

### 5.2 Ví dụ update

```kotlin
class CartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()
    
    fun addItem(item: CartItem) {
        _uiState.update { state ->
            state.copy(items = state.items + item)
        }
    }
    
    fun removeItem(id: Int) {
        _uiState.update { state ->
            state.copy(items = state.items.filter { it.id != id })
        }
    }
    
    fun updateQuantity(id: Int, quantity: Int) {
        _uiState.update { state ->
            state.copy(
                items = state.items.map { item ->
                    if (item.id == id) item.copy(quantity = quantity)
                    else item
                }
            )
        }
    }
}
```

---

## PHẦN 6: MULTIPLE FLOWS

### 6.1 combine - Kết hợp nhiều Flow

```kotlin
class SearchViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    
    // Combine 2 flows
    val filteredProducts: StateFlow<List<Product>> = combine(
        _searchQuery,
        _allProducts
    ) { query, products ->
        if (query.isBlank()) products
        else products.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    fun updateSearch(query: String) {
        _searchQuery.value = query
    }
}
```

---

## PHẦN 7: TRY-CATCH VÀ ERROR HANDLING

```kotlin
class DataViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DataUiState())
    val uiState = _uiState.asStateFlow()
    
    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val result = withContext(Dispatchers.IO) {
                    // Network/Database operation
                    repository.fetchData()
                }
                _uiState.update { it.copy(data = result, isLoading = false) }
                
            } catch (e: IOException) {
                _uiState.update { 
                    it.copy(error = "Lỗi mạng: ${e.message}", isLoading = false) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Lỗi: ${e.message}", isLoading = false) 
                }
            }
        }
    }
}
```

---

## 📝 TÓM TẮT

| Khái niệm | Mô tả |
|-----------|-------|
| Coroutine | Lightweight thread cho async |
| `launch` | Bắt đầu coroutine |
| `suspend` | Function có thể tạm dừng |
| `viewModelScope` | Scope tự động cancel khi ViewModel bị huỷ |
| `StateFlow` | Observable state holder |
| `collectAsState` | Collect Flow trong Compose |
| `update` | Thread-safe update StateFlow |

---

## ➡️ NGÀY MAI
**Day 17: Room Database - Setup**
- Room là gì?
- Entity, DAO, Database
- Thiết lập Room trong project
