# Day 12: State Management - Quản lý trạng thái trong Compose

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **State** là gì và tại sao quan trọng trong Compose
2. Thành thạo `remember` và `mutableStateOf`
3. Nắm vững khái niệm **State Hoisting**
4. Phân biệt **Stateful** vs **Stateless** Composables
5. Sử dụng `rememberSaveable` để lưu state khi xoay màn hình

---

## PHẦN 1: STATE LÀ GÌ?

### 1.1 Định nghĩa

**State** (trạng thái) là bất kỳ giá trị nào có thể thay đổi theo thời gian và ảnh hưởng đến UI.

**Ví dụ về State:**
- Nội dung TextField
- Checkbox đang checked hay không
- Số lượng items trong giỏ hàng
- User đã đăng nhập chưa
- Danh sách sản phẩm từ API

### 1.2 Compose là Declarative UI

```
State thay đổi → UI tự động vẽ lại (Recomposition)
```

```kotlin
// Khi count thay đổi, Compose tự động cập nhật Text
var count by remember { mutableStateOf(0) }
Text("Count: $count")
Button(onClick = { count++ }) { Text("+1") }
```

---

## PHẦN 2: REMEMBER VÀ MUTABLESTATEOF

### 2.1 Vấn đề: Biến thường không hoạt động

```kotlin
@Composable
fun BrokenCounter() {
    var count = 0  // ❌ Reset về 0 mỗi lần recomposition!
    
    Button(onClick = { count++ }) {
        Text("Count: $count")  // Luôn là 0
    }
}
```

### 2.2 Giải pháp: mutableStateOf + remember

```kotlin
@Composable
fun WorkingCounter() {
    var count by remember { mutableStateOf(0) }  // ✅ Giữ giá trị qua recomposition
    
    Button(onClick = { count++ }) {
        Text("Count: $count")  // Cập nhật đúng
    }
}
```

### 2.3 Phân tích cú pháp

```kotlin
var count by remember { mutableStateOf(0) }
```

| Phần | Ý nghĩa |
|------|---------|
| `mutableStateOf(0)` | Tạo state có giá trị ban đầu = 0 |
| `remember { }` | Giữ giá trị qua các lần recomposition |
| `by` | Delegate, cho phép dùng `count` trực tiếp thay vì `count.value` |

### 2.4 Hai cách viết

```kotlin
// Cách 1: Dùng delegate "by" (Khuyên dùng)
var count by remember { mutableStateOf(0) }
count = 5
println(count)  // 5

// Cách 2: Dùng .value
val count = remember { mutableStateOf(0) }
count.value = 5
println(count.value)  // 5
```

### 2.5 State với các kiểu dữ liệu khác

```kotlin
// String
var name by remember { mutableStateOf("") }

// Boolean
var isLoading by remember { mutableStateOf(false) }

// List (CHÚ Ý: phải dùng mutableStateListOf hoặc thay List mới)
val items = remember { mutableStateListOf("A", "B", "C") }
items.add("D")  // UI cập nhật

// Object
data class User(val name: String, val age: Int)
var user by remember { mutableStateOf(User("Minh", 25)) }
user = user.copy(age = 26)  // Phải tạo object mới
```

---

## PHẦN 3: STATE HOISTING (Nâng State lên)

### 3.1 Vấn đề: State bị kẹt bên trong Composable

```kotlin
// ❌ Counter tự quản lý state - khó test, khó tái sử dụng
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("$count")
    }
}
```

### 3.2 Giải pháp: State Hoisting

**Nâng state lên component cha**, truyền xuống qua parameters.

```kotlin
// ✅ Counter không giữ state - dễ test, dễ tái sử dụng
@Composable
fun Counter(
    count: Int,                    // State đọc từ cha
    onCountChange: (Int) -> Unit   // Callback để thay đổi state
) {
    Button(onClick = { onCountChange(count + 1) }) {
        Text("$count")
    }
}

// Cha giữ state
@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    
    Counter(
        count = count,
        onCountChange = { newCount -> count = newCount }
    )
}
```

### 3.3 Pattern chuẩn: value + onValueChange

```kotlin
// TextField chuẩn trong Compose
@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

// Sử dụng
@Composable  
fun FormScreen() {
    var email by remember { mutableStateOf("") }
    
    MyTextField(
        value = email,
        onValueChange = { email = it }
    )
}
```

### 3.4 Lợi ích của State Hoisting

1. **Single source of truth**: State chỉ ở một nơi
2. **Dễ test**: Có thể truyền state giả vào test
3. **Tái sử dụng**: Component không phụ thuộc vào nguồn state
4. **Chia sẻ state**: Nhiều component dùng chung 1 state

---

## PHẦN 4: STATEFUL VS STATELESS COMPOSABLES

### 4.1 Stateful Composable

```kotlin
// Stateful - Tự quản lý state
@Composable
fun StatefulCounter() {
    var count by remember { mutableStateOf(0) }  // State bên trong
    
    Column {
        Text("Count: $count")
        Button(onClick = { count++ }) {
            Text("+1")
        }
    }
}
```

### 4.2 Stateless Composable (Khuyên dùng)

```kotlin
// Stateless - Nhận state từ bên ngoài
@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit
) {
    Column {
        Text("Count: $count")
        Button(onClick = onIncrement) {
            Text("+1")
        }
    }
}

// Wrapper stateful để dùng stateless
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    StatelessCounter(
        count = count,
        onIncrement = { count++ }
    )
}
```

### 4.3 Khi nào dùng cái nào?

| Stateful | Stateless |
|----------|-----------|
| Screens cấp cao | Components tái sử dụng |
| Prototype nhanh | Production code |
| State đơn giản, cục bộ | Cần test, share state |

---

## PHẦN 5: REMEMBERSAVEABLE - GIỮ STATE KHI XOAY MÀN HÌNH

### 5.1 Vấn đề với remember

```kotlin
// ❌ Mất state khi xoay màn hình
var count by remember { mutableStateOf(0) }
```

Khi xoay màn hình, Activity bị destroy → Composable bị tạo lại → `remember` reset.

### 5.2 Giải pháp: rememberSaveable

```kotlin
import androidx.compose.runtime.saveable.rememberSaveable

// ✅ Giữ state khi xoay màn hình
var count by rememberSaveable { mutableStateOf(0) }
```

### 5.3 rememberSaveable với object phức tạp

```kotlin
// Cần implement Parcelable hoặc dùng Saver
@Parcelize
data class User(val name: String, val age: Int) : Parcelable

var user by rememberSaveable { mutableStateOf(User("Minh", 25)) }
```

### 5.4 Khi nào dùng cái nào?

| remember | rememberSaveable |
|----------|------------------|
| State tạm thời | State quan trọng |
| Animation state | Form input |
| Scroll position | Selected tab |
| Không cần giữ khi xoay | Cần giữ khi xoay |

---

## PHẦN 6: DERIVED STATE

### 6.1 derivedStateOf - Tính toán từ state khác

```kotlin
@Composable
fun ShoppingCart() {
    val items = remember { mutableStateListOf(100, 200, 300) }
    
    // Tính tổng chỉ khi items thay đổi
    val total by remember {
        derivedStateOf { items.sum() }
    }
    
    Column {
        items.forEachIndexed { index, price ->
            Text("Item ${index + 1}: ${price}đ")
        }
        Text("Tổng: ${total}đ", fontWeight = FontWeight.Bold)
    }
}
```

### 6.2 Khi nào dùng derivedStateOf?

- Khi cần tính toán từ state khác
- Tránh tính toán lại không cần thiết
- Ví dụ: filter list, sort, sum, validate form

---

## PHẦN 7: VÍ DỤ THỰC TẾ - LOGIN FORM

```kotlin
@Composable
fun LoginScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    // Derived state: form hợp lệ không?
    val isFormValid by remember {
        derivedStateOf {
            email.contains("@") && password.length >= 6
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Email field (Stateless)
        EmailField(
            value = email,
            onValueChange = { email = it; errorMessage = null }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Password field (Stateless)
        PasswordField(
            value = password,
            onValueChange = { password = it; errorMessage = null }
        )
        
        // Error message
        errorMessage?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Login button
        Button(
            onClick = {
                isLoading = true
                // Xử lý đăng nhập...
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Đăng nhập")
            }
        }
    }
}

// Stateless Email Field
@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

// Stateless Password Field
@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Mật khẩu") },
        visualTransformation = if (visible) VisualTransformation.None 
                               else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    if (visible) Icons.Default.Visibility 
                    else Icons.Default.VisibilityOff,
                    null
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}
```

---

## 📝 TÓM TẮT

| Khái niệm | Mô tả |
|-----------|-------|
| `mutableStateOf` | Tạo state có thể thay đổi |
| `remember` | Giữ state qua recomposition |
| `rememberSaveable` | Giữ state qua configuration change |
| State Hoisting | Nâng state lên component cha |
| Stateless | Component không giữ state |
| `derivedStateOf` | Tính toán từ state khác |

---

## ➡️ NGÀY MAI
**Day 13: Navigation - Điều hướng giữa các màn hình**
- NavController và NavHost
- Định nghĩa routes
- Truyền arguments giữa màn hình
- Bottom Navigation
