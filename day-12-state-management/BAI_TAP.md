# Bài tập Day 12: State Management

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Counter với min/max (Dễ)
Tạo Counter với giới hạn:
- Min = 0, Max = 10
- Button "-" disabled khi = 0
- Button "+" disabled khi = 10
- Hiển thị "Min!" hoặc "Max!" khi đạt giới hạn

---

### Bài 2: Toggle Theme (Dễ)
Tạo switch Dark/Light mode:
- Switch để chuyển đổi
- Background đổi màu theo theme
- Text đổi màu theo theme
- Lưu bằng rememberSaveable

---

### Bài 3: Like Button với Animation (Trung bình)
Tạo nút Like như Facebook:
- Icon trái tim
- Click: đổi từ trống → đỏ
- Hiển thị số lượt like
- State được hoist lên

```kotlin
@Composable
fun LikeButton(
    isLiked: Boolean,
    likeCount: Int,
    onLikeClick: () -> Unit
)
```

---

### Bài 4: Shopping Cart (Trung bình)
Tạo giỏ hàng đơn giản:
```kotlin
data class CartItem(val id: Int, val name: String, val price: Int, val quantity: Int)
```
- Hiển thị danh sách items
- Mỗi item có nút +/- quantity
- Hiển thị tổng tiền (dùng derivedStateOf)
- Button "Xóa tất cả"

---

### Bài 5: Multi-step Form (Khó)
Tạo form đăng ký nhiều bước:
```
Step 1: Thông tin cá nhân (Tên, Email)
Step 2: Mật khẩu (Password, Confirm)
Step 3: Xác nhận (Hiển thị tất cả, Button Submit)
```
- Nút "Tiếp theo" và "Quay lại"
- Lưu state tất cả steps
- Validate trước khi next
- rememberSaveable cho form data

---

### Bài 6: Todo App với State Hoisting (Nâng cao)
Tạo Todo App hoàn chỉnh:
```kotlin
data class Todo(val id: Int, val text: String, val completed: Boolean)
```

Structure:
```
TodoScreen (Stateful - giữ list todos)
├── TodoInput (Stateless - nhập todo mới)
├── TodoList (Stateless - hiển thị list)
│   └── TodoItem (Stateless - mỗi item)
└── TodoStats (Stateless - thống kê)
```

Features:
- Thêm todo mới
- Toggle completed
- Xóa todo
- Filter: All / Active / Completed
- Hiển thị số completed / total

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao var thường không hoạt động trong Compose?**
   > Gợi ý: Recomposition reset biến.

2. **remember khác rememberSaveable như thế nào?**
   > Gợi ý: Configuration change (xoay màn hình).

3. **Tại sao nên dùng State Hoisting?**
   > Gợi ý: Single source of truth, testable, reusable.

4. **derivedStateOf giúp gì?**
   > Gợi ý: Avoid unnecessary recomputation.

5. **Khi nào Composable được recompose?**
   > Gợi ý: Khi state mà nó đọc thay đổi.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Composable
fun LimitedCounter() {
    var count by remember { mutableStateOf(0) }
    val min = 0
    val max = 10
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$count", fontSize = 48.sp)
        
        if (count == min) Text("Min!", color = Color.Red)
        if (count == max) Text("Max!", color = Color.Red)
        
        Row {
            Button(
                onClick = { count-- },
                enabled = count > min
            ) { Text("-") }
            
            Button(
                onClick = { count++ },
                enabled = count < max
            ) { Text("+") }
        }
    }
}
```

**Bài 3:**
```kotlin
@Composable
fun LikeButton(
    isLiked: Boolean,
    likeCount: Int,
    onLikeClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onLikeClick() }
    ) {
        Icon(
            if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Like",
            tint = if (isLiked) Color.Red else Color.Gray
        )
        Spacer(Modifier.width(4.dp))
        Text("$likeCount")
    }
}

// Sử dụng
@Composable
fun PostCard() {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(100) }
    
    LikeButton(
        isLiked = isLiked,
        likeCount = likeCount,
        onLikeClick = {
            isLiked = !isLiked
            likeCount += if (isLiked) 1 else -1
        }
    )
}
```

**Bài 4:**
```kotlin
@Composable
fun ShoppingCart() {
    val items = remember {
        mutableStateListOf(
            CartItem(1, "Áo", 200000, 1),
            CartItem(2, "Quần", 300000, 2)
        )
    }
    
    val total by remember {
        derivedStateOf {
            items.sumOf { it.price * it.quantity }
        }
    }
    
    Column {
        items.forEach { item ->
            CartItemRow(
                item = item,
                onQuantityChange = { newQty ->
                    val index = items.indexOfFirst { it.id == item.id }
                    items[index] = item.copy(quantity = newQty)
                }
            )
        }
        
        Text("Tổng: ${String.format("%,d", total)}đ", fontWeight = FontWeight.Bold)
        
        Button(onClick = { items.clear() }) {
            Text("Xóa tất cả")
        }
    }
}
```

**Bài 6 Structure:**
```kotlin
@Composable
fun TodoScreen() {
    var todos by remember { mutableStateOf(listOf<Todo>()) }
    var filter by remember { mutableStateOf("all") }
    
    val filteredTodos by remember(todos, filter) {
        derivedStateOf {
            when (filter) {
                "active" -> todos.filter { !it.completed }
                "completed" -> todos.filter { it.completed }
                else -> todos
            }
        }
    }
    
    Column {
        TodoInput(onAddTodo = { text ->
            todos = todos + Todo(todos.size, text, false)
        })
        
        TodoStats(
            total = todos.size,
            completed = todos.count { it.completed }
        )
        
        FilterTabs(
            selected = filter,
            onFilterChange = { filter = it }
        )
        
        TodoList(
            todos = filteredTodos,
            onToggle = { id ->
                todos = todos.map {
                    if (it.id == id) it.copy(completed = !it.completed) else it
                }
            },
            onDelete = { id ->
                todos = todos.filter { it.id != id }
            }
        )
    }
}
```
