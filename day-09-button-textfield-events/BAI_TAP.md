# Bài tập Day 09: Button, TextField và Events

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Counter App (Dễ)
Tạo ứng dụng đếm số với:
- Text hiển thị số hiện tại (to, đậm)
- Button "-" để giảm
- Button "Reset" để về 0
- Button "+" để tăng
- Số không được âm (min = 0)

---

### Bài 2: Ẩn/Hiện Text (Dễ)
Tạo màn hình với:
- Một Text "Hello World"
- Một Button "Ẩn/Hiện"
- Click button thì Text ẩn/hiện (dùng state Boolean)

---

### Bài 3: Form Đăng ký (Trung bình)
Tạo form đăng ký với các trường:
1. TextField Họ tên (bắt buộc)
2. TextField Email (kiểm tra có @)
3. TextField Số điện thoại (chỉ cho nhập số, max 10 ký tự)
4. TextField Mật khẩu (ẩn ký tự, có nút hiện/ẩn)
5. TextField Xác nhận mật khẩu (kiểm tra khớp)
6. Button "Đăng ký"

Validation:
- Tất cả trường phải được điền
- Email phải có @
- Mật khẩu >= 6 ký tự
- Xác nhận mật khẩu phải khớp
- Hiển thị lỗi màu đỏ nếu không hợp lệ

---

### Bài 4: Đổi màu background (Trung bình)
Tạo màn hình với:
- 4 Button màu: Đỏ, Xanh lá, Xanh dương, Vàng
- Khi click button nào → màu nền thay đổi tương ứng
- Hiển thị tên màu hiện tại ở giữa

---

### Bài 5: Todo Input (Khó)
Tạo giao diện nhập việc cần làm:
```
┌─────────────────────────────────┐
│ ┌─────────────────────┐ ┌─────┐ │
│ │ Nhập công việc...   │ │ + │ │
│ └─────────────────────┘ └─────┘ │
│                                 │
│ • Học Kotlin                    │
│ • Làm bài tập                   │
│ • Đọc sách                      │
└─────────────────────────────────┘
```
- TextField để nhập
- Button "+" để thêm vào danh sách
- Danh sách hiển thị bên dưới
- Clear TextField sau khi thêm

---

### Bài 6: Form Chuyển tiền (Nâng cao)
Tạo form chuyển tiền ngân hàng:
1. TextField Số tài khoản (chỉ số, 10-16 ký tự)
2. TextField Tên người nhận (tự động viết hoa)
3. TextField Số tiền (format 1,000,000)
4. TextField Nội dung chuyển khoản
5. Row chọn loại chuyển: "Nhanh 24/7" hoặc "Thường"
6. Text hiển thị phí (Nhanh: 5,500đ, Thường: 0đ)
7. Text hiển thị tổng tiền = Số tiền + Phí
8. Button "Xác nhận chuyển tiền"

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao phải dùng `remember` khi khai báo state?**
   > Gợi ý: Recomposition và bảo toàn giá trị.

2. **`by` trong `var x by remember { mutableStateOf(0) }` làm gì?**
   > Gợi ý: Delegation, không cần `.value`.

3. **TextField value và onValueChange hoạt động thế nào?**
   > Gợi ý: Unidirectional data flow.

4. **Khi nào dùng Button, OutlinedButton, TextButton?**
   > Gợi ý: Primary action, Secondary action, Tertiary action.

5. **Làm sao để TextField chỉ cho nhập số?**
   > Gợi ý: Filter trong onValueChange hoặc KeyboardType.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Composable
fun CounterApp() {
    var count by remember { mutableStateOf(0) }
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$count", fontSize = 48.sp)
        Row {
            Button(onClick = { if (count > 0) count-- }) { Text("-") }
            Button(onClick = { count = 0 }) { Text("Reset") }
            Button(onClick = { count++ }) { Text("+") }
        }
    }
}
```

**Bài 2:**
```kotlin
@Composable
fun ToggleText() {
    var isVisible by remember { mutableStateOf(true) }
    
    Column {
        if (isVisible) {
            Text("Hello World")
        }
        Button(onClick = { isVisible = !isVisible }) {
            Text(if (isVisible) "Ẩn" else "Hiện")
        }
    }
}
```

**Bài 5:**
```kotlin
@Composable
fun TodoInput() {
    var text by remember { mutableStateOf("") }
    var todos by remember { mutableStateOf(listOf<String>()) }
    
    Column {
        Row {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (text.isNotBlank()) {
                    todos = todos + text
                    text = ""
                }
            }) { Text("+") }
        }
        
        todos.forEach { todo ->
            Text("• $todo")
        }
    }
}
```

**Bài 6 - Format số tiền:**
```kotlin
fun formatMoney(amount: String): String {
    val number = amount.filter { it.isDigit() }.toLongOrNull() ?: 0
    return String.format("%,d", number)
}
```
