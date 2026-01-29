# Day 01: Làm quen Android Studio + Kotlin cơ bản

---

## 🎯 MỤC TIÊU HÔM NAY
Sau bài này, bạn sẽ hiểu:
1. Android Studio hoạt động như thế nào
2. Cấu trúc một Project Android
3. Biến là gì, tại sao cần biến
4. Khác biệt giữa `val` và `var`

---

## PHẦN 1: ANDROID STUDIO LÀ GÌ?

### 1.1 Định nghĩa
Android Studio là một **IDE** (Integrated Development Environment - Môi trường phát triển tích hợp).

**Tại sao gọi là "tích hợp"?**
Vì nó gom tất cả các công cụ bạn cần vào một chỗ:
- **Editor**: Nơi bạn viết code
- **Compiler**: Chương trình dịch code Kotlin thành ngôn ngữ máy hiểu được
- **Debugger**: Công cụ tìm lỗi
- **Emulator**: Máy ảo Android để chạy thử App
- **Build System (Gradle)**: Hệ thống đóng gói App thành file `.apk`

### 1.2 Gradle là gì?
Khi bạn nhấn nút "Run", Android Studio không trực tiếp chạy code của bạn. Nó gọi **Gradle** để:
1. Đọc file `build.gradle.kts` để biết App dùng những thư viện nào
2. Tải các thư viện từ Internet về (nếu chưa có)
3. Biên dịch (compile) code Kotlin thành bytecode
4. Đóng gói thành file `.apk`
5. Cài file `.apk` lên máy ảo hoặc điện thoại thật
6. Khởi chạy App

**Tại sao build lâu?**
Vì Gradle phải làm rất nhiều việc. Máy 8GB RAM sẽ chậm hơn máy 16GB vì Gradle cần nhiều bộ nhớ để xử lý song song.

---

## PHẦN 2: CẤU TRÚC PROJECT ANDROID

Khi bạn tạo một Project mới, Android Studio sinh ra rất nhiều file. Đây là những file **quan trọng nhất**:

```
MyApplication/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/myapplication/
│   │   │   │   └── MainActivity.kt      ← CODE CHÍNH CỦA BẠN
│   │   │   ├── res/                      ← Tài nguyên (ảnh, màu, chữ)
│   │   │   └── AndroidManifest.xml       ← "Giấy khai sinh" của App
│   │   └── build.gradle.kts              ← Cấu hình build cho module app
├── build.gradle.kts                      ← Cấu hình build cho toàn Project
└── settings.gradle.kts                   ← Khai báo có những module nào
```

### 2.1 Giải thích từng file

#### `MainActivity.kt`
Đây là **Entry Point** (điểm bắt đầu) của App. Khi người dùng mở App, Android sẽ:
1. Đọc `AndroidManifest.xml` để tìm Activity nào được đánh dấu là "launcher" (màn hình chính)
2. Tạo một instance (bản sao) của class `MainActivity`
3. Gọi hàm `onCreate()` trong class đó
4. Hàm `onCreate()` ra lệnh vẽ giao diện lên màn hình

#### `AndroidManifest.xml`
Đây là file khai báo thông tin App cho hệ điều hành Android:
- Tên package (định danh duy nhất của App)
- Quyền App cần (Camera, Internet, GPS...)
- Các Activity (màn hình) trong App
- Activity nào là màn hình khởi đầu

#### `build.gradle.kts`
Đây là file cấu hình cho Gradle:
- Phiên bản Android tối thiểu App hỗ trợ (`minSdk`)
- Phiên bản Android App được build (`targetSdk`)
- Các thư viện bên ngoài App sử dụng (`dependencies`)

---

## PHẦN 3: BIẾN (VARIABLE) LÀ GÌ?

### 3.1 Định nghĩa
Biến là một **ô nhớ có tên** trong bộ nhớ máy tính, dùng để lưu trữ dữ liệu.

**Tại sao cần biến?**
Giả sử bạn làm App tính tuổi. Người dùng nhập năm sinh là 2000. Bạn cần lưu con số 2000 này lại để tính toán. Bạn không thể viết:
```kotlin
2024 - 2000  // Cứng nhắc, chỉ tính được cho người sinh năm 2000
```

Bạn cần:
```kotlin
val namSinh = 2000  // Lưu năm sinh vào biến
val namHienTai = 2024
val tuoi = namHienTai - namSinh  // Linh hoạt, tính được cho mọi người
```

### 3.2 Cú pháp khai báo biến trong Kotlin

```kotlin
val tenBien: KieuDuLieu = giaTri
```

Ví dụ:
```kotlin
val ten: String = "Nguyen Van A"
val tuoi: Int = 25
val chieuCao: Double = 1.75
val dangDiHoc: Boolean = true
```

### 3.3 Type Inference (Tự suy luận kiểu)
Kotlin thông minh, nó có thể tự đoán kiểu dữ liệu dựa vào giá trị bạn gán:

```kotlin
val ten = "Nguyen Van A"  // Kotlin tự hiểu đây là String
val tuoi = 25             // Kotlin tự hiểu đây là Int
```

**Khi nào cần ghi rõ kiểu?**
- Khi bạn khai báo biến nhưng chưa gán giá trị ngay
- Khi bạn muốn code rõ ràng hơn cho người đọc

---

## PHẦN 4: VAL VS VAR (QUAN TRỌNG!)

### 4.1 `val` (Value - Giá trị)
- Khai báo một hằng số (constant)
- **KHÔNG THỂ** thay đổi giá trị sau khi gán lần đầu
- Giống như viết bút bi lên giấy - không xóa được

```kotlin
val pi = 3.14159
pi = 3.14  // LỖI! Không thể gán lại giá trị cho val
```

### 4.2 `var` (Variable - Biến)
- Khai báo một biến có thể thay đổi
- **CÓ THỂ** thay đổi giá trị sau khi gán
- Giống như viết bút chì lên giấy - có thể tẩy và viết lại

```kotlin
var diem = 0
diem = 10   // OK! var có thể thay đổi
diem = 20   // OK!
```

### 4.3 Tại sao mặc định nên dùng `val`?

**Lý do 1: An toàn**
Nếu bạn dùng `val`, bạn chắc chắn giá trị đó không bị thay đổi ở nơi khác trong code. Điều này giúp tránh bug.

**Lý do 2: Dễ hiểu**
Khi đọc code, thấy `val` là bạn biết ngay: "À, giá trị này cố định, không cần tìm xem nó bị đổi ở đâu".

**Lý do 3: Hiệu năng**
Compiler có thể tối ưu code tốt hơn khi biết giá trị không đổi.

**Nguyên tắc vàng:**
> Luôn dùng `val`. Chỉ đổi sang `var` khi thực sự cần thay đổi giá trị.

---

## PHẦN 5: CÁC KIỂU DỮ LIỆU CƠ BẢN

### 5.1 Số nguyên (Integer)
```kotlin
val soChan: Int = 10
val soLon: Long = 10000000000L     // Thêm L ở cuối cho số Long
val soNho: Short = 100
val soCucNho: Byte = 127           // Giới hạn từ -128 đến 127
```

**Tại sao có nhiều kiểu số nguyên?**
- `Byte`: 1 byte bộ nhớ, giới hạn -128 đến 127
- `Short`: 2 bytes, giới hạn lớn hơn
- `Int`: 4 bytes, đủ dùng cho hầu hết trường hợp
- `Long`: 8 bytes, cho số rất lớn

Dùng kiểu nhỏ hơn sẽ tiết kiệm bộ nhớ, nhưng thường thì `Int` là đủ.

### 5.2 Số thực (Floating Point)
```kotlin
val chieuCao: Double = 1.75        // Độ chính xác cao (mặc định)
val nhietDo: Float = 36.5f         // Thêm f ở cuối cho Float
```

**Khi nào dùng Float, khi nào dùng Double?**
- `Double`: Mặc định, dùng khi cần độ chính xác cao (tính tiền, khoa học)
- `Float`: Dùng khi cần tiết kiệm bộ nhớ (game, xử lý đồ họa)

### 5.3 Chuỗi ký tự (String)
```kotlin
val hoTen: String = "Nguyen Van A"
val diaChi = "123 Đường ABC"       // Kotlin tự suy luận là String
```

**String Template - Chèn biến vào chuỗi:**
```kotlin
val ten = "Minh"
val tuoi = 25
val loiChao = "Xin chào, tôi là $ten, năm nay $tuoi tuổi"
// Kết quả: "Xin chào, tôi là Minh, năm nay 25 tuổi"
```

**Biểu thức phức tạp dùng `${}`:**
```kotlin
val namSinh = 2000
val thongBao = "Bạn sinh năm $namSinh, năm nay ${2024 - namSinh} tuổi"
// Kết quả: "Bạn sinh năm 2000, năm nay 24 tuổi"
```

### 5.4 Boolean (Đúng/Sai)
```kotlin
val dangDangNhap: Boolean = true
val laAdmin = false
```

**Tại sao Boolean quan trọng?**
Boolean là nền tảng của mọi quyết định trong code:
```kotlin
if (dangDangNhap) {
    // Hiển thị trang chủ
} else {
    // Hiển thị trang đăng nhập
}
```

---

## PHẦN 6: THỰC HÀNH TRONG ANDROID STUDIO

### Bài tập 1: Sửa chữ "Hello Android"
1. Mở file `MainActivity.kt`
2. Tìm dòng `Greeting(name = "Android", ...)`
3. Đổi `"Android"` thành tên của bạn
4. Nhấn nút Run (hoặc Shift + F10)
5. Quan sát kết quả trên màn hình

### Bài tập 2: Tạo biến và hiển thị
Thay thế nội dung hàm `Greeting` bằng:

```kotlin
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Khai báo biến
    val hoTen = "Nguyen Van A"
    val namSinh = 2000
    val namHienTai = 2024
    val tuoi = namHienTai - namSinh
    
    // Hiển thị
    Text(
        text = "Xin chào $hoTen! Bạn $tuoi tuổi.",
        modifier = modifier
    )
}
```

### Bài tập 3: Thử thay đổi val thành var
```kotlin
val x = 10
x = 20  // Thử xem Android Studio báo lỗi gì?

var y = 10
y = 20  // Không lỗi
```

---

## PHẦN 7: CÂU HỎI TỰ KIỂM TRA

1. **Gradle làm nhiệm vụ gì trong Android Studio?**
   <details>
   <summary>Đáp án</summary>
   Gradle đọc file cấu hình, tải thư viện, biên dịch code, đóng gói thành APK và cài lên thiết bị.
   </details>

2. **Tại sao nên dùng `val` thay vì `var` khi có thể?**
   <details>
   <summary>Đáp án</summary>
   Vì val an toàn hơn (giá trị không bị thay đổi ngoài ý muốn), code dễ đọc hơn, và compiler tối ưu tốt hơn.
   </details>

3. **Đoạn code sau có lỗi không? Tại sao?**
   ```kotlin
   val age: Int = "25"
   ```
   <details>
   <summary>Đáp án</summary>
   Có lỗi. Biến age được khai báo kiểu Int (số nguyên) nhưng lại gán giá trị "25" là String (chuỗi). Kotlin không tự động chuyển đổi.
   </details>

4. **String Template `$` dùng để làm gì?**
   <details>
   <summary>Đáp án</summary>
   Để chèn giá trị của biến vào trong chuỗi. Ví dụ: "Xin chào $ten" sẽ thay $ten bằng giá trị thực của biến ten.
   </details>

---

## PHẦN 8: LỖI THƯỜNG GẶP

### Lỗi 1: Gán sai kiểu dữ liệu
```kotlin
val tuoi: Int = "25"  // LỖI: String không thể gán cho Int
// Sửa:
val tuoi: Int = 25
```

### Lỗi 2: Thay đổi giá trị của val
```kotlin
val pi = 3.14
pi = 3.14159  // LỖI: val không thể thay đổi
// Sửa: Dùng var nếu cần thay đổi
var pi = 3.14
pi = 3.14159  // OK
```

### Lỗi 3: Quên dấu ngoặc kép cho String
```kotlin
val ten = Minh  // LỖI: Minh là gì? Biến? Hằng số?
// Sửa:
val ten = "Minh"  // OK: "Minh" là một chuỗi ký tự
```

---

## 📝 TÓM TẮT

| Khái niệm | Ý nghĩa |
|-----------|---------|
| Android Studio | IDE để viết App Android |
| Gradle | Hệ thống build, đóng gói App |
| MainActivity.kt | File code chính, điểm bắt đầu của App |
| `val` | Hằng số, không thể thay đổi |
| `var` | Biến, có thể thay đổi |
| `Int`, `Double`, `String`, `Boolean` | Các kiểu dữ liệu cơ bản |
| `$tenBien` | Chèn biến vào chuỗi |

---

## ➡️ NGÀY MAI
Day 02: Kiểu dữ liệu chi tiết & Toán tử
- Tìm hiểu sâu hơn về các kiểu dữ liệu
- Các phép toán: cộng, trừ, nhân, chia
- Toán tử so sánh và logic
