# Bài tập Day 06: Functions (Hàm)

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Hàm cơ bản - Tính diện tích (Dễ)
Viết các hàm tính diện tích:
1. `tinhDienTichHinhVuong(canh: Int): Int` - Diện tích = cạnh × cạnh
2. `tinhDienTichHinhChuNhat(dai: Int, rong: Int): Int` - Diện tích = dài × rộng
3. `tinhDienTichHinhTron(banKinh: Double): Double` - Diện tích = π × bán kính² (PI = 3.14159)

**Yêu cầu:** Gọi 3 hàm và in kết quả.

---

### Bài 2: Hàm kiểm tra - Validation (Trung bình)
Viết các hàm kiểm tra:
1. `kiemTraTuoi(tuoi: Int): Boolean` - Hợp lệ nếu tuổi từ 0 đến 150
2. `kiemTraEmail(email: String): Boolean` - Hợp lệ nếu chứa "@" và "."
3. `kiemTraMatKhau(pass: String): Boolean` - Hợp lệ nếu độ dài >= 6 ký tự

**Yêu cầu:** Viết hàm `dangKy(ten: String, email: String, matKhau: String)` gọi các hàm kiểm tra trên và in kết quả.

---

### Bài 3: Default Parameters (Trung bình)
Viết hàm `taoThongBao()` với các tham số:
- `tieuDe: String` (bắt buộc)
- `noiDung: String = ""` (mặc định rỗng)
- `loai: String = "info"` (mặc định là "info", có thể là "warning", "error")
- `hienThiIcon: Boolean = true`

**Yêu cầu:** Gọi hàm với nhiều cách khác nhau:
1. Chỉ truyền tiêu đề
2. Truyền tiêu đề + nội dung
3. Truyền tiêu đề + loại = "error"
4. Truyền tất cả tham số

---

### Bài 4: Lambda với Collection (Khó)
Cho danh sách sinh viên:
```kotlin
data class SinhVien(val maSV: String, val ten: String, val diem: Double)

val danhSach = listOf(
    SinhVien("SV001", "Nguyễn An", 8.5),
    SinhVien("SV002", "Trần Bình", 6.0),
    SinhVien("SV003", "Lê Cường", 9.2),
    SinhVien("SV004", "Phạm Dũng", 4.5),
    SinhVien("SV005", "Hoàng Em", 7.8)
)
```

Viết các hàm sử dụng Lambda:
1. `locSinhVienDat()` - Trả về list SV có điểm >= 5
2. `layDanhSachTen()` - Trả về list tên của tất cả SV
3. `tinhDiemTrungBinh()` - Tính điểm trung bình của lớp
4. `timSinhVienDiemCaoNhat()` - Trả về SV có điểm cao nhất
5. `sapXepTheoDiem()` - Sắp xếp danh sách theo điểm giảm dần

---

### Bài 5: Extension Functions (Khó)
Viết các Extension Function:
1. `Int.laSoNguyenTo(): Boolean` - Kiểm tra số nguyên tố
2. `String.demSoTu(): Int` - Đếm số từ trong chuỗi (tách bởi dấu cách)
3. `String.vietHoaChuDau(): String` - Viết hoa chữ cái đầu mỗi từ
4. `List<Int>.tinhTong(): Int` - Tính tổng các phần tử
5. `List<Int>.locSoChan(): List<Int>` - Lọc ra các số chẵn

---

### Bài 6: Higher-Order Function - Máy tính (Nâng cao)
Viết hàm `mayTinh(a: Double, b: Double, phepTinh: (Double, Double) -> Double): Double`

Sau đó tạo các phép tính:
- `cong`: Lambda cộng 2 số
- `tru`: Lambda trừ 2 số  
- `nhan`: Lambda nhân 2 số
- `chia`: Lambda chia 2 số (xử lý chia cho 0)
- `luythua`: Lambda tính a^b

**Yêu cầu:** Gọi hàm `mayTinh` với từng phép tính và in kết quả.

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao nên viết hàm thay vì copy-paste code?**
   > Gợi ý: Nghĩ về việc sửa lỗi, đọc hiểu code, tái sử dụng.

2. **Default Parameters giúp giải quyết vấn đề gì?**
   > Gợi ý: So sánh với Java phải viết nhiều overload methods.

3. **Khi nào nên dùng Named Arguments?**
   > Gợi ý: Nghĩ về hàm có nhiều tham số, tham số cùng kiểu.

4. **Lambda khác gì với hàm thông thường?**
   > Gợi ý: Không có tên, có thể truyền như tham số, viết inline.

5. **Extension Function có thể truy cập private members của class không?**
   > Gợi ý: Extension chỉ là "syntactic sugar", không thực sự thêm method vào class.

6. **Higher-Order Function giúp code linh hoạt như thế nào?**
   > Gợi ý: Nghĩ về hàm `filter`, `map` có thể dùng với bất kỳ điều kiện nào.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
fun tinhDienTichHinhVuong(canh: Int) = canh * canh
```

**Bài 2:**
```kotlin
fun kiemTraEmail(email: String) = email.contains("@") && email.contains(".")
```

**Bài 4:**
```kotlin
// Lọc sinh viên đạt
val svDat = danhSach.filter { it.diem >= 5 }

// Tính điểm trung bình
val diemTB = danhSach.map { it.diem }.average()

// Tìm max
val svCaoNhat = danhSach.maxByOrNull { it.diem }

// Sắp xếp giảm dần
val sapXep = danhSach.sortedByDescending { it.diem }
```

**Bài 5:**
```kotlin
fun Int.laSoNguyenTo(): Boolean {
    if (this < 2) return false
    for (i in 2 until this) {
        if (this % i == 0) return false
    }
    return true
}
```

**Bài 6:**
```kotlin
val chia: (Double, Double) -> Double = { a, b ->
    if (b != 0.0) a / b else Double.NaN
}
```
