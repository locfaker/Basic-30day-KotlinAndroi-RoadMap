# Bài tập Day 11: LazyColumn và LazyRow

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Danh sách số (Dễ)
Tạo LazyColumn hiển thị số từ 1 đến 100:
- Mỗi số là một Text
- Padding 16dp
- Số chẵn màu xanh, số lẻ màu đỏ

---

### Bài 2: Danh sách liên lạc (Trung bình)
Tạo danh sách contact với data class:
```kotlin
data class Contact(val id: Int, val name: String, val phone: String)
```
- Mỗi contact là một Row: Avatar (chữ cái đầu) + Tên + SĐT
- Có Divider giữa các contact
- Có Header "Danh bạ" phía trên

---

### Bài 3: Categories Row (Trung bình)
Tạo LazyRow cho danh mục (như Shopee):
```
[Thời trang] [Điện tử] [Đồ gia dụng] [Sách] [Thể thao]
```
- Mỗi category là Card nhỏ với icon và text
- Padding và spacing hợp lý
- Click được (chỉ log ra console)

---

### Bài 4: Product Grid giả (Khó)
Tạo layout sản phẩm 2 cột (dùng LazyColumn + Row):
```
┌─────────┐ ┌─────────┐
│ SP 1    │ │ SP 2    │
└─────────┘ └─────────┘
┌─────────┐ ┌─────────┐
│ SP 3    │ │ SP 4    │
└─────────┘ └─────────┘
```
- Chia list thành chunks 2 phần tử
- Mỗi Row chứa 2 Card sản phẩm

---

### Bài 5: Chat Messages (Khó)
Tạo màn hình chat giống Messenger:
```kotlin
data class ChatMessage(
    val id: Int,
    val text: String,
    val isFromMe: Boolean,
    val time: String
)
```
- Tin của tôi: căn phải, màu xanh
- Tin người khác: căn trái, màu xám
- reverseLayout = true (tin mới ở dưới)
- Có thời gian nhỏ bên dưới mỗi tin

---

### Bài 6: Sticky Header Contacts (Nâng cao)
Tạo danh bạ với sticky header theo chữ cái:
```
[A] ← Header dính khi scroll
  An
  Anh
  Ánh
[B] ← Header dính khi scroll  
  Bình
  Bảo
```
- Sử dụng stickyHeader
- Header có background màu xám
- Group contacts theo chữ cái đầu

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao phải dùng LazyColumn thay vì Column với scroll?**
   > Gợi ý: Virtualization, memory, performance.

2. **Key trong items() có tác dụng gì?**
   > Gợi ý: Xác định item khi list thay đổi, animation, state preservation.

3. **Khác biệt giữa `items(list)` và `itemsIndexed(list)`?**
   > Gợi ý: Access index hay không.

4. **contentPadding khác gì với Modifier.padding?**
   > Gợi ý: Padding cho content vs padding cho container.

5. **reverseLayout dùng trong trường hợp nào?**
   > Gợi ý: Chat app, comment section (newest at bottom).

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Composable
fun NumberList() {
    LazyColumn {
        items(100) { index ->
            val number = index + 1
            Text(
                text = "Số $number",
                color = if (number % 2 == 0) Color.Blue else Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

**Bài 2:**
```kotlin
@Composable
fun ContactList(contacts: List<Contact>) {
    LazyColumn {
        item {
            Text("Danh bạ", fontWeight = FontWeight.Bold, 
                 modifier = Modifier.padding(16.dp))
        }
        
        itemsIndexed(contacts) { index, contact ->
            ContactItem(contact)
            if (index < contacts.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}
```

**Bài 3:**
```kotlin
@Composable
fun CategoryRow() {
    val categories = listOf("Thời trang", "Điện tử", "Gia dụng", "Sách", "Thể thao")
    
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            Card(onClick = { println("Clicked: $category") }) {
                Text(category, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
```

**Bài 4:**
```kotlin
@Composable
fun ProductGrid(products: List<Product>) {
    LazyColumn {
        items(products.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { product ->
                    ProductCard(product, modifier = Modifier.weight(1f))
                }
                // Nếu row chỉ có 1 item, thêm spacer
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
```

**Bài 5:**
```kotlin
@Composable
fun ChatScreen(messages: List<ChatMessage>) {
    LazyColumn(
        reverseLayout = true,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(messages, key = { it.id }) { message ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.isFromMe) 
                    Arrangement.End else Arrangement.Start
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (message.isFromMe) Color.Blue else Color.LightGray
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(message.text, color = if (message.isFromMe) Color.White else Color.Black)
                        Text(message.time, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}
```
