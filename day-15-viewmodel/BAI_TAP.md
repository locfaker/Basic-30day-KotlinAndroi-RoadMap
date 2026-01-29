# Bài tập Day 15: ViewModel

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Counter ViewModel (Dễ)
Viết CounterViewModel với:
- State: count (bắt đầu từ 0)
- Actions: increment(), decrement(), reset()
- Giới hạn: 0 <= count <= 100

Tạo CounterScreen sử dụng ViewModel này.

---

### Bài 2: Toggle Theme ViewModel (Dễ)
Viết ThemeViewModel với:
- State: isDarkMode (boolean)
- Action: toggleTheme()

Tạo SettingsScreen hiển thị Switch và thay đổi background theo theme.

---

### Bài 3: Todo ViewModel (Trung bình)
Viết TodoViewModel với:

```kotlin
data class Todo(val id: Int, val text: String, val completed: Boolean)

data class TodoUiState(
    val todos: List<Todo>,
    val inputText: String
)
```

Actions:
- updateInput(text)
- addTodo()
- toggleTodo(id)
- deleteTodo(id)

Tạo TodoScreen hoàn chỉnh.

---

### Bài 4: Shopping Cart ViewModel (Trung bình)
Viết CartViewModel với:

```kotlin
data class CartItem(val id: Int, val name: String, val price: Int, val quantity: Int)

data class CartUiState(
    val items: List<CartItem>,
    val promoCode: String
)
```

Actions:
- increaseQuantity(id)
- decreaseQuantity(id)
- removeItem(id)
- applyPromoCode(code)
- clearCart()

Computed:
- totalItems: Int
- subtotal: Int
- discount: Int (10% nếu có promoCode "SAVE10")
- total: Int

---

### Bài 5: User Profile ViewModel (Khó)
Viết ProfileViewModel với:

```kotlin
data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isEditing: Boolean = false,
    val isSaving: Boolean = false,
    val errors: Map<String, String> = emptyMap()
)
```

Actions:
- startEditing()
- cancelEditing()
- updateName(name)
- updateEmail(email)
- updatePhone(phone)
- saveProfile() // Validate trước khi "save"

Validation:
- Name: không được trống
- Email: phải chứa @
- Phone: chỉ số, 10 ký tự

---

### Bài 6: Notes App ViewModel (Nâng cao)
Viết NotesViewModel cho app ghi chú:

```kotlin
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long,
    val isPinned: Boolean = false
)

data class NotesUiState(
    val notes: List<Note>,
    val searchQuery: String,
    val selectedNoteId: Int? = null,
    val isAddingNote: Boolean = false
)
```

Features:
- Add/Edit/Delete note
- Pin/Unpin note
- Search notes
- Sort: Pinned first, then by date
- Select note để xem detail

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **ViewModel khác gì remember?**
   > Gợi ý: Lifecycle, scope, purpose.

2. **Tại sao nên dùng UI State class thay vì nhiều state riêng?**
   > Gợi ý: Single source of truth, atomic updates.

3. **private set trong ViewModel có ý nghĩa gì?**
   > Gợi ý: Encapsulation, chỉ ViewModel thay đổi state.

4. **ViewModel có bị tạo lại khi navigate back không?**
   > Gợi ý: Navigation scope vs Activity scope.

5. **Làm sao test ViewModel?**
   > Gợi ý: Unit test, không cần Android framework.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
class CounterViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set
    
    fun increment() { if (count < 100) count++ }
    fun decrement() { if (count > 0) count-- }
    fun reset() { count = 0 }
}
```

**Bài 3:**
```kotlin
class TodoViewModel : ViewModel() {
    var uiState by mutableStateOf(TodoUiState(emptyList(), ""))
        private set
    
    private var nextId = 0
    
    fun updateInput(text: String) {
        uiState = uiState.copy(inputText = text)
    }
    
    fun addTodo() {
        if (uiState.inputText.isBlank()) return
        val newTodo = Todo(nextId++, uiState.inputText, false)
        uiState = uiState.copy(
            todos = uiState.todos + newTodo,
            inputText = ""
        )
    }
    
    fun toggleTodo(id: Int) {
        uiState = uiState.copy(
            todos = uiState.todos.map {
                if (it.id == id) it.copy(completed = !it.completed) else it
            }
        )
    }
    
    fun deleteTodo(id: Int) {
        uiState = uiState.copy(todos = uiState.todos.filter { it.id != id })
    }
}
```

**Bài 5 - Validation:**
```kotlin
fun saveProfile() {
    val errors = mutableMapOf<String, String>()
    
    if (uiState.name.isBlank()) errors["name"] = "Tên không được trống"
    if (!uiState.email.contains("@")) errors["email"] = "Email không hợp lệ"
    if (!uiState.phone.all { it.isDigit() } || uiState.phone.length != 10) {
        errors["phone"] = "SĐT phải có 10 số"
    }
    
    if (errors.isEmpty()) {
        uiState = uiState.copy(isSaving = true)
        // Save logic...
        uiState = uiState.copy(isSaving = false, isEditing = false)
    } else {
        uiState = uiState.copy(errors = errors)
    }
}
```

**Bài 6 - Sorted Notes:**
```kotlin
val sortedNotes: List<Note>
    get() = uiState.notes
        .filter { it.title.contains(uiState.searchQuery, ignoreCase = true) }
        .sortedWith(compareBy({ !it.isPinned }, { -it.createdAt }))
```
