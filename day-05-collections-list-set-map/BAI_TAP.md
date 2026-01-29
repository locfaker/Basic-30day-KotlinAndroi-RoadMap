# Bài tập Day 05: Collections

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Thao tác List (Dễ)
Cho danh sách: `["Táo", "Cam", "Chuối"]`
1. In ra phần tử đầu tiên và cuối cùng
2. Thêm "Xoài" vào cuối
3. Xóa "Cam"
4. In ra toàn bộ danh sách

### Bài 2: Lọc danh sách (Trung bình)
Cho danh sách điểm: `[5, 8, 3, 9, 6, 7, 4, 10]`
1. Lọc ra các điểm >= 5 (đậu)
2. Đếm bao nhiêu người đậu
3. Tính điểm trung bình

### Bài 3: Set - Loại bỏ trùng lặp (Trung bình)
Cho danh sách có trùng: `[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]`
1. Chuyển thành Set để loại bỏ trùng
2. In ra các số duy nhất

### Bài 4: Map - Thông tin sinh viên (Trung bình)
Tạo Map chứa thông tin sinh viên:
- "maSV" → "SV001"
- "hoTen" → "Nguyễn Văn A"
- "diemTB" → 7.5
- "xepLoai" → "Khá"

In ra thông tin theo format: "Sinh viên: [hoTen], Xếp loại: [xepLoai]"

### Bài 5: Tổng hợp (Khó)
Cho danh sách sản phẩm:
```kotlin
data class SanPham(val ten: String, val gia: Int)
val sanPhams = listOf(
    SanPham("Áo", 200000),
    SanPham("Quần", 300000),
    SanPham("Giày", 500000),
    SanPham("Mũ", 100000)
)
```
1. Lọc sản phẩm giá >= 200000
2. Sắp xếp theo giá tăng dần
3. Tính tổng tiền tất cả sản phẩm

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Khi nào dùng List, khi nào dùng Set?**

2. **`listOf()` khác gì `mutableListOf()`?**

3. **Tại sao cần dùng `filter`, `map` thay vì viết vòng lặp thủ công?**

4. **Trong Android, Collection thường dùng ở đâu?**

---

## 💡 GỢI Ý
- Dùng `filter { }` để lọc
- Dùng `map { }` để biến đổi
- Dùng `.sum()` hoặc `.sumOf { }` để tính tổng
- Dùng `.average()` để tính trung bình
