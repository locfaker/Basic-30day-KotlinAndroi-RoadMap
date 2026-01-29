# Day 05: Collections - List, Set, Map

---

## 🎯 MỤC TIÊU
Hiểu được:
1. Collection là gì, tại sao cần
2. List: Danh sách có thứ tự
3. Set: Tập hợp không trùng lặp
4. Map: Cặp khóa-giá trị
5. Mutable vs Immutable (có thể sửa vs không thể sửa)

---

## PHẦN 1: COLLECTION LÀ GÌ?

Collection là **bộ sưu tập** chứa nhiều phần tử cùng loại.

**Ví dụ thực tế:**
- Danh sách tin nhắn trong App Chat
- Danh sách sản phẩm trong giỏ hàng
- Danh sách bạn bè

---

## PHẦN 2: LIST (Danh sách có thứ tự)

### 2.1 Đặc điểm
- Có thứ tự (phần tử đầu, giữa, cuối)
- Có thể chứa phần tử trùng lặp
- Truy cập qua chỉ số (index), bắt đầu từ 0

### 2.2 List không thể sửa (Immutable)
```kotlin
val danhSach = listOf("An", "Bình", "Cường")
println(danhSach[0])        // "An"
println(danhSach.size)      // 3
println(danhSach.first())   // "An"
println(danhSach.last())    // "Cường"

// danhSach.add("Dũng")  // LỖI! Không thể thêm
```

### 2.3 List có thể sửa (Mutable)
```kotlin
val danhSach = mutableListOf("An", "Bình")

danhSach.add("Cường")           // Thêm cuối
danhSach.add(0, "Anh")          // Thêm đầu
danhSach.remove("Bình")         // Xóa phần tử
danhSach[0] = "Ánh"             // Sửa phần tử
danhSach.clear()                // Xóa tất cả
```

### 2.4 Duyệt List
```kotlin
val fruits = listOf("Táo", "Cam", "Chuối")

// Cách 1: for-in
for (fruit in fruits) {
    println(fruit)
}

// Cách 2: forEach
fruits.forEach { fruit ->
    println(fruit)
}

// Cách 3: forEachIndexed (có index)
fruits.forEachIndexed { index, fruit ->
    println("$index: $fruit")
}
```

---

## PHẦN 3: SET (Tập hợp không trùng lặp)

### 3.1 Đặc điểm
- **KHÔNG** có phần tử trùng lặp
- Không đảm bảo thứ tự

### 3.2 Khi nào dùng Set?
- Lưu danh sách ID người dùng (ID không được trùng)
- Lưu các tag của bài viết

```kotlin
val soTrung = setOf(1, 2, 2, 3, 3, 3)
println(soTrung)  // [1, 2, 3] - Tự động loại bỏ trùng

val tags = mutableSetOf("kotlin", "android")
tags.add("kotlin")  // Không thêm được vì đã có
println(tags)       // [kotlin, android]
```

---

## PHẦN 4: MAP (Cặp khóa-giá trị)

### 4.1 Đặc điểm
- Mỗi phần tử là một cặp: **Key → Value**
- Key phải duy nhất
- Truy cập value thông qua key

### 4.2 Khi nào dùng Map?
- Lưu thông tin người dùng: "email" → "abc@gmail.com"
- Lưu cấu hình: "theme" → "dark"

```kotlin
val nguoiDung = mapOf(
    "ten" to "Minh",
    "tuoi" to 25,
    "email" to "minh@gmail.com"
)

println(nguoiDung["ten"])    // "Minh"
println(nguoiDung["tuoi"])   // 25
```

### 4.3 Mutable Map
```kotlin
val config = mutableMapOf(
    "theme" to "light",
    "language" to "vi"
)

config["theme"] = "dark"      // Sửa
config["fontSize"] = "14"     // Thêm mới
config.remove("language")     // Xóa
```

---

## PHẦN 5: CÁC HÀM XỬ LÝ COLLECTION (Quan trọng!)

### 5.1 filter - Lọc phần tử
```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6)
val chanNumbers = numbers.filter { it % 2 == 0 }
println(chanNumbers)  // [2, 4, 6]
```

### 5.2 map - Biến đổi phần tử
```kotlin
val numbers = listOf(1, 2, 3)
val doubled = numbers.map { it * 2 }
println(doubled)  // [2, 4, 6]
```

### 5.3 find - Tìm phần tử đầu tiên
```kotlin
val names = listOf("An", "Bình", "Cường")
val startsWithB = names.find { it.startsWith("B") }
println(startsWithB)  // "Bình"
```

### 5.4 any / all / none - Kiểm tra điều kiện
```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

numbers.any { it > 3 }   // true (có phần tử > 3)
numbers.all { it > 0 }   // true (tất cả > 0)
numbers.none { it < 0 }  // true (không có phần tử < 0)
```

### 5.5 sortedBy - Sắp xếp
```kotlin
val names = listOf("Cường", "An", "Bình")
val sorted = names.sorted()
println(sorted)  // [An, Bình, Cường]

// Sắp xếp theo tiêu chí
data class Person(val name: String, val age: Int)
val people = listOf(Person("An", 30), Person("Bình", 20))
val byAge = people.sortedBy { it.age }
```

---

## ⚡ SO SÁNH NHANH

| Đặc điểm | List | Set | Map |
|----------|------|-----|-----|
| Thứ tự | Có | Không | Theo key |
| Trùng lặp | Cho phép | Không | Key không trùng |
| Truy cập | Theo index | Duyệt qua | Theo key |
| Dùng khi | Danh sách | Tập duy nhất | Cặp key-value |

---

## 📝 TÓM TẮT

| Loại | Immutable | Mutable |
|------|-----------|---------|
| List | `listOf()` | `mutableListOf()` |
| Set | `setOf()` | `mutableSetOf()` |
| Map | `mapOf()` | `mutableMapOf()` |
