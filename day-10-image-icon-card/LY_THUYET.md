# Day 10: Image, Icon và Card

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiển thị **Image** từ resource và từ URL
2. Sử dụng **Icons** của Material Design
3. Tạo **Card** đẹp mắt cho ứng dụng
4. Tích hợp **Coil** để tải ảnh từ Internet

---

## PHẦN 1: IMAGE - HIỂN THỊ HÌNH ẢNH

### 1.1 Image từ Resource (drawable)

Đặt file ảnh vào `app/src/main/res/drawable/`

```kotlin
@Composable
fun ImageFromResource() {
    Image(
        painter = painterResource(id = R.drawable.my_image),
        contentDescription = "Mô tả hình ảnh",  // Accessibility
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}
```

### 1.2 ContentScale - Cách hiển thị ảnh

```kotlin
@Composable
fun ContentScaleExample() {
    Image(
        painter = painterResource(R.drawable.photo),
        contentDescription = null,
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Crop  // Cách scale ảnh
    )
}
```

| ContentScale | Mô tả |
|--------------|-------|
| `Crop` | Cắt ảnh để lấp đầy, có thể mất phần ảnh |
| `Fit` | Hiển thị trọn ảnh, có thể thừa không gian |
| `FillBounds` | Kéo giãn ảnh để lấp đầy (méo ảnh) |
| `Inside` | Thu nhỏ ảnh vừa khung |
| `None` | Không scale |
| `FillWidth` | Lấp đầy chiều rộng |
| `FillHeight` | Lấp đầy chiều cao |

### 1.3 Image với các hình dạng

```kotlin
@Composable
fun ShapedImages() {
    Column {
        // Ảnh hình tròn
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Blue, CircleShape),
            contentScale = ContentScale.Crop
        )
        
        // Ảnh bo góc
        Image(
            painter = painterResource(R.drawable.photo),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp, 120.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
```

---

## PHẦN 2: COIL - TẢI ẢNH TỪ INTERNET

### 2.1 Thêm dependency

Trong `build.gradle.kts` (app level):
```kotlin
dependencies {
    implementation("io.coil-kt:coil-compose:2.5.0")
}
```

Trong `AndroidManifest.xml`, thêm permission:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 2.2 AsyncImage - Tải ảnh từ URL

```kotlin
import coil.compose.AsyncImage

@Composable
fun ImageFromUrl() {
    AsyncImage(
        model = "https://example.com/image.jpg",
        contentDescription = "Ảnh từ Internet",
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}
```

### 2.3 Xử lý Loading và Error

```kotlin
import coil.compose.SubcomposeAsyncImage

@Composable
fun ImageWithStates() {
    SubcomposeAsyncImage(
        model = "https://example.com/image.jpg",
        contentDescription = null,
        modifier = Modifier.size(200.dp),
        loading = {
            // Hiển thị khi đang tải
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            // Hiển thị khi lỗi
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = "Error",
                    tint = Color.Red
                )
            }
        }
    )
}
```

### 2.4 AsyncImage với Placeholder

```kotlin
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext

@Composable
fun ImageWithPlaceholder() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://example.com/image.jpg")
            .crossfade(true)                    // Animation chuyển ảnh
            .placeholder(R.drawable.placeholder) // Ảnh tạm
            .error(R.drawable.error_image)       // Ảnh khi lỗi
            .build(),
        contentDescription = null,
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Crop
    )
}
```

---

## PHẦN 3: ICONS - BIỂU TƯỢNG

### 3.1 Material Icons

```kotlin
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*

@Composable
fun IconExamples() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(Icons.Default.Home, contentDescription = "Home")
        Icon(Icons.Default.Favorite, contentDescription = "Like", tint = Color.Red)
        Icon(Icons.Default.Share, contentDescription = "Share")
        Icon(Icons.Default.Settings, contentDescription = "Settings")
    }
}
```

### 3.2 Icon Styles

```kotlin
// Filled (Default) - Đặc
Icon(Icons.Filled.Favorite, contentDescription = null)

// Outlined - Viền
Icon(Icons.Outlined.Favorite, contentDescription = null)

// Rounded - Bo tròn
Icon(Icons.Rounded.Favorite, contentDescription = null)

// Sharp - Góc nhọn  
Icon(Icons.Sharp.Favorite, contentDescription = null)

// TwoTone - Hai tông màu
Icon(Icons.TwoTone.Favorite, contentDescription = null)
```

### 3.3 Thêm Extended Icons

Trong `build.gradle.kts`:
```kotlin
implementation("androidx.compose.material:material-icons-extended:1.6.0")
```

Sau đó có thể dùng nhiều icon hơn:
```kotlin
Icon(Icons.Default.AccountBalance, null)
Icon(Icons.Default.AirplanemodeActive, null)
Icon(Icons.Default.Restaurant, null)
```

### 3.4 Icon với kích thước và màu tùy chỉnh

```kotlin
@Composable
fun CustomIcon() {
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Yêu thích",
        modifier = Modifier.size(48.dp),
        tint = Color.Red
    )
}
```

---

## PHẦN 4: CARD - THẺ CHỨA NỘI DUNG

### 4.1 Card cơ bản

```kotlin
@Composable  
fun BasicCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tiêu đề Card", fontWeight = FontWeight.Bold)
            Text("Nội dung bên trong card")
        }
    }
}
```

### 4.2 Card với tùy chỉnh

```kotlin
@Composable
fun CustomCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),           // Bo góc
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)       // Màu nền
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp                  // Bóng đổ
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Custom Card")
        }
    }
}
```

### 4.3 Card có thể click

```kotlin
@Composable
fun ClickableCard() {
    Card(
        onClick = {
            // Xử lý click
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Click me!", modifier = Modifier.padding(16.dp))
    }
}
```

### 4.4 ElevatedCard và OutlinedCard

```kotlin
@Composable
fun CardVariants() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Card thường
        Card(modifier = Modifier.fillMaxWidth()) {
            Text("Card", modifier = Modifier.padding(16.dp))
        }
        
        // Card nổi (có shadow)
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Text("ElevatedCard", modifier = Modifier.padding(16.dp))
        }
        
        // Card viền
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Text("OutlinedCard", modifier = Modifier.padding(16.dp))
        }
    }
}
```

---

## PHẦN 5: KẾT HỢP - PRODUCT CARD

```kotlin
@Composable
fun ProductCard(
    name: String,
    price: String,
    rating: Float,
    imageUrl: String
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Ảnh sản phẩm
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.padding(12.dp)) {
                // Tên sản phẩm
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)  // Màu vàng gold
                    )
                    Text(
                        text = " $rating",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Giá
                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        name = "Áo thun nam cotton",
        price = "250.000đ",
        rating = 4.5f,
        imageUrl = "https://via.placeholder.com/150"
    )
}
```

---

## PHẦN 6: USER PROFILE CARD

```kotlin
@Composable
fun UserProfileCard(
    name: String,
    email: String,
    avatarUrl: String,
    isOnline: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar với badge online
            Box {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                // Online indicator
                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color.Green, CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Info
            Column(modifier = Modifier.weight(1f)) {
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
            
            // Action button
            IconButton(onClick = { /* Chat */ }) {
                Icon(Icons.Default.Message, contentDescription = "Chat")
            }
        }
    }
}
```

---

## 📝 TÓM TẮT

| Component | Công dụng | Import |
|-----------|-----------|--------|
| `Image` | Ảnh từ resource | `painterResource(R.drawable.x)` |
| `AsyncImage` | Ảnh từ URL (Coil) | `coil.compose.AsyncImage` |
| `Icon` | Biểu tượng | `Icons.Default.Name` |
| `Card` | Thẻ chứa nội dung | Material3 |
| `ElevatedCard` | Card có shadow | Material3 |
| `OutlinedCard` | Card viền | Material3 |

---

## ➡️ NGÀY MAI
**Day 11: LazyColumn và LazyRow - Danh sách hiệu năng cao**
- Tại sao dùng Lazy thay vì Column/Row
- LazyColumn cho danh sách dọc
- LazyRow cho danh sách ngang
- items, itemsIndexed
