# Bài tập Day 02: Kiểu dữ liệu & Toán tử

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Chuyển đổi kiểu dữ liệu (Dễ)
Cho `val chuoi = "123"`, hãy:
1. Chuyển thành `Int` và cộng thêm 100
2. Chuyển thành `Double`
3. Thử chuyển `"abc"` thành `Int` bằng `toIntOrNull()` và xem kết quả

### Bài 2: Thao tác String (Dễ)
Cho `val hoTen = "nguyen van a"`, hãy:
1. Chuyển thành chữ hoa
2. Lấy 6 ký tự đầu tiên
3. Thay "van" thành "thi"
4. Đếm độ dài chuỗi

### Bài 3: Tính BMI (Trung bình)
Viết chương trình tính chỉ số BMI:
- Công thức: `BMI = canNang / (chieuCao * chieuCao)`
- Cho `canNang = 70.0` (kg), `chieuCao = 1.75` (m)
- In kết quả: "BMI của bạn là: [kết quả]"

### Bài 4: Tính tiền điện (Khó)
Cho số điện tiêu thụ `soDien = 150`:
- 50 số đầu: 1.678đ/số
- 50 số tiếp (51-100): 1.734đ/số
- Trên 100 số: 2.014đ/số

Tính tổng tiền phải trả.

### Bài 5: Kiểm tra điều kiện (Logic)
Cho:
```kotlin
val tuoi = 20
val coBangLai = true
val khongSayRuou = true
```
Viết biểu thức kiểm tra: Người này có được lái xe không?
(Điều kiện: >= 18 tuổi VÀ có bằng lái VÀ không say rượu)

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **`5 / 2` cho kết quả bao nhiêu? Tại sao?**

2. **`"123".toInt()` khác gì với `"123".toIntOrNull()`?**

3. **`true && false || true` cho kết quả gì? Giải thích thứ tự tính.**

4. **Tại sao String trong Kotlin được gọi là "bất biến" (immutable)?**

5. **Khi nào nên dùng `Float`, khi nào nên dùng `Double`?**

---

## 💡 GỢI Ý
- Mở file `THUC_HANH.kt` để làm bài
- Với bài 4, cần dùng `if-else` (sẽ học kỹ ở Day 03)
