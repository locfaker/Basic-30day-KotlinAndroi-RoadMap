# Day 11: LazyColumn và LazyRow - Danh sách hiệu năng cao

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu tại sao cần **Lazy** thay vì Column/Row thông thường
2. Sử dụng **LazyColumn** cho danh sách dọc
3. Sử dụng **LazyRow** cho danh sách ngang
4. Nắm vững `items`, `itemsIndexed`, `item`
5. Tối ưu hiệu năng với `key`

---

## PHẦN 1: TẠI SAO CẦN LAZY LAYOUTS?

### 1.1 Vấn đề với Column/Row thông thường

```kotlin
// ❌ KHÔNG NÊN - Tạo TẤT CẢ 1000 items cùng lúc
Column {
    repeat(1000) { index ->
        Text("Item $index")
    }
}
// → Lag, tốn bộ nhớ, có thể crash!
```

### 1.2 Giải pháp: Lazy Layouts

```kotlin
// ✅ NÊN DÙNG - Chỉ tạo items đang hiển thị trên màn hình
LazyColumn {
    items(1000) { index ->
        Text("Item $index")
    }
}
// → Mượt mà, tiết kiệm bộ nhớ
```

### 1.3 So sánh

| Đặc điểm | Column/Row | LazyColumn/LazyRow |
|----------|------------|-------------------|
| Số items | Tất cả cùng lúc | Chỉ items đang thấy |
| Bộ nhớ | Tốn nhiều | Tiết kiệm |
| Hiệu năng | Chậm với list dài | Luôn mượt |
| Scroll | Tự động | Tự động |
| Dùng khi | < 20 items | >= 20 items |

---

## PHẦN 2: LAZYCOLUMN - DANH SÁCH DỌC

### 2.1 Cú pháp cơ bản

```kotlin
@Composable
fun BasicLazyColumn() {
    LazyColumn {
        // Cách 1: items với số lượng
        items(100) { index ->
            Text("Item $index")
        }
    }
}
```

### 2.2 items với List

```kotlin
@Composable
fun LazyColumnWithList() {
    val names = listOf("An", "Bình", "Cường", "Dũng", "Em")
    
    LazyColumn {
        items(names) { name ->
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
```

### 2.3 itemsIndexed - Có cả index và item

```kotlin
@Composable
fun LazyColumnIndexed() {
    val products = listOf("Áo", "Quần", "Giày", "Mũ")
    
    LazyColumn {
        itemsIndexed(products) { index, product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("${index + 1}. ")
                Text(product)
            }
        }
    }
}
```

### 2.4 Kết hợp item và items

```kotlin
@Composable
fun MixedLazyColumn() {
    val products = listOf("Sản phẩm A", "Sản phẩm B", "Sản phẩm C")
    
    LazyColumn {
        // Header (1 item)
        item {
            Text(
                "DANH SÁCH SẢN PHẨM",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        
        // List items
        items(products) { product ->
            ProductItem(product)
        }
        
        // Footer (1 item)
        item {
            Text(
                "--- Hết danh sách ---",
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        }
    }
}
```

### 2.5 Modifier cho LazyColumn

```kotlin
@Composable
fun StyledLazyColumn() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),           // Padding cho content
        verticalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách giữa items
    ) {
        items(20) { index ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Item $index", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
```

---

## PHẦN 3: LAZYROW - DANH SÁCH NGANG

### 3.1 Cú pháp cơ bản

```kotlin
@Composable
fun BasicLazyRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(10) { index ->
            Card(
                modifier = Modifier.size(120.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Item $index")
                }
            }
        }
    }
}
```

### 3.2 Category Row (Như Netflix, Shopee)

```kotlin
@Composable
fun CategoryRow(
    title: String,
    items: List<String>
) {
    Column {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            TextButton(onClick = { }) {
                Text("Xem tất cả")
            }
        }
        
        // Horizontal list
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                ProductCard(item)
            }
        }
    }
}
```

---

## PHẦN 4: KEY - TỐI ƯU HIỆU NĂNG

### 4.1 Vấn đề không có key

Khi danh sách thay đổi (thêm/xóa/sắp xếp), Compose không biết item nào là item nào, phải vẽ lại tất cả.

### 4.2 Giải pháp: Thêm key

```kotlin
data class Product(
    val id: Int,
    val name: String
)

@Composable
fun OptimizedLazyColumn(products: List<Product>) {
    LazyColumn {
        items(
            items = products,
            key = { product -> product.id }  // ID duy nhất
        ) { product ->
            ProductItem(product)
        }
    }
}
```

### 4.3 Khi nào cần key?

- Danh sách có thể thêm/xóa items
- Danh sách có thể sắp xếp lại
- Items có animation
- Items có state riêng (checkbox, textfield)

---

## PHẦN 5: LAZYCOLUMN VỚI DIVIDER

```kotlin
@Composable
fun LazyColumnWithDivider() {
    val items = (1..20).toList()
    
    LazyColumn {
        items(items) { item ->
            Column {
                Text(
                    text = "Item $item",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                
                // Không thêm divider cho item cuối
                if (item != items.last()) {
                    HorizontalDivider()
                }
            }
        }
    }
}
```

---

## PHẦN 6: STICKY HEADERS

```kotlin
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderExample() {
    val contacts = mapOf(
        "A" to listOf("An", "Anh", "Ánh"),
        "B" to listOf("Bình", "Bảo"),
        "C" to listOf("Cường", "Chi", "Châu")
    )
    
    LazyColumn {
        contacts.forEach { (letter, names) ->
            // Sticky header
            stickyHeader {
                Text(
                    text = letter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Items
            items(names) { name ->
                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
```

---

## PHẦN 7: SCROLL STATE

### 7.1 Theo dõi vị trí scroll

```kotlin
@Composable
fun ScrollStateExample() {
    val listState = rememberLazyListState()
    
    // Biết item đầu tiên đang hiển thị
    val firstVisibleIndex = listState.firstVisibleItemIndex
    
    Column {
        Text("Đang xem từ item: $firstVisibleIndex")
        
        LazyColumn(state = listState) {
            items(100) { index ->
                Text("Item $index", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
```

### 7.2 Scroll đến vị trí cụ thể

```kotlin
@Composable
fun ScrollToPositionExample() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    Column {
        Button(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(50)  // Scroll đến item 50
                }
            }
        ) {
            Text("Đến item 50")
        }
        
        LazyColumn(state = listState) {
            items(100) { index ->
                Text("Item $index", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
```

---

## PHẦN 8: VÍ DỤ THỰC TẾ - CHAT APP

```kotlin
data class Message(
    val id: Int,
    val text: String,
    val isFromMe: Boolean,
    val time: String
)

@Composable
fun ChatScreen(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        reverseLayout = true  // Tin mới nhất ở dưới
    ) {
        items(messages, key = { it.id }) { message ->
            MessageBubble(message)
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromMe) 
            Arrangement.End else Arrangement.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromMe) 
                    Color(0xFF0084FF) else Color(0xFFE4E6EB)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    color = if (message.isFromMe) Color.White else Color.Black
                )
                Text(
                    text = message.time,
                    fontSize = 10.sp,
                    color = if (message.isFromMe) Color.White.copy(0.7f) 
                           else Color.Gray
                )
            }
        }
    }
}
```

---

## 📝 TÓM TẮT

| Component | Dùng khi | Cú pháp |
|-----------|----------|---------|
| `LazyColumn` | Danh sách dọc dài | `LazyColumn { items(list) { ... } }` |
| `LazyRow` | Danh sách ngang | `LazyRow { items(list) { ... } }` |
| `item` | Thêm 1 item (header/footer) | `item { Text("Header") }` |
| `items` | Thêm nhiều items | `items(list) { item -> ... }` |
| `itemsIndexed` | Cần cả index và item | `itemsIndexed(list) { i, item -> }` |
| `key` | Tối ưu khi list thay đổi | `items(list, key = { it.id })` |

---

## ➡️ NGÀY MAI
**Day 12: State Management cơ bản**
- remember và mutableStateOf
- State hoisting (nâng state lên)
- Stateless vs Stateful composables
- rememberSaveable
