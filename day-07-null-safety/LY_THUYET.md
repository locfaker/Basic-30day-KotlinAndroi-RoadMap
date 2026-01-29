# Day 07: Null Safety - Loại bỏ "Billion Dollar Mistake"

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **NullPointerException** là gì và tại sao nó nguy hiểm
2. Nắm vững cách Kotlin xử lý null an toàn
3. Thành thạo các toán tử: `?.`, `?:`, `!!`, `?.let`
4. Biết khi nào dùng công cụ nào cho phù hợp

---

## PHẦN 1: VẤN ĐỀ VỚI NULL - "Billion Dollar Mistake"

### 1.1 Null là gì?

`null` nghĩa là **"không có giá trị"** hoặc **"trống rỗng"**.

```kotlin
// Ví dụ: Người dùng chưa nhập tên
val tenNguoiDung = null  // Không có giá trị

// Ví dụ: Tìm kiếm không có kết quả
val ketQuaTimKiem = null  // Không tìm thấy
```

### 1.2 Tại sao Null nguy hiểm?

Trong Java và nhiều ngôn ngữ khác, khi bạn cố gắng sử dụng một biến có giá trị `null`, chương trình sẽ **CRASH** với lỗi `NullPointerException` (NPE).

```java
// Java - Code nguy hiểm
String name = null;
int length = name.length();  // CRASH! NullPointerException
```

**Thống kê thực tế:**
- NullPointerException là lỗi phổ biến nhất trong các ứng dụng
- Tony Hoare (người phát minh null) gọi đây là "sai lầm tỷ đô" của ông

### 1.3 Kotlin giải quyết vấn đề này như thế nào?

Kotlin phân biệt rõ ràng:
- **Non-null type**: Biến CHẮC CHẮN có giá trị, không bao giờ null
- **Nullable type**: Biến CÓ THỂ null

```kotlin
// Non-null - KHÔNG thể gán null
var ten: String = "Minh"
ten = null  // LỖI COMPILE! Không cho phép

// Nullable - CÓ THỂ null (thêm dấu ?)
var tenNullable: String? = "Minh"
tenNullable = null  // OK
```

---

## PHẦN 2: KHAI BÁO NULLABLE TYPE

### 2.1 Cú pháp: Thêm dấu `?` sau kiểu dữ liệu

```kotlin
// Non-nullable (Mặc định)
val name: String = "Minh"      // Không thể null
val age: Int = 25              // Không thể null
val isOnline: Boolean = true   // Không thể null

// Nullable (Thêm dấu ?)
val nickname: String? = null   // Có thể null
val phone: String? = "0123"    // Có thể có giá trị hoặc null
val score: Int? = null         // Có thể null
```

### 2.2 Khi nào cần dùng Nullable?

| Tình huống | Ví dụ |
|------------|-------|
| Dữ liệu từ người dùng có thể để trống | `val soDienThoai: String? = inputField.text` |
| Kết quả tìm kiếm có thể không có | `val user: User? = database.findById(id)` |
| API trả về có thể thiếu field | `val avatar: String? = response.avatarUrl` |
| Giá trị mặc định chưa được set | `var selectedItem: Item? = null` |

---

## PHẦN 3: SAFE CALL OPERATOR `?.` (An toàn nhất)

### 3.1 Vấn đề: Không thể gọi method trên nullable type

```kotlin
val name: String? = null
val length = name.length  // LỖI COMPILE! name có thể null
```

### 3.2 Giải pháp: Safe Call `?.`

**Cách hoạt động:** Nếu biến không null → gọi method. Nếu null → trả về null.

```kotlin
val name: String? = null
val length: Int? = name?.length  // Kết quả: null (không crash!)

val name2: String? = "Minh"
val length2: Int? = name2?.length  // Kết quả: 4
```

### 3.3 Chuỗi Safe Call (Chain)

Khi có nhiều tầng có thể null:

```kotlin
// Giả sử: User -> Address -> City -> Name
data class City(val name: String?)
data class Address(val city: City?)
data class User(val address: Address?)

val user: User? = null

// Cách an toàn - Dùng chuỗi ?.
val cityName: String? = user?.address?.city?.name
// Nếu bất kỳ tầng nào null → kết quả là null, không crash
```

### 3.4 Safe Call với method
```kotlin
val name: String? = "  minh  "

// Gọi nhiều method liên tiếp
val cleanName: String? = name?.trim()?.uppercase()
// Kết quả: "MINH"

val nullName: String? = null
val cleanNullName: String? = nullName?.trim()?.uppercase()
// Kết quả: null (không crash)
```

---

## PHẦN 4: ELVIS OPERATOR `?:` (Giá trị mặc định)

### 4.1 Vấn đề: Muốn có giá trị backup khi null

```kotlin
val name: String? = null
// Muốn: Nếu name null thì dùng "Khách"
```

### 4.2 Giải pháp: Elvis Operator `?:`

**Cách hoạt động:** Nếu trái null → dùng giá trị bên phải

```kotlin
val name: String? = null
val displayName: String = name ?: "Khách"
// Kết quả: "Khách"

val name2: String? = "Minh"
val displayName2: String = name2 ?: "Khách"
// Kết quả: "Minh"
```

### 4.3 Kết hợp Safe Call + Elvis

```kotlin
val user: User? = null

// Lấy tên thành phố, nếu null thì dùng "Chưa xác định"
val cityName: String = user?.address?.city?.name ?: "Chưa xác định"
```

### 4.4 Elvis với return/throw

```kotlin
fun processUser(userId: String?) {
    // Nếu userId null → return luôn, không chạy tiếp
    val id = userId ?: return
    
    // Code tiếp theo chỉ chạy khi id có giá trị
    println("Processing user: $id")
}

fun getUser(id: String?): User {
    // Nếu id null → throw exception
    val validId = id ?: throw IllegalArgumentException("ID không được null")
    return database.findById(validId)
}
```

---

## PHẦN 5: NOT-NULL ASSERTION `!!` (Nguy hiểm - Cẩn thận!)

### 5.1 Cách hoạt động

`!!` nói với compiler: "Tôi CHẮC CHẮN biến này không null. Nếu null thì cứ crash đi!"

```kotlin
val name: String? = "Minh"
val length: Int = name!!.length  // Kết quả: 4

val nullName: String? = null
val length2: Int = nullName!!.length  // CRASH! NullPointerException
```

### 5.2 Khi nào dùng `!!`?

**Hầu như KHÔNG BAO GIỜ nên dùng `!!`**

Chỉ dùng khi:
1. Bạn 100% chắc chắn giá trị không null (đã kiểm tra trước đó)
2. Trong unit test
3. Khi làm việc với Java code cũ

```kotlin
// Trường hợp hiếm hoi có thể dùng !!
fun process(list: List<String>) {
    if (list.isNotEmpty()) {
        val first = list.firstOrNull()!!  // Đã check isNotEmpty, chắc chắn có
    }
}
```

### 5.3 Thay thế `!!` bằng cách an toàn hơn

```kotlin
// ĐỪNG làm thế này
val name: String? = getName()
println(name!!.uppercase())  // Nguy hiểm!

// HÃY làm thế này
val name: String? = getName()
name?.let { 
    println(it.uppercase())
}

// Hoặc
val name: String = getName() ?: "Mặc định"
println(name.uppercase())
```

---

## PHẦN 6: SCOPE FUNCTIONS VỚI NULL

### 6.1 `?.let { }` - Thực thi code nếu không null

```kotlin
val email: String? = "minh@gmail.com"

email?.let { emailValue ->
    // Block này CHỈ chạy khi email không null
    println("Email hợp lệ: $emailValue")
    sendVerification(emailValue)
}

// Viết gọn với "it"
email?.let {
    println("Email: $it")
}
```

### 6.2 `?.also { }` - Tương tự let nhưng trả về object gốc

```kotlin
val user: User? = getUser()

user?.also {
    println("User: ${it.name}")
    log("Loaded user: ${it.id}")
}?.let {
    // Tiếp tục xử lý user
    processUser(it)
}
```

### 6.3 `?.run { }` - Truy cập trực tiếp properties

```kotlin
val user: User? = getUser()

user?.run {
    // Trong block này, "this" là user
    println("Tên: $name")      // Không cần user.name
    println("Tuổi: $age")      // Không cần user.age
    updateProfile(name, age)
}
```

---

## PHẦN 7: SMART CAST (Kotlin tự động ép kiểu)

### 7.1 Kotlin thông minh sau khi check null

```kotlin
val name: String? = "Minh"

if (name != null) {
    // Sau khi check, Kotlin tự động biết name không null
    println(name.length)  // Không cần ?. vì đã check ở trên
}

// Smart cast trong when
when (name) {
    null -> println("Tên trống")
    else -> println("Độ dài: ${name.length}")  // Smart cast
}
```

### 7.2 Điều kiện để Smart Cast hoạt động

```kotlin
// ✅ Hoạt động với val (không thể thay đổi)
val name: String? = "Minh"
if (name != null) {
    println(name.length)  // OK
}

// ❌ KHÔNG hoạt động với var (có thể bị thay đổi bởi thread khác)
var name: String? = "Minh"
if (name != null) {
    // Compiler không chắc chắn name vẫn không null ở đây
    // println(name.length)  // Vẫn cần ?.
    println(name?.length)    // Phải dùng ?.
}
```

---

## PHẦN 8: BẢNG TỔNG HỢP

| Operator | Cú pháp | Khi nào dùng | Kết quả nếu null |
|----------|---------|--------------|------------------|
| Safe Call | `a?.b` | Truy cập an toàn | `null` |
| Elvis | `a ?: b` | Giá trị mặc định | Giá trị `b` |
| Not-null | `a!!` | TRÁNH DÙNG | CRASH! |
| let | `a?.let { }` | Thực thi nếu không null | Không chạy block |
| Smart Cast | `if (a != null)` | Sau khi check null | Tự ép kiểu |

---

## ⚡ LỖI THƯỜNG GẶP

### Lỗi 1: Quên dấu `?` khi khai báo nullable
```kotlin
var name: String = null  // LỖI! String không thể null
var name: String? = null // OK
```

### Lỗi 2: Lạm dụng `!!`
```kotlin
// SAI - Nguy hiểm
val length = name!!.length

// ĐÚNG - An toàn
val length = name?.length ?: 0
```

### Lỗi 3: Quên xử lý case null
```kotlin
// SAI - Bỏ qua null
val name: String? = null
println(name?.length)  // In ra "null" - không có ý nghĩa

// ĐÚNG - Xử lý rõ ràng
val name: String? = null
val length = name?.length ?: run {
    println("Tên trống!")
    0
}
```

---

## 📝 TÓM TẮT

| Tình huống | Giải pháp |
|------------|-----------|
| Truy cập property/method của nullable | `object?.property` |
| Cần giá trị mặc định khi null | `value ?: defaultValue` |
| Thực thi code khi không null | `value?.let { ... }` |
| Chắc chắn không null (hiếm) | `value!!` |
| Chain nhiều nullable | `a?.b?.c?.d` |

---

## ➡️ NGÀY MAI
**Day 08: Giới thiệu Jetpack Compose - UI hiện đại cho Android**
- @Composable là gì?
- Các component cơ bản: Text, Column, Row, Box
- Modifier - Trang trí UI
- Preview - Xem trước giao diện
