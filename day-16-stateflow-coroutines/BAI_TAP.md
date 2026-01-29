# Bài tập Day 16: StateFlow & Coroutines

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Counter với delay (Dễ)
Tạo CounterViewModel sử dụng StateFlow:
- increment() delay 500ms trước khi tăng
- Hiển thị loading spinner khi đang delay
- Button disabled khi đang loading

---

### Bài 2: Fake API Call (Trung bình)
Tạo UserViewModel giả lập fetch users:
```kotlin
suspend fun fakeGetUsers(): List<User> {
    delay(2000)
    return listOf(User(1, "An"), User(2, "Bình"))
}
```
- Hiển thị loading khi đang fetch
- Hiển thị error nếu random fail (30% chance)
- Nút Retry khi có error

---

### Bài 3: Search với debounce (Trung bình)
Tạo SearchViewModel:
- Input search query
- Debounce 300ms (đợi user ngừng gõ)
- Filter products list
- Hiển thị "Đang tìm..." khi searching

---

### Bài 4: Multiple API calls (Khó)
Tạo DashboardViewModel load nhiều data:
```kotlin
data class DashboardUiState(
    val user: User? = null,
    val products: List<Product> = emptyList(),
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false
)
```
- Load song song 3 API calls
- Chỉ tắt loading khi TẤT CẢ hoàn thành
- Hiển thị từng phần khi có data

---

### Bài 5: Countdown Timer (Khó)
Tạo TimerViewModel:
```kotlin
data class TimerUiState(
    val timeLeft: Int = 60,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false
)
```
- Start/Pause/Reset timer
- Đếm ngược mỗi giây
- Thông báo khi kết thúc

---

### Bài 6: Pull to Refresh (Nâng cao)
Tạo NewsViewModel với pull to refresh:
- Load news khi khởi động
- Pull to refresh để reload
- Hiển thị refresh indicator
- Cache data cũ khi refresh fail

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao cần Coroutines thay vì Thread?**
   > Gợi ý: Lightweight, structured concurrency, cancellation.

2. **StateFlow khác LiveData như thế nào?**
   > Gợi ý: Kotlin-first, Flow operators, initial value required.

3. **viewModelScope tự động làm gì khi ViewModel bị huỷ?**
   > Gợi ý: Cancel tất cả coroutines đang chạy.

4. **Khi nào dùng Dispatchers.IO vs Default?**
   > Gợi ý: IO blocking vs CPU computation.

5. **collectAsState() hoạt động như thế nào?**
   > Gợi ý: Subscribe to Flow, convert to Compose State.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState = _uiState.asStateFlow()
    
    fun increment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(500)
            _uiState.update { it.copy(count = it.count + 1, isLoading = false) }
        }
    }
}
```

**Bài 2:**
```kotlin
fun loadUsers() {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        try {
            if (Random.nextFloat() < 0.3f) throw Exception("Random error")
            val users = fakeGetUsers()
            _uiState.update { it.copy(users = users, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message, isLoading = false) }
        }
    }
}
```

**Bài 3 - Debounce:**
```kotlin
private val _searchQuery = MutableStateFlow("")

init {
    viewModelScope.launch {
        _searchQuery
            .debounce(300)
            .collectLatest { query ->
                searchProducts(query)
            }
    }
}
```

**Bài 4 - Parallel calls:**
```kotlin
fun loadDashboard() {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        
        val userDeferred = async { fetchUser() }
        val productsDeferred = async { fetchProducts() }
        val notificationsDeferred = async { fetchNotifications() }
        
        _uiState.update {
            it.copy(
                user = userDeferred.await(),
                products = productsDeferred.await(),
                notifications = notificationsDeferred.await(),
                isLoading = false
            )
        }
    }
}
```

**Bài 5 - Timer:**
```kotlin
private var timerJob: Job? = null

fun startTimer() {
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
        _uiState.update { it.copy(isRunning = true) }
        while (_uiState.value.timeLeft > 0) {
            delay(1000)
            _uiState.update { it.copy(timeLeft = it.timeLeft - 1) }
        }
        _uiState.update { it.copy(isRunning = false, isFinished = true) }
    }
}

fun pauseTimer() {
    timerJob?.cancel()
    _uiState.update { it.copy(isRunning = false) }
}
```
