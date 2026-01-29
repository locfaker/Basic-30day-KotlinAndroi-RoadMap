# Day 03: Câu lệnh điều kiện (if/else, when)

---

## 🎯 MỤC TIÊU
Hiểu được:
1. Tại sao App cần "đưa ra quyết định"
2. Cách dùng `if`, `else if`, `else`
3. Kotlin coi `if` là biểu thức (Expression) - khác gì?
4. Sức mạnh của `when` - thay thế switch/case

---

## PHẦN 1: TẠI SAO CẦN CÂU LỆNH ĐIỀU KIỆN?

App của bạn cần đưa ra quyết định:
- **NẾU** mật khẩu đúng → Cho vào App
- **NẾU KHÔNG** → Báo lỗi

Mọi điều kiện cuối cùng đều trả về **Boolean** (`true` hoặc `false`).

---

## PHẦN 2: CẤU TRÚC IF / ELSE

### 2.1 If đơn giản
```kotlin
val diem = 8
if (diem >= 5) {
    println("Bạn đã đỗ!")
}
```

### 2.2 If - Else
```kotlin
val tuoi = 16
if (tuoi >= 18) {
    println("Được phép lái xe")
} else {
    println("Chưa đủ tuổi lái xe")
}
```

### 2.3 If - Else If - Else (Nhiều nhánh)
```kotlin
val nhietDo = 30
if (nhietDo > 35) {
    println("Trời rất nóng")
} else if (nhietDo > 25) {
    println("Trời ấm áp")
} else if (nhietDo > 15) {
    println("Trời mát mẻ")
} else {
    println("Trời lạnh")
}
```

### 2.4 ĐẶC SẢN KOTLIN: If là Expression

Trong Kotlin, `if` có thể **trả về giá trị**:

```kotlin
// Cách cũ (dài dòng):
var ketQua = ""
if (a > b) {
    ketQua = "A lớn hơn"
} else {
    ketQua = "B lớn hơn"
}

// Cách Kotlin (ngắn gọn):
val ketQua = if (a > b) "A lớn hơn" else "B lớn hơn"
```

**Lưu ý:** Khi dùng `if` như Expression, BẮT BUỘC phải có `else`.

---

## PHẦN 3: LỆNH WHEN (Thay thế Switch/Case)

`when` trong Kotlin cực kỳ mạnh mẽ và dễ đọc.

### 3.1 When cơ bản
```kotlin
val thu = 2
when (thu) {
    2 -> println("Thứ Hai")
    3 -> println("Thứ Ba")
    4 -> println("Thứ Tư")
    else -> println("Ngày khác")
}
```

### 3.2 Nhóm nhiều điều kiện
```kotlin
when (thu) {
    2, 3, 4, 5, 6 -> println("Ngày đi làm")
    7, 8 -> println("Cuối tuần")
}
```

### 3.3 Dùng với dải số (Ranges)
```kotlin
val diem = 8.5
when (diem) {
    in 9.0..10.0 -> println("Xuất sắc")
    in 8.0..8.9 -> println("Giỏi")
    in 6.5..7.9 -> println("Khá")
    in 5.0..6.4 -> println("Trung bình")
    else -> println("Yếu")
}
```

### 3.4 When không tham số
```kotlin
val x = 10
val y = 20
when {
    x > y -> println("X lớn hơn Y")
    x < y -> println("X nhỏ hơn Y")
    else -> println("X bằng Y")
}
```

### 3.5 When là Expression
```kotlin
val xepLoai = when (diem) {
    in 9.0..10.0 -> "Xuất sắc"
    in 8.0..8.9 -> "Giỏi"
    else -> "Cần cố gắng"
}
```

---

## PHẦN 4: KẾT HỢP ĐIỀU KIỆN PHỨC TẠP

Dùng `&&` (VÀ), `||` (HOẶC), `!` (KHÔNG):

```kotlin
val coVe = true
val tienConLai = 100
val laVIP = false

// Vào được nếu: có vé HOẶC (là VIP VÀ có đủ tiền)
if (coVe || (laVIP && tienConLai >= 50)) {
    println("Mời vào!")
}
```

---

## ⚡ LỖI THƯỜNG GẶP

### Lỗi 1: Nhầm `=` và `==`
```kotlin
if (x = 5)  // SAI: Đây là phép gán
if (x == 5) // ĐÚNG: Đây là phép so sánh
```

### Lỗi 2: Quên `else` khi dùng Expression
```kotlin
val ten = if (laNam) "Anh"  // LỖI: Thiếu else
val ten = if (laNam) "Anh" else "Chị"  // OK
```

### Lỗi 3: Thứ tự điều kiện sai
```kotlin
val diem = 9
if (diem > 5) { println("Đỗ") }
else if (diem > 8) { println("Giỏi") }  // KHÔNG BAO GIỜ CHẠY!
// Vì 9 > 5 nên đã vào nhánh đầu rồi

// Sửa: Điều kiện hẹp hơn để trước
if (diem > 8) { println("Giỏi") }
else if (diem > 5) { println("Đỗ") }
```

---

## 📝 TÓM TẮT

| Cấu trúc | Khi nào dùng |
|----------|--------------|
| `if-else` | Điều kiện đúng/sai đơn giản |
| `if-else if-else` | 2-3 nhánh điều kiện |
| `when` | Nhiều trường hợp, dải giá trị |
| `if` Expression | Gán giá trị dựa trên điều kiện |
