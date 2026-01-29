# Day 09: Button, TextField và Xử lý sự kiện

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Sử dụng thành thạo các loại **Button** trong Compose
2. Tạo **TextField** cho nhập liệu người dùng
3. Hiểu cách **xử lý sự kiện** (click, input)
4. Nắm vững **state** cơ bản với `remember` và `mutableStateOf`

---

## PHẦN 1: BUTTON - NÚT BẤM

### 1.1 Button cơ bản

```kotlin
@Composable
fun BasicButton() {
    Button(
        onClick = {
            // Code xử lý khi click
            println("Button clicked!")
        }
    ) {
        Text("Click me")  // Nội dung bên trong Button
    }
}
```

### 1.2 Các loại Button

```kotlin
@Composable
fun ButtonTypes() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 1. Button thông thường (có nền màu)
        Button(onClick = { }) {
            Text("Button")
        }
        
        // 2. OutlinedButton (chỉ có viền)
        OutlinedButton(onClick = { }) {
            Text("Outlined Button")
        }
        
        // 3. TextButton (không có nền, không viền)
        TextButton(onClick = { }) {
            Text("Text Button")
        }
        
        // 4. IconButton (chỉ icon)
        IconButton(onClick = { }) {
            Icon(Icons.Default.Favorite, contentDescription = "Like")
        }
        
        // 5. FloatingActionButton (FAB)
        FloatingActionButton(onClick = { }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}
```

### 1.3 Tùy chỉnh Button

```kotlin
@Composable
fun CustomButton() {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,           // Màu nền
            contentColor = Color.White            // Màu chữ/icon
        ),
        shape = RoundedCornerShape(12.dp),       // Bo góc
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp              // Bóng đổ
        ),
        enabled = true                           // Có thể click không
    ) {
        Icon(
            Icons.Default.Send,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Gửi tin nhắn", fontSize = 16.sp)
    }
}
```

### 1.4 Button với Loading State

```kotlin
@Composable
fun LoadingButton() {
    var isLoading by remember { mutableStateOf(false) }
    
    Button(
        onClick = {
            isLoading = true
            // Sau khi xong: isLoading = false
        },
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Đang xử lý...")
        } else {
            Text("Đăng nhập")
        }
    }
}
```

---

## PHẦN 2: TEXTFIELD - NHẬP LIỆU

### 2.1 TextField cơ bản

```kotlin
@Composable
fun BasicTextField() {
    var text by remember { mutableStateOf("") }
    
    TextField(
        value = text,                        // Giá trị hiện tại
        onValueChange = { newText ->         // Xử lý khi nhập
            text = newText
        },
        label = { Text("Nhập tên") },        // Label
        placeholder = { Text("VD: Nguyễn Văn A") }  // Placeholder
    )
}
```

### 2.2 OutlinedTextField (Phổ biến hơn)

```kotlin
@Composable
fun OutlinedTextFieldExample() {
    var email by remember { mutableStateOf("") }
    
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email") },
        placeholder = { Text("example@gmail.com") },
        leadingIcon = {                      // Icon đầu
            Icon(Icons.Default.Email, contentDescription = null)
        },
        trailingIcon = {                     // Icon cuối
            if (email.isNotEmpty()) {
                IconButton(onClick = { email = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = "Xóa")
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,                   // Chỉ 1 dòng
        shape = RoundedCornerShape(12.dp)
    )
}
```

### 2.3 TextField cho mật khẩu

```kotlin
@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Mật khẩu") },
        singleLine = true,
        visualTransformation = if (passwordVisible) 
            VisualTransformation.None 
        else 
            PasswordVisualTransformation(),      // Ẩn ký tự
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password  // Bàn phím password
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    if (passwordVisible) Icons.Default.Visibility
                    else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle password"
                )
            }
        }
    )
}
```

### 2.4 TextField với Validation

```kotlin
@Composable
fun ValidatedTextField() {
    var email by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    
    // Kiểm tra email hợp lệ
    val isValidEmail = email.contains("@") && email.contains(".")
    
    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                isError = it.isNotEmpty() && !it.contains("@")
            },
            label = { Text("Email") },
            isError = isError,                    // Hiển thị trạng thái lỗi
            supportingText = {                    // Text hỗ trợ/lỗi
                if (isError) {
                    Text("Email phải chứa @", color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        // Hiển thị trạng thái
        if (isValidEmail) {
            Text("✓ Email hợp lệ", color = Color.Green)
        }
    }
}
```

### 2.5 TextField cho số điện thoại

```kotlin
@Composable
fun PhoneTextField() {
    var phone by remember { mutableStateOf("") }
    
    OutlinedTextField(
        value = phone,
        onValueChange = { newValue ->
            // Chỉ cho phép nhập số và tối đa 10 ký tự
            if (newValue.all { it.isDigit() } && newValue.length <= 10) {
                phone = newValue
            }
        },
        label = { Text("Số điện thoại") },
        prefix = { Text("+84 ") },               // Prefix
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone     // Bàn phím số
        ),
        singleLine = true
    )
}
```

---

## PHẦN 3: STATE - TRẠNG THÁI

### 3.1 Tại sao cần State?

Trong Compose, UI được vẽ dựa trên **state**. Khi state thay đổi → UI tự động cập nhật.

```kotlin
// KHÔNG hoạt động - Biến thường không kích hoạt recomposition
var count = 0
Button(onClick = { count++ }) {
    Text("Count: $count")  // UI không cập nhật!
}

// HOẠT ĐỘNG - dùng mutableStateOf
var count by remember { mutableStateOf(0) }
Button(onClick = { count++ }) {
    Text("Count: $count")  // UI cập nhật!
}
```

### 3.2 remember và mutableStateOf

```kotlin
// Cú pháp đầy đủ
val count = remember { mutableStateOf(0) }
// Truy cập: count.value

// Cú pháp ngắn gọn với delegate "by"
var count by remember { mutableStateOf(0) }
// Truy cập trực tiếp: count
```

### 3.3 Ví dụ: Counter App

```kotlin
@Composable
fun CounterApp() {
    var count by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Đếm: $count",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { count-- }) {
                Text("-", fontSize = 24.sp)
            }
            
            Button(onClick = { count = 0 }) {
                Text("Reset")
            }
            
            Button(onClick = { count++ }) {
                Text("+", fontSize = 24.sp)
            }
        }
    }
}
```

---

## PHẦN 4: XỬ LÝ SỰ KIỆN NÂNG CAO

### 4.1 Xử lý nhiều loại click

```kotlin
@Composable
fun ClickHandling() {
    var message by remember { mutableStateOf("Chưa có tương tác") }
    
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.LightGray)
            .clickable { message = "Clicked!" }           // Click đơn
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { message = "Tap" },
                    onDoubleTap = { message = "Double Tap" },
                    onLongPress = { message = "Long Press" }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}
```

### 4.2 Form đăng nhập hoàn chỉnh

```kotlin
@Composable
fun LoginForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Đăng nhập",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = null },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = null },
            label = { Text("Mật khẩu") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        null
                    )
                }
            },
            visualTransformation = if (passwordVisible) 
                VisualTransformation.None 
            else 
                PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        // Error message
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = Color.Red)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Login button
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Vui lòng điền đầy đủ thông tin"
                } else if (!email.contains("@")) {
                    errorMessage = "Email không hợp lệ"
                } else {
                    isLoading = true
                    // Xử lý đăng nhập...
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Đăng nhập", fontSize = 16.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Register link
        TextButton(
            onClick = { /* Navigate to register */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Chưa có tài khoản? Đăng ký ngay")
        }
    }
}
```

---

## 📝 TÓM TẮT

| Component | Công dụng | Ví dụ |
|-----------|-----------|-------|
| `Button` | Nút bấm chính | `Button(onClick = {}) { Text("OK") }` |
| `OutlinedButton` | Nút viền | `OutlinedButton(onClick = {}) { Text("Cancel") }` |
| `TextButton` | Nút text | `TextButton(onClick = {}) { Text("Skip") }` |
| `TextField` | Nhập liệu nền đậm | `TextField(value, onValueChange)` |
| `OutlinedTextField` | Nhập liệu viền | `OutlinedTextField(value, onValueChange)` |
| `remember` | Giữ state qua recomposition | `remember { mutableStateOf(0) }` |
| `mutableStateOf` | Tạo state có thể thay đổi | `mutableStateOf("")` |

---

## ➡️ NGÀY MAI
**Day 10: Image, Icon và Card**
- Hiển thị hình ảnh
- Sử dụng Icons
- Tạo Card đẹp mắt
- Coil - Tải ảnh từ Internet
