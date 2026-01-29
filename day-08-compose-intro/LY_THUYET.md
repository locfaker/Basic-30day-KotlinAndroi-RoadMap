# Day 08: Giới thiệu Jetpack Compose - UI hiện đại cho Android

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **Jetpack Compose** là gì và tại sao nó thay thế XML
2. Nắm vững khái niệm **@Composable**
3. Sử dụng thành thạo các component cơ bản: `Text`, `Column`, `Row`, `Box`
4. Hiểu cách **Modifier** hoạt động
5. Biết cách dùng **Preview** để xem trước giao diện

---

## PHẦN 1: JETPACK COMPOSE LÀ GÌ?

### 1.1 Định nghĩa

Jetpack Compose là **bộ công cụ UI hiện đại** của Android, cho phép bạn xây dựng giao diện bằng **code Kotlin thuần túy**, không cần file XML.

### 1.2 So sánh: Cách cũ (XML) vs Cách mới (Compose)

**Cách cũ - XML + Kotlin tách biệt:**
```xml
<!-- layout.xml -->
<LinearLayout>
    <TextView android:id="@+id/tvHello" android:text="Hello" />
    <Button android:id="@+id/btnClick" android:text="Click me" />
</LinearLayout>
```
```kotlin
// Activity.kt
val tvHello = findViewById<TextView>(R.id.tvHello)
val btnClick = findViewById<Button>(R.id.btnClick)
btnClick.setOnClickListener { tvHello.text = "Clicked!" }
```

**Cách mới - Compose (Tất cả trong Kotlin):**
```kotlin
@Composable
fun MyScreen() {
    var text by remember { mutableStateOf("Hello") }
    
    Column {
        Text(text = text)
        Button(onClick = { text = "Clicked!" }) {
            Text("Click me")
        }
    }
}
```

### 1.3 Ưu điểm của Jetpack Compose

| Đặc điểm | XML cũ | Jetpack Compose |
|----------|--------|-----------------|
| Ngôn ngữ | XML + Kotlin | Kotlin only |
| Số file | 2 file (XML + KT) | 1 file |
| Cập nhật UI | Thủ công (findViewById) | Tự động |
| Preview | Cần chạy App | Xem ngay trong IDE |
| Tái sử dụng | Khó | Dễ (như gọi hàm) |
| Code | Dài dòng | Ngắn gọn |

---

## PHẦN 2: @COMPOSABLE - TIM MẠCH CỦA COMPOSE

### 2.1 @Composable là gì?

`@Composable` là annotation (chú thích) đánh dấu một hàm là **"hàm vẽ UI"**.

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Xin chào $name!")
}
```

### 2.2 Quy tắc quan trọng

1. **Hàm @Composable chỉ gọi được từ hàm @Composable khác**
```kotlin
@Composable
fun ParentScreen() {
    Greeting("Minh")  // OK - Gọi từ @Composable
}

fun normalFunction() {
    Greeting("Minh")  // LỖI! Không thể gọi từ hàm thường
}
```

2. **Tên hàm @Composable viết HOA chữ đầu (PascalCase)**
```kotlin
@Composable
fun UserCard() { ... }     // ĐÚNG

@Composable
fun userCard() { ... }     // SAI (theo convention)
```

3. **Không return giá trị thông thường**
- Hàm @Composable "trả về" UI, không phải data
- Nếu cần trả data, dùng hàm thường

### 2.3 Compose hoạt động như thế nào?

```
1. Bạn viết hàm @Composable mô tả UI
2. Compose đọc và tạo "cây UI" trong bộ nhớ
3. Compose vẽ cây đó lên màn hình
4. Khi data thay đổi → Compose tự động vẽ lại (Recomposition)
```

---

## PHẦN 3: CÁC COMPONENT CƠ BẢN

### 3.1 Text - Hiển thị văn bản

```kotlin
@Composable
fun TextExamples() {
    // Text đơn giản
    Text(text = "Hello World")
    
    // Text với style
    Text(
        text = "Tiêu đề lớn",
        fontSize = 24.sp,              // Kích thước
        fontWeight = FontWeight.Bold,  // Độ đậm
        color = Color.Blue             // Màu sắc
    )
    
    // Text nhiều dòng
    Text(
        text = "Đây là đoạn văn bản dài có thể xuống dòng khi cần thiết",
        maxLines = 2,                  // Tối đa 2 dòng
        overflow = TextOverflow.Ellipsis  // Thêm ... nếu quá dài
    )
}
```

### 3.2 Column - Xếp theo chiều DỌC

Giống `LinearLayout vertical` trong XML.

```kotlin
@Composable
fun ColumnExample() {
    Column {
        Text("Dòng 1")
        Text("Dòng 2")
        Text("Dòng 3")
    }
    
    // Column với căn chỉnh
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,      // Căn giữa dọc
        horizontalAlignment = Alignment.CenterHorizontally  // Căn giữa ngang
    ) {
        Text("Căn giữa màn hình")
    }
}
```

**Kết quả Column:**
```
┌─────────────┐
│ Dòng 1      │
│ Dòng 2      │
│ Dòng 3      │
└─────────────┘
```

### 3.3 Row - Xếp theo chiều NGANG

Giống `LinearLayout horizontal` trong XML.

```kotlin
@Composable
fun RowExample() {
    Row {
        Text("Trái")
        Text("Giữa")
        Text("Phải")
    }
    
    // Row với căn chỉnh
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,  // Cách đều 2 đầu
        verticalAlignment = Alignment.CenterVertically     // Căn giữa dọc
    ) {
        Text("Trái")
        Text("Phải")
    }
}
```

**Kết quả Row:**
```
┌──────────────────────┐
│ Trái   Giữa   Phải   │
└──────────────────────┘
```

### 3.4 Box - Xếp chồng lên nhau

Giống `FrameLayout` trong XML.

```kotlin
@Composable
fun BoxExample() {
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Phần tử sau đè lên phần tử trước
        Text("Nền")           // Ở dưới
        Text("Đè lên trên")   // Ở trên
    }
}
```

**Kết quả Box:**
```
┌─────────────┐
│   Đè lên    │  ← Phần tử sau đè lên phần tử trước
│    trên     │
└─────────────┘
```

### 3.5 Kết hợp Column, Row, Box

```kotlin
@Composable
fun CombinedLayout() {
    Column {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Logo")
            Text("Menu")
        }
        
        // Content
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text("Nội dung chính")
        }
        
        // Footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("© 2024 My App")
        }
    }
}
```

---

## PHẦN 4: MODIFIER - TRANG TRÍ VÀ ĐỊNH VỊ

### 4.1 Modifier là gì?

Modifier là cách bạn **tùy chỉnh** một component: kích thước, màu nền, padding, sự kiện click, v.v.

### 4.2 Các Modifier thường dùng

```kotlin
@Composable
fun ModifierExamples() {
    Text(
        text = "Hello",
        modifier = Modifier
            .fillMaxWidth()              // Chiếm hết chiều ngang
            .height(50.dp)               // Chiều cao 50dp
            .padding(16.dp)              // Padding 16dp tất cả các cạnh
            .padding(horizontal = 8.dp)  // Padding trái-phải 8dp
            .background(Color.Yellow)    // Nền vàng
            .clickable { /* xử lý click */ }
    )
}
```

### 4.3 Bảng Modifier phổ biến

| Modifier | Chức năng | Ví dụ |
|----------|-----------|-------|
| `.fillMaxWidth()` | Chiếm hết chiều ngang | |
| `.fillMaxHeight()` | Chiếm hết chiều dọc | |
| `.fillMaxSize()` | Chiếm hết cả 2 chiều | |
| `.size(100.dp)` | Kích thước cố định | |
| `.width(100.dp)` | Chiều rộng cố định | |
| `.height(50.dp)` | Chiều cao cố định | |
| `.padding(16.dp)` | Padding tất cả cạnh | |
| `.padding(start = 8.dp)` | Padding cạnh trái | |
| `.background(Color.Red)` | Màu nền | |
| `.border(1.dp, Color.Black)` | Viền | |
| `.clickable { }` | Xử lý click | |
| `.weight(1f)` | Trọng số (trong Row/Column) | |

### 4.4 THỨ TỰ MODIFIER RẤT QUAN TRỌNG!

```kotlin
// Padding TRƯỚC background → Padding nằm ngoài
Text(
    modifier = Modifier
        .padding(16.dp)
        .background(Color.Yellow)
)

// Background TRƯỚC padding → Padding nằm trong
Text(
    modifier = Modifier
        .background(Color.Yellow)
        .padding(16.dp)
)
```

---

## PHẦN 5: PREVIEW - XEM TRƯỚC GIAO DIỆN

### 5.1 @Preview là gì?

`@Preview` cho phép bạn **xem trước UI ngay trong Android Studio** mà không cần chạy App.

```kotlin
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}
```

### 5.2 Các tùy chọn @Preview

```kotlin
@Preview(
    name = "Light Mode",           // Tên hiển thị
    showBackground = true,         // Hiện nền trắng
    backgroundColor = 0xFFFFFFFF,  // Màu nền tùy chỉnh
    widthDp = 320,                 // Chiều rộng
    heightDp = 640,                // Chiều cao
    showSystemUi = true            // Hiện status bar, navigation bar
)
@Composable
fun MyScreenPreview() {
    MyScreen()
}
```

### 5.3 Nhiều Preview

```kotlin
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LightPreview() {
    MyTheme(darkTheme = false) {
        MyScreen()
    }
}

@Preview(name = "Dark Mode", showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyScreen()
    }
}
```

---

## PHẦN 6: VÍ DỤ THỰC TẾ - CARD THÔNG TIN

```kotlin
@Composable
fun UserCard(name: String, email: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar placeholder
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Blue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Thông tin
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = email,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    UserCard(name = "Nguyễn Văn A", email = "a@gmail.com")
}
```

---

## 📝 TÓM TẮT

| Khái niệm | Ý nghĩa |
|-----------|---------|
| `@Composable` | Đánh dấu hàm là UI component |
| `Text()` | Hiển thị văn bản |
| `Column` | Xếp dọc |
| `Row` | Xếp ngang |
| `Box` | Xếp chồng |
| `Modifier` | Tùy chỉnh component |
| `@Preview` | Xem trước trong IDE |

---

## ➡️ NGÀY MAI
**Day 09: Text & Styling chi tiết**
- Typography - Font chữ
- MaterialTheme
- Custom Text Styles
- Annotated Strings (Text có format)
