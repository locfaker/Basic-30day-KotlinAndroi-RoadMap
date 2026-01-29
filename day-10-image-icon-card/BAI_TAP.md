# Bài tập Day 10: Image, Icon và Card

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Avatar với viền (Dễ)
Tạo avatar hình tròn:
- Dùng Box làm placeholder (vì chưa có ảnh)
- Kích thước 80x80dp
- Hình tròn (CircleShape)
- Viền 3dp màu xanh dương
- Chứa chữ cái đầu tên (VD: "M")

---

### Bài 2: Icon Gallery (Dễ)
Tạo một Row chứa 5 icon phổ biến:
- Home, Favorite, Search, Settings, Person
- Mỗi icon kích thước 32dp
- Khoảng cách đều nhau
- Mỗi icon màu khác nhau

---

### Bài 3: Product Card (Trung bình)
Tạo Card sản phẩm giống App bán hàng:
```
┌──────────────────┐
│    [HÌNH ẢNH]    │  ← Box placeholder 150dp height
│                  │
├──────────────────┤
│ Tên sản phẩm     │  ← Bold
│ ⭐ 4.5 (120 đánh giá)
│ 250.000đ  350.000đ │  ← Đỏ, Gạch ngang
│ [🛒 Thêm vào giỏ]│  ← Button
└──────────────────┘
```

---

### Bài 4: User Profile Card (Trung bình)
Tạo Card thông tin user:
```
┌────────────────────────────────────┐
│ ┌────┐                             │
│ │ 🟢 │  Nguyễn Văn A     [📩] [📞]│
│ └────┘  Senior Developer           │
│         📍 Hà Nội, Việt Nam        │
└────────────────────────────────────┘
```
- Avatar hình tròn với badge online (chấm xanh)
- Tên + chức danh
- Địa chỉ với icon location
- 2 icon buttons: Message, Phone

---

### Bài 5: News Card (Khó)
Tạo Card tin tức giống các App đọc báo:
```
┌────────────────────────────────────┐
│ [IMAGE]  │ Tiêu đề bài báo dài    │
│          │ có thể 2 dòng...        │
│          │ 📰 VnExpress • 2h ago   │
└────────────────────────────────────┘
```
- Ảnh bên trái (width 120dp)
- Tiêu đề bên phải (tối đa 2 dòng, overflow ellipsis)
- Nguồn tin + thời gian

---

### Bài 6: Music Player Card (Nâng cao)
```
┌────────────────────────────────────┐
│              [ALBUM ART]           │
│              200x200               │
│                                    │
│         Tên bài hát               │
│         Tên ca sĩ                 │
│                                    │
│    ◀◀   ▶/⏸   ▶▶    🔀   🔁     │
│                                    │
│  ━━━━━━━━━●━━━━━━━━━━━━━━━━━━    │
│  1:24            3:45             │
└────────────────────────────────────┘
```
- Album art hình vuông bo góc
- Tên bài + ca sĩ căn giữa
- Controls: Previous, Play/Pause, Next, Shuffle, Repeat
- Progress bar (có thể dùng Slider)

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao cần `contentDescription` cho Image và Icon?**
   > Gợi ý: Accessibility, Screen Reader.

2. **Sự khác nhau giữa `Image` và `AsyncImage`?**
   > Gợi ý: Local resource vs Network.

3. **ContentScale.Crop khác ContentScale.Fit như thế nào?**
   > Gợi ý: Cắt ảnh vs co ảnh.

4. **Card, ElevatedCard, OutlinedCard khác nhau thế nào?**
   > Gợi ý: Shadow, border.

5. **Khi nào dùng Icons.Filled vs Icons.Outlined?**
   > Gợi ý: Selected state vs unselected.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Composable
fun Avatar(letter: String) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Color.Blue)
            .border(3.dp, Color.Blue, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(letter, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}
```

**Bài 3:**
```kotlin
@Composable
fun ProductCard(name: String, rating: Float, reviews: Int, price: Int, oldPrice: Int) {
    Card(modifier = Modifier.width(180.dp)) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp).background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, null, modifier = Modifier.size(48.dp))
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(name, fontWeight = FontWeight.Bold)
                Row {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                    Text(" $rating ($reviews)")
                }
                Row {
                    Text("${price}đ", color = Color.Red, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text("${oldPrice}đ", textDecoration = TextDecoration.LineThrough, color = Color.Gray)
                }
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.ShoppingCart, null)
                    Text(" Thêm vào giỏ")
                }
            }
        }
    }
}
```

**Bài 4:**
```kotlin
@Composable
fun UserProfileCard() {
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box {
                Box(
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) { Text("A", color = Color.White) }
                Box(
                    modifier = Modifier.size(16.dp).background(Color.Green, CircleShape)
                        .border(2.dp, Color.White, CircleShape).align(Alignment.BottomEnd)
                )
            }
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text("Nguyễn Văn A", fontWeight = FontWeight.Bold)
                Text("Senior Developer", color = Color.Gray)
                Row { Icon(Icons.Default.LocationOn, null, Modifier.size(16.dp)); Text("Hà Nội") }
            }
            IconButton(onClick = {}) { Icon(Icons.Default.Email, null) }
            IconButton(onClick = {}) { Icon(Icons.Default.Phone, null) }
        }
    }
}
```
