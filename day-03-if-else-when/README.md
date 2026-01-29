# Day 03: Câu lệnh điều kiện (if/else, when) - Giúp App "biết suy nghĩ"

---

## 🎯 MỤC TIÊU HÔM NAY
Sau bài này, bạn sẽ hiểu:
1. Bản chất của việc rẽ nhánh trong lập trình.
2. Cách dùng `if`, `else if`, `else` từ cơ bản đến nâng cao.
3. Tại sao Kotlin coi `if` là một biểu thức (Expression).
4. Sức mạnh của `when` - "đặc sản" của Kotlin.
5. Cách kết hợp toán tử logic để xử lý điều kiện phức tạp.

---

## PHẦN 1: BẢN CHẤT CỦA CÂU LỆNH ĐIỀU KIỆN

Lập trình không phải là một đường thẳng. App của bạn cần đưa ra quyết định:
- **NẾU** mật khẩu đúng → Cho vào App.
- **NẾU KHÔNG** → Báo lỗi.

Trong máy tính, mọi điều kiện cuối cùng đều trả về **Boolean** (`true` hoặc `false`).

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
    println("Được phép lái xe.")
} else {
    println("Chưa đủ tuổi lái xe.")
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

### 2.4 "Đặc sản" Kotlin: If là một biểu thức (Expression)
Trong các ngôn ngữ cũ (Java, C), `if` chỉ là một câu lệnh. Trong Kotlin, `if` có thể trả về giá trị.

**Cách cũ:**
```kotlin
var ketQua = ""
if (a > b) {
    ketQua = "A lớn hơn"
} else {
    ketQua = "B lớn hơn"
}
```

**Cách Kotlin (Ngắn gọn, sạch sẽ):**
```kotlin
val ketQua = if (a > b) "A lớn hơn" else "B lớn hơn"
```
*Lưu ý: Khi dùng if như một Expression, bạn bắt buộc phải có nhánh `else`.*

---

## PHẦN 3: CÂU LỆNH WHEN (Thay thế Switch/Case)

`when` trong Kotlin cực kỳ mạnh mẽ và dễ đọc hơn `if-else` dài dằng dặc.

### 3.1 Dùng như Switch cơ bản
```kotlin
val thu = 2
when (thu) {
    2 -> println("Thứ Hai")
    3 -> println("Thứ Ba")
    4 -> println("Thứ Tư")
    else -> println("Ngày không hợp lệ")
}
```

### 3.2 Nhóm nhiều điều kiện
```kotlin
when (thu) {
    2, 3, 4, 5, 6 -> println("Ngày đi làm")
    7, 8 -> println("Ngày nghỉ cuối tuần")
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
    else -> println("Yếu/Kém")
}
```

### 3.4 When không tham số (Thay thế if-else if)
```kotlin
val x = 10
val y = 20
when {
    x > y -> println("X lớn hơn Y")
    x < y -> println("X nhỏ hơn Y")
    else -> println("X bằng Y")
}
```

---

## PHẦN 4: KẾT HỢP ĐIỀU KIỆN PHỨC TẠP

Sử dụng toán tử logic `&&` (VÀ), `||` (HOẶC), `!` (KHÔNG).

```kotlin
val coVe = true
val coTien = 100
val laVIP = false

// Điều kiện: Có vé HOẶC (Là VIP VÀ có đủ tiền)
if (coVe || (laVIP && coTien >= 50)) {
    println("Mời vào xem phim!")
}
```

---

## PHẦN 5: BÀI TẬP THỰC HÀNH (4 Cấp độ)

### 🏋️ Cấp độ 1: Khởi động (Dễ)
Viết App kiểm tra số chẵn lẻ.
1. Khai báo biến `n`.
2. Dùng `if-else` kiểm tra: nếu `n % 2 == 0` thì in "Chẵn", ngược lại in "Lẻ".

### 🏋️ Cấp độ 2: Tư duy logic (Trung bình)
Viết App phân loại lứa tuổi:
- Dưới 13: "Trẻ em"
- 13 đến 19: "Thiếu niên"
- 20 đến 59: "Người trưởng thành"
- Trên 60: "Người già"
*(Yêu cầu: Dùng `when` với dải số `in ..`)*

### 🏋️ Cấp độ 3: Ứng dụng thực tế (Khó)
Viết App tính tiền điện đơn giản:
- 50 số đầu: 1.678đ/số
- 50 số tiếp theo (51-100): 1.734đ/số
- Trên 100 số: 2.014đ/số
1. Nhập vào `soDien`.
2. Tính tổng tiền và in ra.

### 🏋️ Cấp độ 4: Thử thách Jetpack Compose (Nâng cao)
1. Mở Project Android Studio.
2. Tạo một màn hình có 1 biến `diem`.
3. Nếu `diem >= 5`, hiển thị chữ màu **Xanh**.
4. Nếu `diem < 5`, hiển thị chữ màu **Đỏ**.

---

## PHẦN 6: CÂU HỎI PHẢN BIỆN (Để hiểu sâu)

1. **Tại sao Kotlin khuyến khích dùng `when` thay vì nhiều lệnh `if-else` lồng nhau?**
   <details>
   <summary>Đáp án</summary>
   - Dễ đọc hơn (Clean code).
   - Hiệu năng tốt hơn (Compiler tối ưu tốt hơn).
   - Tránh sai sót khi quên dấu ngoặc nhọn `{}`.
   </details>

2. **Dùng `if` như một Expression (biểu thức) có lợi ích gì?**
   <details>
   <summary>Đáp án</summary>
   Giúp gán giá trị cho biến trực tiếp, làm cho biến có thể là `val` (bất biến) thay vì `var`. Điều này giúp code an toàn hơn.
   </details>

3. **Lệnh `else` trong `when` có bắt buộc không?**
   <details>
   <summary>Đáp án</summary>
   - Nếu dùng `when` như một câu lệnh thực thi (Statement): KHÔNG bắt buộc.
   - Nếu dùng `when` để gán giá trị cho biến (Expression): BẮT BUỘC (Trừ khi bạn đã liệt kê hết sạch các trường hợp có thể xảy ra, ví dụ như với kiểu Enum hoặc Sealed Class).
   </details>

---

## ⚡ LỖI THƯỜNG GẶP (Cẩn thận!)

### Lỗi 1: Dùng nhầm `=` và `==`
```kotlin
if (x = 5) // SAI: Đây là phép gán
if (x == 5) // ĐÚNG: Đây là phép so sánh
```

### Lỗi 2: Quên nhánh `else` khi dùng Expression
```kotlin
val name = if (isMan) "Anh" // LỖI: Cần else
```

### Lỗi 3: Thứ tự điều kiện trong `if-else if`
```kotlin
val diem = 9
if (diem > 5) { ... }
else if (diem > 8) { ... } // Nhánh này sẽ KHÔNG BAO GIỜ được chạy vì 9 đã lọt vào nhánh > 5 rồi.
// BÀI HỌC: Để điều kiện hẹp hơn lên trước.
```

---

## 📝 TÓM TẮT
- `if-else` dùng cho các điều kiện đúng/sai đơn giản.
- `when` dùng khi có nhiều trường hợp hoặc dải giá trị.
- Tận dụng `if` và `when` dưới dạng biểu thức để code ngắn gọn và an toàn hơn.

---

## ➡️ NGÀY MAI
**Day 04: Vòng lặp (for, while)**
- Cách lặp qua một danh sách.
- Duyệt số từ 1 đến 10 thế nào? Từ 10 về 1 thế nào?
- Khi nào dùng for, khi nào dùng while?
