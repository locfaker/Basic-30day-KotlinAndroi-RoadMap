# Day 06: Functions (Hàm) - Viên gạch xây dựng chương trình

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **bản chất** của hàm là gì và tại sao nó quan trọng
2. Biết cách khai báo hàm đúng cách
3. Hiểu rõ về tham số, giá trị trả về
4. Nắm vững các kỹ thuật nâng cao: Default parameters, Named arguments
5. Làm quen với Lambda - nền tảng của lập trình hiện đại

---

## PHẦN 1: HÀM LÀ GÌ? TẠI SAO CẦN HÀM?

### 1.1 Định nghĩa theo ngôn ngữ đơn giản

Hãy tưởng tượng bạn có một **công thức nấu phở**:
1. Luộc xương lấy nước
2. Cho gia vị: thảo quả, quế, hồi
3. Trần bánh phở
4. Xếp thịt, rau lên trên
5. Chan nước dùng

Mỗi lần muốn nấu phở, bạn không cần nhớ lại từng bước. Bạn chỉ cần nói: **"Nấu phở"** là biết phải làm gì.

Trong lập trình, **HÀM** chính là "công thức" đó - một khối code được đặt tên để thực hiện một nhiệm vụ cụ thể.

```kotlin
// "Công thức" nấu phở trong code
fun nauPho() {
    println("1. Luộc xương lấy nước")
    println("2. Cho gia vị")
    println("3. Trần bánh phở")
    println("4. Xếp thịt, rau")
    println("5. Chan nước dùng")
    println("Xong! Phở đã sẵn sàng!")
}

// Mỗi lần muốn "nấu phở", chỉ cần gọi:
nauPho()
```

### 1.2 Tại sao HÀM cực kỳ quan trọng?

**Vấn đề 1: Code lặp lại nhiều lần**
```kotlin
// KHÔNG dùng hàm - Code xấu, lặp lại
println("Tính tổng 1+2: ${1+2}")
println("Tính tổng 5+3: ${5+3}")
println("Tính tổng 10+20: ${10+20}")

// DÙNG hàm - Code sạch, tái sử dụng
fun tinhTong(a: Int, b: Int): Int = a + b

println("Tính tổng 1+2: ${tinhTong(1, 2)}")
println("Tính tổng 5+3: ${tinhTong(5, 3)}")
println("Tính tổng 10+20: ${tinhTong(10, 20)}")
```

**Vấn đề 2: Sửa lỗi khó khăn**
Nếu không dùng hàm, khi phát hiện lỗi ở phép tính tổng, bạn phải sửa **10 chỗ** trong code. Với hàm, chỉ cần sửa **1 chỗ** duy nhất.

**Vấn đề 3: Code khó đọc**
```kotlin
// KHÔNG dùng hàm - Đọc mệt
val gia = 100000
val soLuong = 3
val thue = 0.1
val tamTinh = gia * soLuong
val tienThue = tamTinh * thue
val tongTien = tamTinh + tienThue
val giamGia = if (tongTien > 200000) tongTien * 0.05 else 0.0
val thanhToan = tongTien - giamGia

// DÙNG hàm - Đọc hiểu ngay
val thanhToan = tinhTongTien(gia = 100000, soLuong = 3)
```

### 1.3 Quy tắc đặt tên hàm

| Quy tắc | Ví dụ đúng | Ví dụ sai |
|---------|------------|-----------|
| camelCase | `tinhTongTien()` | `TinhTongTien()` |
| Bắt đầu bằng động từ | `getTen()`, `setDiem()` | `ten()`, `diem()` |
| Mô tả rõ việc làm | `kiemTraDangNhap()` | `check()` |
| Không viết tắt quá ngắn | `calculateTotal()` | `calc()` |

---

## PHẦN 2: CÚ PHÁP KHAI BÁO HÀM (Chi tiết từng phần)

### 2.1 Cấu trúc đầy đủ

```kotlin
fun tenHam(thamSo1: KieuDuLieu1, thamSo2: KieuDuLieu2): KieuTraVe {
    // Thân hàm - Code xử lý logic
    return giaTri
}
```

**Giải thích từng phần:**

| Phần | Ý nghĩa | Ví dụ |
|------|---------|-------|
| `fun` | Từ khóa bắt buộc để khai báo hàm | `fun` |
| `tenHam` | Tên của hàm, dùng để gọi sau này | `tinhTong` |
| `(...)` | Danh sách tham số (đầu vào) | `(a: Int, b: Int)` |
| `: KieuTraVe` | Kiểu dữ liệu của giá trị trả về | `: Int` |
| `{ ... }` | Thân hàm - chứa code xử lý | `{ return a + b }` |
| `return` | Trả kết quả về cho nơi gọi hàm | `return 100` |

### 2.2 Hàm không có tham số (No Parameters)

Khi hàm không cần dữ liệu đầu vào:

```kotlin
fun chaoMungDenApp() {
    println("╔════════════════════════════════╗")
    println("║   Chào mừng đến với App!       ║")
    println("║   Phiên bản 1.0                ║")
    println("╚════════════════════════════════╝")
}

// Gọi hàm
chaoMungDenApp()
```

### 2.3 Hàm có tham số (Parameters)

**Tham số** là dữ liệu bạn truyền vào để hàm xử lý.

```kotlin
// Hàm có 1 tham số
fun chaoTen(ten: String) {
    println("Xin chào $ten!")
    println("Chúc bạn một ngày tốt lành!")
}

// Gọi hàm với giá trị cụ thể
chaoTen("Minh")     // "Xin chào Minh!"
chaoTen("An")       // "Xin chào An!"

// Hàm có nhiều tham số
fun gioiThieu(ten: String, tuoi: Int, nghiep: String) {
    println("Tôi là $ten")
    println("Năm nay $tuoi tuổi")
    println("Làm nghề $nghiep")
}

gioiThieu("Minh", 25, "Lập trình viên")
```

**⚠️ PHÂN BIỆT: Tham số (Parameter) vs Đối số (Argument)**

```kotlin
// ten, tuoi là THAM SỐ (Parameter) - Định nghĩa trong hàm
fun chao(ten: String, tuoi: Int) { ... }

// "Minh", 25 là ĐỐI SỐ (Argument) - Giá trị truyền vào khi gọi
chao("Minh", 25)
```

### 2.4 Hàm không trả về giá trị (Unit)

Khi hàm chỉ thực hiện hành động (in ra màn hình, lưu dữ liệu) mà không cần trả kết quả:

```kotlin
// Cách 1: Không ghi gì (Kotlin tự hiểu là Unit)
fun inThongBao(noiDung: String) {
    println("📢 $noiDung")
}

// Cách 2: Ghi rõ Unit
fun inThongBao(noiDung: String): Unit {
    println("📢 $noiDung")
}

// Unit tương đương void trong Java/C
```

### 2.5 Hàm có giá trị trả về (Return)

Khi hàm cần trả lại kết quả để dùng tiếp:

```kotlin
// Hàm trả về Int
fun tinhTong(a: Int, b: Int): Int {
    val ketQua = a + b
    return ketQua  // Trả về giá trị cho nơi gọi
}

// Sử dụng giá trị trả về
val tong = tinhTong(5, 3)  // tong = 8
println("Kết quả: $tong")

// Hoặc dùng trực tiếp
println("5 + 3 = ${tinhTong(5, 3)}")
```

**Hàm trả về Boolean (Dùng cho kiểm tra)**

```kotlin
fun laSoChan(so: Int): Boolean {
    return so % 2 == 0
}

fun laSoNguyenTo(n: Int): Boolean {
    if (n < 2) return false
    for (i in 2 until n) {
        if (n % i == 0) return false
    }
    return true
}

// Sử dụng
if (laSoChan(10)) {
    println("10 là số chẵn")
}

if (laSoNguyenTo(17)) {
    println("17 là số nguyên tố")
}
```

### 2.6 Hàm một dòng (Single Expression Function)

Khi thân hàm chỉ có 1 biểu thức, có thể viết gọn:

```kotlin
// Cách dài
fun tinhTong(a: Int, b: Int): Int {
    return a + b
}

// Cách gọn - Bỏ return, dấu ngoặc nhọn, và thậm chí kiểu trả về
fun tinhTong(a: Int, b: Int) = a + b

// Các ví dụ khác
fun binhPhuong(n: Int) = n * n
fun chaoTen(ten: String) = "Xin chào $ten!"
fun laSoChan(n: Int) = n % 2 == 0
```

---

## PHẦN 3: DEFAULT PARAMETERS (Giá trị mặc định)

### 3.1 Vấn đề: Quá nhiều phiên bản hàm

Trong Java, nếu muốn hàm linh hoạt, bạn phải viết nhiều phiên bản:

```java
// Java - Phải viết 3 phiên bản
void chao() { ... }
void chao(String ten) { ... }
void chao(String ten, String loiChao) { ... }
```

### 3.2 Giải pháp: Default Parameters trong Kotlin

```kotlin
// Kotlin - Chỉ cần 1 phiên bản
fun chao(ten: String = "Khách", loiChao: String = "Xin chào") {
    println("$loiChao $ten!")
}

// Gọi với đủ tham số
chao("Minh", "Hello")       // "Hello Minh!"

// Gọi thiếu tham số - Dùng giá trị mặc định
chao("Minh")                // "Xin chào Minh!"
chao()                      // "Xin chào Khách!"
```

### 3.3 Ví dụ thực tế: Hàm kết nối Database

```kotlin
fun ketNoiDatabase(
    host: String = "localhost",
    port: Int = 5432,
    database: String = "mydb",
    user: String = "admin",
    password: String = ""
) {
    println("Đang kết nối đến $host:$port/$database với user $user")
    // Code kết nối thực tế...
}

// Kết nối mặc định
ketNoiDatabase()

// Chỉ đổi host
ketNoiDatabase(host = "192.168.1.100")

// Đổi nhiều thứ
ketNoiDatabase(host = "production.server.com", database = "prod_db", password = "secret")
```

---

## PHẦN 4: NAMED ARGUMENTS (Đặt tên tham số khi gọi)

### 4.1 Vấn đề: Khó nhớ thứ tự tham số

```kotlin
fun taoNguoiDung(ten: String, email: String, dienThoai: String, diaChi: String) { ... }

// Gọi hàm - Dễ nhầm thứ tự!
taoNguoiDung("Minh", "0123456789", "minh@gmail.com", "Hà Nội")  // SAI: Đảo email và điện thoại
```

### 4.2 Giải pháp: Named Arguments

```kotlin
// Gọi với tên tham số - Rõ ràng, không sợ nhầm
taoNguoiDung(
    ten = "Minh",
    email = "minh@gmail.com",
    dienThoai = "0123456789",
    diaChi = "Hà Nội"
)

// Thậm chí có thể đổi thứ tự
taoNguoiDung(
    diaChi = "Hà Nội",
    ten = "Minh",
    email = "minh@gmail.com",
    dienThoai = "0123456789"
)
```

### 4.3 Kết hợp Default + Named Arguments

```kotlin
fun guiEmail(
    nguoiNhan: String,
    tieuDe: String = "Không có tiêu đề",
    noiDung: String = "",
    dinhKem: String? = null,
    uuTien: Boolean = false
) {
    println("Gửi đến: $nguoiNhan")
    println("Tiêu đề: $tieuDe")
    println("Nội dung: $noiDung")
    println("Ưu tiên: $uuTien")
}

// Linh hoạt trong cách gọi
guiEmail("minh@gmail.com")  // Chỉ cần email, còn lại dùng mặc định

guiEmail(
    nguoiNhan = "boss@company.com",
    uuTien = true,
    tieuDe = "Báo cáo khẩn"
)
```

---

## PHẦN 5: LAMBDA EXPRESSIONS (Hàm ẩn danh)

### 5.1 Lambda là gì?

Lambda là **hàm không có tên**. Thay vì khai báo hàm rồi gọi, bạn viết trực tiếp logic vào trong dấu `{ }`.

### 5.2 Cú pháp Lambda

```kotlin
// Cú pháp đầy đủ
val tenLambda: (KieuThamSo) -> KieuTraVe = { thamSo -> bieuThuc }

// Ví dụ
val cong: (Int, Int) -> Int = { a, b -> a + b }
println(cong(3, 5))  // 8
```

### 5.3 Ví dụ từ đơn giản đến phức tạp

```kotlin
// Lambda không tham số
val chaoHoi: () -> String = { "Xin chào!" }
println(chaoHoi())  // "Xin chào!"

// Lambda 1 tham số - Dùng "it"
val binhPhuong: (Int) -> Int = { it * it }
println(binhPhuong(5))  // 25

// Lambda 2 tham số
val tinhTich: (Int, Int) -> Int = { a, b -> a * b }
println(tinhTich(4, 5))  // 20

// Lambda với nhiều dòng
val phanTich: (Int) -> String = { so ->
    val chanLe = if (so % 2 == 0) "chẵn" else "lẻ"
    val amDuong = if (so >= 0) "dương" else "âm"
    "Số $so là số $chanLe và $amDuong"
}
println(phanTich(7))  // "Số 7 là số lẻ và dương"
```

### 5.4 Lambda trong thực tế - Làm việc với Collection

```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// filter: Lọc phần tử thỏa điều kiện
val soChan = numbers.filter { it % 2 == 0 }
println(soChan)  // [2, 4, 6, 8, 10]

// map: Biến đổi từng phần tử
val nhanDoi = numbers.map { it * 2 }
println(nhanDoi)  // [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]

// forEach: Duyệt qua từng phần tử
numbers.forEach { println("Số: $it") }

// find: Tìm phần tử đầu tiên thỏa điều kiện
val soLonHon5 = numbers.find { it > 5 }
println(soLonHon5)  // 6

// Kết hợp nhiều hàm (Chain)
val ketQua = numbers
    .filter { it % 2 == 0 }      // Lọc số chẵn: [2, 4, 6, 8, 10]
    .map { it * 10 }              // Nhân 10: [20, 40, 60, 80, 100]
    .filter { it > 50 }           // Lọc > 50: [60, 80, 100]
println(ketQua)  // [60, 80, 100]
```

---

## PHẦN 6: HIGHER-ORDER FUNCTIONS (Hàm bậc cao)

### 6.1 Định nghĩa
Hàm bậc cao là hàm mà:
- Nhận hàm khác làm tham số, HOẶC
- Trả về một hàm

### 6.2 Ví dụ: Hàm nhận Lambda làm tham số

```kotlin
// Định nghĩa hàm nhận lambda
fun tinhToan(a: Int, b: Int, phepTinh: (Int, Int) -> Int): Int {
    return phepTinh(a, b)
}

// Sử dụng
val tongSo = tinhToan(10, 5) { x, y -> x + y }   // 15
val hieuSo = tinhToan(10, 5) { x, y -> x - y }   // 5
val tichSo = tinhToan(10, 5) { x, y -> x * y }   // 50
val thuongSo = tinhToan(10, 5) { x, y -> x / y } // 2
```

### 6.3 Ví dụ thực tế: Hàm xử lý danh sách

```kotlin
data class SinhVien(val ten: String, val diem: Double)

val danhSach = listOf(
    SinhVien("An", 8.5),
    SinhVien("Bình", 6.0),
    SinhVien("Cường", 9.2),
    SinhVien("Dũng", 4.5)
)

// Hàm tìm kiếm linh hoạt với điều kiện do người gọi quyết định
fun timSinhVien(
    list: List<SinhVien>,
    dieuKien: (SinhVien) -> Boolean
): List<SinhVien> {
    return list.filter(dieuKien)
}

// Tìm sinh viên điểm >= 5
val datYeuCau = timSinhVien(danhSach) { it.diem >= 5.0 }

// Tìm sinh viên tên bắt đầu bằng "C"
val tenC = timSinhVien(danhSach) { it.ten.startsWith("C") }

// Tìm sinh viên giỏi (>=8)
val sinhVienGioi = timSinhVien(danhSach) { it.diem >= 8.0 }
```

---

## PHẦN 7: EXTENSION FUNCTIONS (Mở rộng hàm cho class có sẵn)

### 7.1 Vấn đề
Bạn muốn thêm hàm `inHoa()` cho kiểu `String`, nhưng `String` là class của Kotlin, bạn không thể sửa nó được.

### 7.2 Giải pháp: Extension Functions

```kotlin
// Thêm hàm mới cho String
fun String.inHoaChuDauMoiTu(): String {
    return this.split(" ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}

// Sử dụng như hàm có sẵn của String
val ten = "nguyen van a"
println(ten.inHoaChuDauMoiTu())  // "Nguyen Van A"
```

### 7.3 Các Extension hữu ích

```kotlin
// Extension cho Int
fun Int.binhPhuong() = this * this
fun Int.lapPhuong() = this * this * this
fun Int.laSoChan() = this % 2 == 0

println(5.binhPhuong())    // 25
println(3.lapPhuong())     // 27
println(4.laSoChan())      // true

// Extension cho List
fun List<Int>.tinhTrungBinh(): Double {
    return if (this.isEmpty()) 0.0 else this.sum().toDouble() / this.size
}

val diem = listOf(7, 8, 9, 6, 8)
println("Điểm TB: ${diem.tinhTrungBinh()}")  // 7.6

// Extension cho nullable type
fun String?.orDefault(default: String = "N/A"): String {
    return this ?: default
}

val ten: String? = null
println(ten.orDefault())           // "N/A"
println(ten.orDefault("Ẩn danh"))  // "Ẩn danh"
```

---

## ⚡ LỖI THƯỜNG GẶP

### Lỗi 1: Quên return khi hàm có kiểu trả về
```kotlin
// SAI
fun tinhTong(a: Int, b: Int): Int {
    a + b  // Không có return!
}

// ĐÚNG
fun tinhTong(a: Int, b: Int): Int {
    return a + b
}
```

### Lỗi 2: Return sai kiểu
```kotlin
// SAI
fun layTen(): String {
    return 123  // return Int nhưng khai báo String
}

// ĐÚNG
fun layTen(): String {
    return "Minh"
}
```

### Lỗi 3: Gọi hàm thiếu tham số bắt buộc
```kotlin
fun chao(ten: String) { ... }

// SAI
chao()  // Thiếu tham số ten

// ĐÚNG
chao("Minh")
```

### Lỗi 4: Nhầm thứ tự tham số
```kotlin
fun dangKy(email: String, matKhau: String) { ... }

// SAI - Đảo ngược email và mật khẩu
dangKy("123456", "minh@gmail.com")

// ĐÚNG - Dùng named arguments cho an toàn
dangKy(email = "minh@gmail.com", matKhau = "123456")
```

---

## 📝 BẢNG TÓM TẮT

| Loại hàm | Cú pháp | Ví dụ |
|----------|---------|-------|
| Không tham số, không trả về | `fun ten() { ... }` | `fun chao() { println("Hi") }` |
| Có tham số, không trả về | `fun ten(p: Type) { ... }` | `fun chao(ten: String) { print(ten) }` |
| Có trả về | `fun ten(): Type { return ... }` | `fun cong(a: Int, b: Int): Int = a + b` |
| Default params | `fun ten(p: Type = value)` | `fun chao(ten: String = "Khách")` |
| Lambda | `{ params -> body }` | `val sum = { a: Int, b: Int -> a + b }` |
| Extension | `fun Type.ten() = ...` | `fun Int.square() = this * this` |

---

## ➡️ NGÀY MAI
**Day 07: Null Safety - An toàn với giá trị null**
- NullPointerException là gì và tại sao nó nguy hiểm?
- Nullable types trong Kotlin
- Safe call (?.), Elvis operator (?:)
- Not-null assertion (!!)
