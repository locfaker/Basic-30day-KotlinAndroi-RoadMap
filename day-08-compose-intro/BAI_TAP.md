# Bài tập Day 08: Jetpack Compose Cơ bản

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Hello Compose (Dễ)
Tạo màn hình đơn giản với:
1. Một `Column` chiếm toàn màn hình
2. Căn giữa cả ngang và dọc
3. Hiển thị Text "Xin chào Jetpack Compose!" với font size 24sp
4. Bên dưới là Text "Day 08 - Bài tập" với màu xám

---

### Bài 2: Profile Card (Trung bình)
Tạo một Card thông tin cá nhân:
```
┌────────────────────────────────┐
│  ┌───┐                         │
│  │ M │  Minh Nguyen            │
│  └───┘  minh@gmail.com         │
│         SĐT: 0123456789        │
└────────────────────────────────┘
```
- Hình tròn bên trái (Box với CircleShape) chứa chữ cái đầu tên
- Bên phải là Column chứa tên (Bold), email, số điện thoại
- Card có nền màu nhạt, bo góc, padding 16dp

---

### Bài 3: Header Layout (Trung bình)
Tạo Header giống các App thực tế:
```
┌────────────────────────────────┐
│  ☰  Logo        🔔  👤        │
└────────────────────────────────┘
```
- Row chiếm full width
- Icon menu (dùng Text "☰")
- Text "Logo" ở giữa
- Icons thông báo và user ở bên phải
- Dùng `Arrangement.SpaceBetween` và `weight()`

---

### Bài 4: Thẻ sản phẩm (Khó)
Tạo Card sản phẩm cho App bán hàng:
```
┌────────────────────┐
│   ┌────────────┐   │
│   │   IMAGE    │   │
│   │  (Box)     │   │
│   └────────────┘   │
│  Tên sản phẩm      │
│  ⭐ 4.5 (120)      │
│  250.000đ          │
└────────────────────┘
```
- Box màu xám làm placeholder cho ảnh (height 150dp)
- Tên sản phẩm Bold
- Rating với icon sao (dùng emoji ⭐)
- Giá tiền màu đỏ

---

### Bài 5: Danh sách dọc (Khó)
Tạo danh sách 5 item trong Column:
```
┌────────────────────────────────┐
│ 1. Học Kotlin cơ bản       ✓  │
├────────────────────────────────┤
│ 2. Học Jetpack Compose     ✓  │
├────────────────────────────────┤
│ 3. Xây dựng App đầu tiên   ○  │
├────────────────────────────────┤
│ 4. Học MVVM                ○  │
├────────────────────────────────┤
│ 5. Publish lên Play Store  ○  │
└────────────────────────────────┘
```
- Mỗi item là một Row với số thứ tự, nội dung, trạng thái
- Item hoàn thành có màu xanh, chưa hoàn thành màu xám
- Có đường kẻ giữa các item

---

### Bài 6: Modifier Chain (Nâng cao)
Tạo một Box với các Modifier theo đúng thứ tự để tạo hiệu ứng:
1. Kích thước 200x100dp
2. Padding ngoài 16dp (bên ngoài nền)
3. Background màu xanh dương, bo góc 16dp
4. Padding trong 24dp (bên trong nền)
5. Border 2dp màu đen
6. Trong Box có Text "Modifier Order" căn giữa

Thử đổi thứ tự các Modifier và quan sát sự khác biệt!

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao Compose được gọi là "Declarative UI"?**
   > Gợi ý: So sánh với cách truyền thống (Imperative) - bạn phải chỉ dẫn từng bước vs chỉ mô tả kết quả muốn có.

2. **Hàm @Composable khác gì hàm thường?**
   > Gợi ý: Không return UI, chỉ gọi được từ @Composable khác, được Compose framework xử lý đặc biệt.

3. **Thứ tự Modifier có quan trọng không? Cho ví dụ.**
   > Gợi ý: `padding().background()` khác `background().padding()` như thế nào?

4. **Column và LazyColumn khác nhau như thế nào?**
   > Gợi ý: Hiệu năng khi danh sách có nhiều item.

5. **@Preview giúp ích gì trong quá trình phát triển?**
   > Gợi ý: Không cần chạy App, xem nhiều trạng thái cùng lúc.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Composable
fun HelloCompose() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Xin chào Jetpack Compose!",
            fontSize = 24.sp
        )
        Text(
            text = "Day 08 - Bài tập",
            color = Color.Gray
        )
    }
}
```

**Bài 2:**
```kotlin
@Composable
fun ProfileCard(name: String, email: String, phone: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Blue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(name.first().toString(), color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        // Info
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(name, fontWeight = FontWeight.Bold)
            Text(email, color = Color.Gray)
            Text("SĐT: $phone", color = Color.Gray)
        }
    }
}
```

**Bài 3:**
```kotlin
@Composable
fun AppHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("☰", fontSize = 24.sp)
        Text("Logo", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Row {
            Text("🔔", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text("👤", fontSize = 24.sp)
        }
    }
}
```

**Bài 5:**
```kotlin
data class TodoItem(val id: Int, val title: String, val completed: Boolean)

@Composable
fun TodoList() {
    val items = listOf(
        TodoItem(1, "Học Kotlin cơ bản", true),
        TodoItem(2, "Học Jetpack Compose", true),
        TodoItem(3, "Xây dựng App đầu tiên", false),
        TodoItem(4, "Học MVVM", false),
        TodoItem(5, "Publish lên Play Store", false)
    )
    
    Column {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "${item.id}. ${item.title}",
                    color = if (item.completed) Color.Green else Color.Gray
                )
                Text(if (item.completed) "✓" else "○")
            }
            Divider()
        }
    }
}
```
