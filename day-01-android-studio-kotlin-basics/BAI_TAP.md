# Bài tập Day 01: Làm quen Android Studio & Biến

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Sửa chữ "Hello Android" (Khởi động)
1. Mở file `MainActivity.kt`
2. Tìm dòng `Greeting(name = "Android", ...)`
3. Đổi `"Android"` thành tên của bạn
4. Nhấn nút Run (hoặc Shift + F10)
5. Quan sát kết quả trên màn hình

### Bài 2: Khai báo thông tin cá nhân (Dễ)
Khai báo các biến sau và in ra màn hình:
- `hoTen`: Họ tên của bạn
- `namSinh`: Năm sinh
- `chieuCao`: Chiều cao (mét)
- `laNamGioi`: true/false

### Bài 3: Tính tuổi (Trung bình)
1. Khai báo biến `namSinh` và `namHienTai`
2. Tính tuổi và lưu vào biến `tuoi`
3. In ra: "Bạn sinh năm [namSinh], năm nay [tuoi] tuổi"

### Bài 4: Thử nghiệm val và var (Tư duy)
```kotlin
val x = 10
x = 20  // Dòng này sẽ báo lỗi gì?

var y = 10
y = 20  // Dòng này có lỗi không?
```
Giải thích tại sao?

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Gradle làm nhiệm vụ gì trong Android Studio?**

2. **Tại sao nên dùng `val` thay vì `var` khi có thể?**

3. **Đoạn code sau có lỗi không? Tại sao?**
   ```kotlin
   val age: Int = "25"
   ```

4. **String Template `$` dùng để làm gì?**

5. **Khi nào bạn BẮT BUỘC phải dùng `var` thay vì `val`?**

---

## 💡 GỢI Ý
- Mở file `THUC_HANH.kt` để làm bài
- Chạy bằng cách nhấn nút tam giác xanh cạnh `fun main()`
