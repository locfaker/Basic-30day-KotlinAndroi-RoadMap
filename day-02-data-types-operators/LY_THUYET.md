# Day 02: Kiểu dữ liệu & Toán tử

---

## 🎯 MỤC TIÊU
Hiểu được:
1. Chi tiết về từng kiểu dữ liệu trong Kotlin
2. Cách chuyển đổi giữa các kiểu dữ liệu
3. Các phép toán số học, so sánh, logic
4. Thứ tự ưu tiên của toán tử

---

## PHẦN 1: KIỂU DỮ LIỆU SỐ

### 1.1 Bảng tổng hợp

| Kiểu | Kích thước | Phạm vi | Ví dụ |
|------|------------|---------|-------|
| `Byte` | 1 byte | -128 đến 127 | `val b: Byte = 100` |
| `Short` | 2 bytes | ±32,767 | `val s: Short = 1000` |
| `Int` | 4 bytes | ±2.1 tỷ | `val i = 100000` |
| `Long` | 8 bytes | Rất lớn | `val l = 10000000000L` |
| `Float` | 4 bytes | 6-7 số thập phân | `val f = 3.14f` |
| `Double` | 8 bytes | 15-16 số thập phân | `val d = 3.14159` |

### 1.2 Tại sao có nhiều kiểu số?

1. **Tiết kiệm bộ nhớ**: `Byte` (1 byte) nhẹ hơn `Int` (4 bytes)
2. **Tốc độ**: CPU xử lý số nhỏ nhanh hơn
3. **Độ chính xác**: `Double` chính xác hơn `Float` khi tính tiền

### 1.3 Chuyển đổi kiểu (Type Conversion)

Kotlin **KHÔNG** tự động chuyển đổi. Phải chuyển rõ ràng:

```kotlin
val soNguyen: Int = 100
val soThuc: Double = soNguyen.toDouble()  // 100.0

val chuoi: String = "123"
val so: Int = chuoi.toInt()  // 123

// Cách an toàn (không crash nếu sai):
val soAnToan = "abc".toIntOrNull()  // null (vì "abc" không phải số)
```

**Các hàm chuyển đổi:**
- `.toByte()`, `.toShort()`, `.toInt()`, `.toLong()`
- `.toFloat()`, `.toDouble()`
- `.toString()`

---

## PHẦN 2: KIỂU STRING (CHUỖI)

### 2.1 String là bất biến (Immutable)

Khi "thay đổi" String, Kotlin tạo String MỚI:
```kotlin
var ten = "Minh"
ten = ten + " Nguyen"  // Tạo String mới, gán lại
```

### 2.2 Các thao tác với String

```kotlin
val ten = "Nguyen Van A"

ten.length              // 12 - Độ dài
ten.uppercase()         // "NGUYEN VAN A"
ten.lowercase()         // "nguyen van a"
ten[0]                  // 'N' - Ký tự đầu
ten.contains("Van")     // true
ten.replace("Van", "Thi")  // "Nguyen Thi A"
ten.substring(0, 6)     // "Nguyen"
ten.split(" ")          // ["Nguyen", "Van", "A"]
"  Minh  ".trim()       // "Minh" - Bỏ khoảng trắng
```

### 2.3 Raw String (Chuỗi nhiều dòng)

```kotlin
val vanBan = """
    Dòng 1
    Dòng 2
    Dòng 3
""".trimIndent()
```

---

## PHẦN 3: TOÁN TỬ SỐ HỌC

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `+` | Cộng | `5 + 3` | `8` |
| `-` | Trừ | `5 - 3` | `2` |
| `*` | Nhân | `5 * 3` | `15` |
| `/` | Chia | `5 / 3` | `1` (chia nguyên!) |
| `%` | Chia lấy dư | `5 % 3` | `2` |

### ⚠️ Chú ý phép chia:

```kotlin
// Chia hai số nguyên → Kết quả là số nguyên
val a = 5 / 3      // 1 (không phải 1.666...)

// Muốn chính xác, ít nhất một số phải là Double
val b = 5.0 / 3    // 1.6666666666666667
val c = 5.toDouble() / 3  // 1.6666666666666667
```

### Phép chia lấy dư (Modulo) %

```kotlin
val du = 10 % 3  // 1 (10 chia 3 được 3 dư 1)

// Ứng dụng: Kiểm tra chẵn/lẻ
val laSoChan = (7 % 2 == 0)  // false
```

### Toán tử gán kết hợp

```kotlin
var x = 10
x += 5   // x = x + 5 → 15
x -= 3   // x = x - 3 → 12
x *= 2   // x = x * 2 → 24
x /= 4   // x = x / 4 → 6
```

### Toán tử tăng/giảm

```kotlin
var dem = 0
dem++    // Tăng 1: dem = 1
dem--    // Giảm 1: dem = 0
```

---

## PHẦN 4: TOÁN TỬ SO SÁNH

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `==` | Bằng | `5 == 5` | `true` |
| `!=` | Không bằng | `5 != 3` | `true` |
| `>` | Lớn hơn | `5 > 3` | `true` |
| `<` | Nhỏ hơn | `5 < 3` | `false` |
| `>=` | Lớn hơn hoặc bằng | `5 >= 5` | `true` |
| `<=` | Nhỏ hơn hoặc bằng | `5 <= 3` | `false` |

**Kết quả luôn là Boolean (`true` hoặc `false`).**

---

## PHẦN 5: TOÁN TỬ LOGIC

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `&&` | VÀ (AND) | `true && false` | `false` |
| `\|\|` | HOẶC (OR) | `true \|\| false` | `true` |
| `!` | PHỦ ĐỊNH (NOT) | `!true` | `false` |

### AND (`&&`) - Cả hai đều đúng mới đúng

```kotlin
val tuoi = 25
val coBangLai = true
val duocLaiXe = (tuoi >= 18) && coBangLai  // true
```

### OR (`||`) - Một trong hai đúng là đúng

```kotlin
val laVip = false
val laNhanVien = true
val duocGiamGia = laVip || laNhanVien  // true
```

### NOT (`!`) - Đảo ngược

```kotlin
val dangDangNhap = true
val chuaDangNhap = !dangDangNhap  // false
```

---

## PHẦN 6: THỨ TỰ ƯU TIÊN TOÁN TỬ

Từ cao xuống thấp:
1. `()` - Ngoặc đơn
2. `!`, `++`, `--`
3. `*`, `/`, `%`
4. `+`, `-`
5. `>`, `<`, `>=`, `<=`
6. `==`, `!=`
7. `&&`
8. `||`
9. `=`, `+=`, `-=`...

```kotlin
val ketQua = 2 + 3 * 4      // 14 (nhân trước)
val ketQua2 = (2 + 3) * 4   // 20 (ngoặc trước)
```

**Nguyên tắc:** Nếu không chắc, dùng ngoặc đơn `()` để làm rõ!

---

## ⚡ LỖI THƯỜNG GẶP

### Lỗi 1: Nhầm `=` và `==`
```kotlin
if (x = 5)  // SAI: phép gán
if (x == 5) // ĐÚNG: phép so sánh
```

### Lỗi 2: Chia số nguyên
```kotlin
val phanTram = 1 / 3 * 100  // 0 (chứ không phải 33)
// Sửa:
val phanTram = 1.0 / 3 * 100  // 33.333...
```

---

## 📝 TÓM TẮT

| Loại | Toán tử |
|------|---------|
| Số học | `+`, `-`, `*`, `/`, `%` |
| So sánh | `==`, `!=`, `>`, `<`, `>=`, `<=` |
| Logic | `&&`, `\|\|`, `!` |
| Gán | `=`, `+=`, `-=`, `*=`, `/=` |
