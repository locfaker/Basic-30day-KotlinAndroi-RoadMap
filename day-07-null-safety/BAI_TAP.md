# Bài tập Day 07: Null Safety

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Khai báo Nullable (Dễ)
Khai báo các biến sau và xử lý:
```kotlin
val tenDayDu: String? = "Nguyễn Văn A"
val bietDanh: String? = null
val soDienThoai: String? = "0123456789"
val email: String? = null
```

1. In ra độ dài của `tenDayDu` (dùng `?.`)
2. In ra `bietDanh`, nếu null thì in "Chưa có biệt danh" (dùng `?:`)
3. In ra `soDienThoai` viết hoa, nếu null thì "N/A"
4. Kiểm tra `email` có null không, nếu có thì in "Vui lòng cập nhật email"

---

### Bài 2: Safe Call Chain (Trung bình)
Cho các data class:
```kotlin
data class DiaChi(val thanhPho: String?, val quan: String?, val duong: String?)
data class NguoiDung(val ten: String, val diaChi: DiaChi?)
```

Tạo các user khác nhau và lấy tên thành phố an toàn:
1. User có đầy đủ địa chỉ
2. User có địa chỉ nhưng thành phố null
3. User không có địa chỉ (diaChi = null)

Với mỗi user, in ra: "Thành phố: [tên thành phố hoặc 'Chưa cập nhật']"

---

### Bài 3: Elvis với Return (Trung bình)
Viết hàm `xuLyDonHang(maDonHang: String?, soLuong: Int?)`:
1. Nếu `maDonHang` null → in "Lỗi: Thiếu mã đơn hàng" và return
2. Nếu `soLuong` null hoặc <= 0 → in "Lỗi: Số lượng không hợp lệ" và return
3. Nếu OK → in "Xử lý đơn hàng [mã] với [số lượng] sản phẩm"

Gọi hàm với các trường hợp:
- `xuLyDonHang(null, 5)`
- `xuLyDonHang("DH001", null)`
- `xuLyDonHang("DH001", 0)`
- `xuLyDonHang("DH001", 3)`

---

### Bài 4: let và also (Khó)
Cho danh sách người dùng:
```kotlin
data class User(val id: Int, val name: String, val email: String?)

val users = listOf(
    User(1, "An", "an@gmail.com"),
    User(2, "Bình", null),
    User(3, "Cường", "cuong@gmail.com"),
    User(4, "Dũng", null)
)
```

1. Duyệt qua danh sách, với mỗi user:
   - Nếu có email → in "Gửi email đến [email]"
   - Nếu không có email → in "[Tên] chưa có email"
2. Lọc ra những user có email và in danh sách

---

### Bài 5: Tránh dùng !! (Nâng cao)
Refactor code sau để KHÔNG dùng `!!`:

```kotlin
// Code cũ - Nguy hiểm
fun inThongTin(name: String?, age: Int?, city: String?) {
    println("Tên: ${name!!}")
    println("Tuổi: ${age!!}")
    println("Thành phố: ${city!!}")
}
```

Viết lại với:
1. Cách 1: Dùng `?: return` để thoát sớm nếu thiếu dữ liệu
2. Cách 2: Dùng `?:` để có giá trị mặc định
3. Cách 3: Dùng `?.let` để chỉ in khi có giá trị

---

### Bài 6: Smart Cast (Nâng cao)
Viết hàm `phanTichDuLieu(data: Any?)` sử dụng smart cast:

1. Nếu `data` là `null` → in "Không có dữ liệu"
2. Nếu `data` là `String` → in "Chuỗi có độ dài: [length]"
3. Nếu `data` là `Int` → in "Số nguyên, bình phương: [n*n]"
4. Nếu `data` là `List<*>` → in "Danh sách có [size] phần tử"
5. Các loại khác → in "Loại dữ liệu: [tên class]"

Gọi hàm với: `null`, `"Hello"`, `42`, `listOf(1,2,3)`, `3.14`

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao Kotlin phân biệt `String` và `String?`?**
   > Gợi ý: So sánh với Java, NullPointerException

2. **`?.` khác gì `?:`?**
   > Gợi ý: Một cái trả về null, một cái trả về giá trị mặc định

3. **Tại sao nên tránh dùng `!!`?**
   > Gợi ý: Nó phá vỡ mục đích null safety của Kotlin

4. **`?.let { }` có lợi ích gì so với `if (x != null)`?**
   > Gợi ý: Functional, chain được, biến thành non-null trong block

5. **Smart Cast hoạt động với `var` không? Tại sao?**
   > Gợi ý: Thread safety, var có thể bị đổi giá trị

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 2:**
```kotlin
val user = NguoiDung("An", DiaChi("Hà Nội", "Hoàn Kiếm", "Trần Hưng Đạo"))
val thanhPho = user.diaChi?.thanhPho ?: "Chưa cập nhật"
```

**Bài 3:**
```kotlin
fun xuLyDonHang(maDonHang: String?, soLuong: Int?) {
    val ma = maDonHang ?: run {
        println("Lỗi: Thiếu mã đơn hàng")
        return
    }
    val sl = soLuong?.takeIf { it > 0 } ?: run {
        println("Lỗi: Số lượng không hợp lệ")
        return
    }
    println("Xử lý đơn hàng $ma với $sl sản phẩm")
}
```

**Bài 5 - Cách 1:**
```kotlin
fun inThongTin(name: String?, age: Int?, city: String?) {
    val n = name ?: return
    val a = age ?: return
    val c = city ?: return
    println("Tên: $n, Tuổi: $a, TP: $c")
}
```

**Bài 6:**
```kotlin
fun phanTichDuLieu(data: Any?) {
    when (data) {
        null -> println("Không có dữ liệu")
        is String -> println("Chuỗi có độ dài: ${data.length}")
        is Int -> println("Số nguyên, bình phương: ${data * data}")
        is List<*> -> println("Danh sách có ${data.size} phần tử")
        else -> println("Loại dữ liệu: ${data::class.simpleName}")
    }
}
```
