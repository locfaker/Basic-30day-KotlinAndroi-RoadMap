# Day 02: Kiểu dữ liệu & Toán tử

---

## 🎯 MỤC TIÊU HÔM NAY
Sau bài này, bạn sẽ hiểu:
1. Chi tiết về từng kiểu dữ liệu trong Kotlin
2. Cách chuyển đổi giữa các kiểu dữ liệu
3. Các phép toán số học
4. Toán tử so sánh và logic
5. Thứ tự ưu tiên của các toán tử

---

## PHẦN 1: KIỂU DỮ LIỆU SỐ

### 1.1 Bảng tổng hợp các kiểu số

| Kiểu | Kích thước | Phạm vi | Ví dụ |
|------|------------|---------|-------|
| `Byte` | 1 byte | -128 đến 127 | `val b: Byte = 100` |
| `Short` | 2 bytes | -32,768 đến 32,767 | `val s: Short = 1000` |
| `Int` | 4 bytes | -2.1 tỷ đến 2.1 tỷ | `val i: Int = 100000` |
| `Long` | 8 bytes | Rất lớn | `val l: Long = 10000000000L` |
| `Float` | 4 bytes | 6-7 chữ số thập phân | `val f: Float = 3.14f` |
| `Double` | 8 bytes | 15-16 chữ số thập phân | `val d: Double = 3.14159265359` |

### 1.2 Tại sao có nhiều kiểu số như vậy?

**Lý do 1: Tiết kiệm bộ nhớ**
Nếu bạn chỉ lưu tuổi người (0-150), dùng `Byte` (1 byte) thay vì `Int` (4 bytes) tiết kiệm được 3 bytes. Trong một App có hàng triệu user, điều này rất quan trọng.

**Lý do 2: Tốc độ xử lý**
CPU xử lý số nhỏ nhanh hơn số lớn.

**Lý do 3: Độ chính xác**
`Double` chính xác hơn `Float`. Khi tính tiền, bạn không muốn mất vài đồng vì làm tròn sai.

### 1.3 Chuyển đổi kiểu (Type Conversion)

Kotlin **KHÔNG** tự động chuyển đổi kiểu. Bạn phải chuyển rõ ràng:

```kotlin
val soNguyen: Int = 100
val soThuc: Double = soNguyen.toDouble()  // 100.0

val soLon: Long = 1000000000L
val soNho: Int = soLon.toInt()  // Cẩn thận: có thể mất dữ liệu nếu số quá lớn

val chuoi: String = "123"
val so: Int = chuoi.toInt()  // 123
```

**Các hàm chuyển đổi:**
- `.toByte()`, `.toShort()`, `.toInt()`, `.toLong()`
- `.toFloat()`, `.toDouble()`
- `.toString()`

**Cảnh báo quan trọng:**
```kotlin
val chuoi = "abc"
val so = chuoi.toInt()  // CRASH! "abc" không phải số

// Cách an toàn:
val so = chuoi.toIntOrNull()  // Trả về null nếu không chuyển được
```

---

## PHẦN 2: KIỂU BOOLEAN

### 2.1 Giá trị Boolean
Boolean chỉ có 2 giá trị: `true` (đúng) và `false` (sai).

```kotlin
val dangOnline: Boolean = true
val laNguoiLon: Boolean = false
```

### 2.2 Tại sao Boolean quan trọng?

Mọi quyết định trong code đều dựa trên Boolean:
- Đăng nhập thành công? → `true` → Vào trang chủ
- Đăng nhập thất bại? → `false` → Hiện thông báo lỗi

```kotlin
val matKhauDung = true

if (matKhauDung) {
    // Cho vào App
} else {
    // Báo lỗi
}
```

---

## PHẦN 3: KIỂU STRING (CHUỖI)

### 3.1 Khai báo String
```kotlin
val ten: String = "Nguyen Van A"
val diaChi = "123 Đường ABC"  // Kotlin tự suy luận
```

### 3.2 String là bất biến (Immutable)

**Quan trọng:** Khi bạn "thay đổi" một String, thực chất Kotlin tạo ra một String MỚI.

```kotlin
var ten = "Minh"
ten = ten + " Nguyen"  // Tạo String mới "Minh Nguyen", gán lại cho biến ten
// String gốc "Minh" vẫn tồn tại trong bộ nhớ (sẽ bị dọn dẹp sau)
```

### 3.3 Các thao tác với String

```kotlin
val ten = "Nguyen Van A"

// Độ dài
val doDai = ten.length  // 12

// Chữ hoa/thường
val chuHoa = ten.uppercase()     // "NGUYEN VAN A"
val chuThuong = ten.lowercase()  // "nguyen van a"

// Lấy ký tự tại vị trí (bắt đầu từ 0)
val kyTuDau = ten[0]  // 'N'
val kyTuThu3 = ten[2] // 'u'

// Kiểm tra chứa chuỗi con
val coVan = ten.contains("Van")  // true

// Thay thế
val tenMoi = ten.replace("Van", "Thi")  // "Nguyen Thi A"

// Cắt chuỗi
val ho = ten.substring(0, 6)  // "Nguyen" (từ vị trí 0 đến 5)

// Tách chuỗi
val danhSachTu = ten.split(" ")  // ["Nguyen", "Van", "A"]

// Bỏ khoảng trắng đầu cuối
val tenCoKhoangTrang = "  Minh  "
val tenSach = tenCoKhoangTrang.trim()  // "Minh"
```

### 3.4 String Template (Chèn biến vào chuỗi)

```kotlin
val ten = "Minh"
val tuoi = 25

// Cách 1: Nối chuỗi (cũ, không nên dùng)
val loiChao1 = "Xin chào, tôi là " + ten + ", năm nay " + tuoi + " tuổi"

// Cách 2: String Template (mới, nên dùng)
val loiChao2 = "Xin chào, tôi là $ten, năm nay $tuoi tuổi"

// Cách 3: Biểu thức phức tạp dùng ${}
val namSinh = 2000
val loiChao3 = "Bạn sinh năm $namSinh, năm nay ${2024 - namSinh} tuổi"
```

### 3.5 Raw String (Chuỗi nhiều dòng)

```kotlin
val vanBan = """
    Đây là dòng 1
    Đây là dòng 2
    Đây là dòng 3
""".trimIndent()

// trimIndent() bỏ các khoảng trắng thừa ở đầu mỗi dòng
```

---

## PHẦN 4: TOÁN TỬ SỐ HỌC

### 4.1 Các phép toán cơ bản

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `+` | Cộng | `5 + 3` | `8` |
| `-` | Trừ | `5 - 3` | `2` |
| `*` | Nhân | `5 * 3` | `15` |
| `/` | Chia | `5 / 3` | `1` (chia nguyên) |
| `%` | Chia lấy dư | `5 % 3` | `2` |

### 4.2 Chú ý về phép chia

```kotlin
// Chia hai số nguyên → Kết quả là số nguyên (bỏ phần thập phân)
val a = 5 / 3      // Kết quả: 1 (không phải 1.666...)

// Muốn kết quả chính xác, ít nhất một số phải là Double
val b = 5.0 / 3    // Kết quả: 1.6666666666666667
val c = 5 / 3.0    // Kết quả: 1.6666666666666667
val d = 5.toDouble() / 3  // Kết quả: 1.6666666666666667
```

### 4.3 Phép chia lấy dư (Modulo)

Phép `%` trả về phần dư sau khi chia:

```kotlin
val du = 10 % 3  // 10 chia 3 được 3 dư 1 → Kết quả: 1
```

**Ứng dụng thực tế:**

```kotlin
// Kiểm tra số chẵn/lẻ
val so = 7
val laSoChan = (so % 2 == 0)  // false (7 chia 2 dư 1)

// Lấy chữ số hàng đơn vị
val soLon = 12345
val hangDonVi = soLon % 10  // 5
```

### 4.4 Toán tử gán kết hợp

```kotlin
var x = 10

x += 5   // Tương đương: x = x + 5  → x = 15
x -= 3   // Tương đương: x = x - 3  → x = 12
x *= 2   // Tương đương: x = x * 2  → x = 24
x /= 4   // Tương đương: x = x / 4  → x = 6
x %= 4   // Tương đương: x = x % 4  → x = 2
```

### 4.5 Toán tử tăng/giảm

```kotlin
var dem = 0

dem++    // Tăng 1: dem = 1
dem--    // Giảm 1: dem = 0

// Prefix vs Postfix
var a = 5
val b = a++  // b = 5, sau đó a = 6 (gán trước, tăng sau)
val c = ++a  // a tăng thành 7 trước, c = 7 (tăng trước, gán sau)
```

---

## PHẦN 5: TOÁN TỬ SO SÁNH

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `==` | Bằng | `5 == 5` | `true` |
| `!=` | Không bằng | `5 != 3` | `true` |
| `>` | Lớn hơn | `5 > 3` | `true` |
| `<` | Nhỏ hơn | `5 < 3` | `false` |
| `>=` | Lớn hơn hoặc bằng | `5 >= 5` | `true` |
| `<=` | Nhỏ hơn hoặc bằng | `5 <= 3` | `false` |

**Kết quả của phép so sánh luôn là Boolean (`true` hoặc `false`).**

```kotlin
val tuoi = 20
val laNguoiLon = tuoi >= 18  // true

val diem = 4.5
val dat = diem >= 5.0        // false
```

---

## PHẦN 6: TOÁN TỬ LOGIC

| Toán tử | Ý nghĩa | Ví dụ |
|---------|---------|-------|
| `&&` | VÀ (AND) | `true && false` → `false` |
| `\|\|` | HOẶC (OR) | `true \|\| false` → `true` |
| `!` | PHỦ ĐỊNH (NOT) | `!true` → `false` |

### 6.1 Toán tử AND (`&&`)
Chỉ `true` khi **CẢ HAI** đều `true`:

| A | B | A && B |
|---|---|--------|
| true | true | **true** |
| true | false | false |
| false | true | false |
| false | false | false |

```kotlin
val tuoi = 25
val coBangLai = true

val duocLaiXe = (tuoi >= 18) && coBangLai  // true && true = true
```

### 6.2 Toán tử OR (`||`)
`true` khi **ÍT NHẤT MỘT** cái `true`:

| A | B | A \|\| B |
|---|---|----------|
| true | true | true |
| true | false | **true** |
| false | true | **true** |
| false | false | false |

```kotlin
val laVip = false
val laNhanVien = true

val duocGiamGia = laVip || laNhanVien  // false || true = true
```

### 6.3 Toán tử NOT (`!`)
Đảo ngược giá trị:

```kotlin
val dangDangNhap = true
val chuaDangNhap = !dangDangNhap  // false
```

### 6.4 Kết hợp nhiều điều kiện

```kotlin
val tuoi = 25
val coBangLai = true
val khongUongRuou = true

// Phải đủ 18 tuổi VÀ có bằng lái VÀ không say rượu
val duocLaiXe = (tuoi >= 18) && coBangLai && khongUongRuou
```

---

## PHẦN 7: THỨ TỰ ƯU TIÊN TOÁN TỬ

Từ cao xuống thấp:

1. `()` - Ngoặc đơn (ưu tiên cao nhất)
2. `!`, `++`, `--` - Toán tử một ngôi
3. `*`, `/`, `%` - Nhân, chia
4. `+`, `-` - Cộng, trừ
5. `>`, `<`, `>=`, `<=` - So sánh
6. `==`, `!=` - Bằng, không bằng
7. `&&` - AND
8. `||` - OR
9. `=`, `+=`, `-=`... - Gán (ưu tiên thấp nhất)

**Ví dụ:**
```kotlin
val ketQua = 2 + 3 * 4      // 3 * 4 = 12, rồi 2 + 12 = 14
val ketQua2 = (2 + 3) * 4   // 2 + 3 = 5, rồi 5 * 4 = 20
```

**Nguyên tắc:** Nếu không chắc, hãy dùng ngoặc đơn `()` để làm rõ ý định!

---

## PHẦN 8: THỰC HÀNH

### Bài tập 1: Tính tuổi
```kotlin
fun main() {
    val namSinh = 2000
    val namHienTai = 2024
    val tuoi = namHienTai - namSinh
    println("Bạn $tuoi tuổi")
}
```

### Bài tập 2: Kiểm tra số chẵn/lẻ
```kotlin
fun main() {
    val so = 7
    val laSoChan = (so % 2 == 0)
    println("$so là số chẵn: $laSoChan")
}
```

### Bài tập 3: Tính BMI
```kotlin
fun main() {
    val canNang = 70.0  // kg
    val chieuCao = 1.75 // m
    val bmi = canNang / (chieuCao * chieuCao)
    println("BMI của bạn là: $bmi")
    
    // Đánh giá
    val tinhTrang = if (bmi < 18.5) "Gầy"
                    else if (bmi < 25) "Bình thường"
                    else if (bmi < 30) "Thừa cân"
                    else "Béo phì"
    println("Tình trạng: $tinhTrang")
}
```

### Bài tập 4: Kiểm tra điều kiện lái xe
```kotlin
fun main() {
    val tuoi = 20
    val coBangLai = true
    val khongSayRuou = true
    
    val duocLaiXe = (tuoi >= 18) && coBangLai && khongSayRuou
    println("Được phép lái xe: $duocLaiXe")
}
```

---

## PHẦN 9: CÂU HỎI TỰ KIỂM TRA

1. **`5 / 2` cho kết quả bao nhiêu? Tại sao?**
   <details>
   <summary>Đáp án</summary>
   Kết quả là 2. Vì cả hai đều là Int nên kết quả là phép chia nguyên, phần thập phân bị bỏ.
   </details>

2. **`"123".toInt()` khác gì với `"123".toIntOrNull()`?**
   <details>
   <summary>Đáp án</summary>
   `toInt()` sẽ crash nếu chuỗi không phải số. `toIntOrNull()` trả về null nếu không chuyển được, an toàn hơn.
   </details>

3. **`true && false || true` cho kết quả gì?**
   <details>
   <summary>Đáp án</summary>
   Kết quả là `true`. AND tính trước: `true && false = false`. Sau đó OR: `false || true = true`.
   </details>

---

## ➡️ NGÀY MAI
Day 03: Điều kiện (if/else, when)
- Cấu trúc rẽ nhánh
- Biểu thức when thay thế nhiều if-else
- When với ranges và Smart Cast
