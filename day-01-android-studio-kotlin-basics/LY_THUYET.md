# Day 01: Làm quen Android Studio & Kotlin cơ bản

---

## 🎯 MỤC TIÊU
Hiểu được:
1. Android Studio hoạt động như thế nào
2. Cấu trúc một Project Android
3. Biến là gì, tại sao cần biến
4. Sự khác biệt giữa `val` và `var`

---

## PHẦN 1: ANDROID STUDIO LÀ GÌ?

### 1.1 Định nghĩa
Android Studio là một **IDE** (Integrated Development Environment - Môi trường phát triển tích hợp).

**IDE gồm những gì?**
- **Editor**: Nơi bạn viết code
- **Compiler**: Dịch code Kotlin thành ngôn ngữ máy
- **Debugger**: Công cụ tìm lỗi
- **Emulator**: Máy ảo Android để chạy thử App
- **Build System (Gradle)**: Đóng gói App thành file `.apk`

### 1.2 Gradle là gì? Tại sao build lâu?

Khi bạn nhấn nút **Run**, Gradle phải làm:
1. Đọc file `build.gradle.kts` để biết App dùng thư viện gì
2. Tải các thư viện từ Internet (nếu chưa có)
3. Biên dịch code Kotlin thành bytecode
4. Đóng gói thành file `.apk`
5. Cài file `.apk` lên máy ảo/điện thoại
6. Khởi chạy App

**Máy 8GB RAM sẽ chậm** vì Gradle cần nhiều bộ nhớ để xử lý song song.

---

## PHẦN 2: CẤU TRÚC PROJECT ANDROID

```
MyApplication/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/myapplication/
│   │   │   └── MainActivity.kt      ← CODE CHÍNH
│   │   ├── res/                      ← Tài nguyên (ảnh, màu)
│   │   └── AndroidManifest.xml       ← "Giấy khai sinh" App
│   └── build.gradle.kts              ← Cấu hình module app
├── build.gradle.kts                  ← Cấu hình toàn Project
└── settings.gradle.kts               ← Khai báo modules
```

### Các file quan trọng:

| File | Chức năng |
|------|-----------|
| `MainActivity.kt` | Điểm bắt đầu của App. Hàm `onCreate()` là nơi vẽ giao diện |
| `AndroidManifest.xml` | Khai báo tên App, quyền (Camera, Internet...), Activity nào chạy đầu tiên |
| `build.gradle.kts` | Phiên bản Android hỗ trợ, thư viện sử dụng |

---

## PHẦN 3: BIẾN (VARIABLE) LÀ GÌ?

### 3.1 Định nghĩa
Biến là một **ô nhớ có tên** trong bộ nhớ máy tính, dùng để lưu trữ dữ liệu.

### 3.2 Tại sao cần biến?
Giả sử làm App tính tuổi. Người dùng nhập năm sinh là 2000:

```kotlin
// KHÔNG dùng biến (Cứng nhắc):
2024 - 2000  // Chỉ tính được cho người sinh năm 2000

// CÓ dùng biến (Linh hoạt):
val namSinh = 2000
val namHienTai = 2024
val tuoi = namHienTai - namSinh  // Tính được cho mọi người
```

### 3.3 Cú pháp khai báo biến

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

### 3.4 Type Inference (Tự suy luận kiểu)
Kotlin thông minh, tự đoán kiểu dữ liệu:

```kotlin
val ten = "Nguyen Van A"  // Kotlin hiểu đây là String
val tuoi = 25             // Kotlin hiểu đây là Int
```

---

## PHẦN 4: VAL VS VAR (QUAN TRỌNG!)

### 4.1 `val` (Value - Giá trị cố định)
- **KHÔNG THỂ** thay đổi sau khi gán
- Như viết bút bi - không xóa được

```kotlin
val pi = 3.14159
pi = 3.14  // LỖI! Không thể gán lại
```

### 4.2 `var` (Variable - Biến có thể đổi)
- **CÓ THỂ** thay đổi sau khi gán
- Như viết bút chì - có thể tẩy

```kotlin
var diem = 0
diem = 10   // OK!
diem = 20   // OK!
```

### 4.3 Nguyên tắc vàng:
> **Luôn dùng `val`. Chỉ đổi sang `var` khi thực sự cần thay đổi giá trị.**

**Lý do:**
1. **An toàn**: Giá trị không bị thay đổi ngoài ý muốn
2. **Dễ đọc**: Biết ngay giá trị cố định, không cần tìm xem bị đổi ở đâu
3. **Hiệu năng**: Compiler tối ưu tốt hơn

---

## PHẦN 5: CÁC KIỂU DỮ LIỆU CƠ BẢN

### 5.1 Số nguyên (Integer)

| Kiểu | Kích thước | Phạm vi |
|------|------------|---------|
| `Byte` | 1 byte | -128 đến 127 |
| `Short` | 2 bytes | -32,768 đến 32,767 |
| `Int` | 4 bytes | ±2.1 tỷ |
| `Long` | 8 bytes | Rất lớn |

```kotlin
val tuoi: Int = 25
val danSo: Long = 8000000000L  // Thêm L cho Long
```

### 5.2 Số thực (Floating Point)

| Kiểu | Độ chính xác |
|------|--------------|
| `Float` | 6-7 chữ số thập phân |
| `Double` | 15-16 chữ số thập phân |

```kotlin
val chieuCao: Double = 1.75     // Mặc định
val nhietDo: Float = 36.5f      // Thêm f cho Float
```

### 5.3 Chuỗi ký tự (String)

```kotlin
val hoTen = "Nguyen Van A"
```

**String Template - Chèn biến vào chuỗi:**
```kotlin
val ten = "Minh"
val tuoi = 25
val loiChao = "Xin chào $ten, bạn $tuoi tuổi"

// Biểu thức phức tạp dùng ${}
val namSinh = 2000
val thongBao = "Bạn sinh năm $namSinh, năm nay ${2024 - namSinh} tuổi"
```

### 5.4 Boolean (Đúng/Sai)

```kotlin
val dangDangNhap: Boolean = true
val laAdmin = false
```

---

## PHẦN 6: LỖI THƯỜNG GẶP

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
// Sửa: Dùng var
var pi = 3.14
```

### Lỗi 3: Quên dấu ngoặc kép cho String
```kotlin
val ten = Minh  // LỖI
val ten = "Minh"  // OK
```

---

## 📝 TÓM TẮT

| Khái niệm | Ý nghĩa |
|-----------|---------|
| Android Studio | IDE để viết App Android |
| Gradle | Hệ thống build, đóng gói App |
| `val` | Hằng số, không thể thay đổi |
| `var` | Biến, có thể thay đổi |
| `Int`, `Double`, `String`, `Boolean` | Các kiểu dữ liệu cơ bản |
| `$tenBien` | Chèn biến vào chuỗi |
