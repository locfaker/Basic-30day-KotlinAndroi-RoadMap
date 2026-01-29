# Day 04: Vòng lặp (Loops) - Tự động hóa công việc lặp đi lặp lại

---

## 🎭 TẠI SAO CẦN VÒNG LẶP?
Trong lập trình, có những công việc phải lặp lại nhiều lần. Ví dụ:
- Gửi thông báo cho 100 người dùng.
- Kiểm tra từng tin nhắn trong hộp thư.
- Vẽ 10 cái nút giống hệt nhau trên màn hình.

Thay vì viết 100 dòng code giống nhau, ta dùng **Vòng lặp**.

---

## PHẦN 1: VÒNG LẶP `FOR` (Khi biết trước số lần lặp)

Vòng lặp `for` trong Kotlin rất mạnh mẽ vì nó hoạt động dựa trên các "dải" (Ranges) hoặc các tập hợp dữ liệu.

### 1.1 Lặp theo dải số (Ranges)
```kotlin
// Chạy từ 1 đến 5 (bao gồm cả 5)
for (i in 1..5) {
    println("Lần thứ $i")
}

// Chạy từ 1 đến 5 (NHƯNG KHÔNG bao gồm 5)
for (i in 1 until 5) {
    println("Chỉ chạy đến 4: $i")
}

// Chạy ngược từ 5 về 1
for (i in 5 downTo 1) {
    println("Đếm ngược: $i")
}

// Chạy với bước nhảy (Step)
for (i in 1..10 step 2) {
    println("Số lẻ: $i") // 1, 3, 5, 7, 9
}
```

### 1.2 Lặp qua một danh sách (List/Array)
Đây là cách dùng phổ biến nhất trong Android (ví dụ: lặp qua danh sách tin nhắn để hiển thị).
```kotlin
val sinhVien = listOf("An", "Bình", "Cường")
for (ten in sinhVien) {
    println("Chào bạn $ten")
}
```

---

## PHẦN 2: VÒNG LẶP `WHILE` & `DO-WHILE` (Khi dựa vào điều kiện)

Dùng khi bạn **không biết trước** phải lặp bao nhiêu lần, chỉ biết lặp **khi điều kiện còn đúng**.

### 2.1 Vòng lặp `while`
Kiểm tra điều kiện TRƯỚC khi làm. Nếu sai ngay từ đầu thì không chạy lần nào.
```kotlin
var nangLuong = 10
while (nangLuong > 0) {
    println("Đang làm việc... Năng lượng còn $nangLuong")
    nangLuong-- // Quan trọng: Phải thay đổi điều kiện để thoát vòng lặp
}
```

### 2.2 Vòng lặp `do-while`
Làm TRƯỚC, kiểm tra SAU. Đảm bảo code chạy **ít nhất 1 lần**.
```kotlin
var soLanThu = 0
do {
    println("Đang thử kết nối lần thứ $soLanThu")
    soLanThu++
} while (soLanThu < 0) // Điều kiện sai ngay, nhưng vẫn chạy được 1 lần
```

---

## PHẦN 3: ĐIỀU KHIỂN VÒNG LẶP (`BREAK` & `CONTINUE`)

### 3.1 `break` (Thoát ngay lập tức)
Dùng để dừng vòng lặp sớm khi đã đạt được mục đích.
```kotlin
for (i in 1..10) {
    if (i == 5) break // Tìm thấy số 5 rồi, nghỉ luôn!
    println(i)
}
```

### 3.2 `continue` (Bỏ qua lần này)
Dùng để bỏ qua các bước không cần thiết và nhảy sang lần lặp tiếp theo.
```kotlin
for (i in 1..10) {
    if (i % 2 == 0) continue // Nếu là số chẵn, bỏ qua không in
    println("Số lẻ: $i")
}
```

---

## PHẦN 4: LỆNH `REPEAT` (Đặc sản Kotlin)
Nếu bạn chỉ muốn lặp đơn giản N lần mà không quan tâm đến biến chạy `i`:
```kotlin
repeat(3) {
    println("Chào bạn!")
}
```

---

## ⚡ CẢNH BÁO: VÒNG LẶP VÔ TẬN (INFINITE LOOP)
Đây là lỗi khiến máy bạn bị treo (lag). Xảy ra khi điều kiện `while` luôn luôn đúng.
```kotlin
// NGUY HIỂM:
var x = 5
while (x > 0) {
    println("Lỗi rồi!")
    // Quên không trừ x đi, x mãi mãi > 0
}
```
**Cách sửa:** Luôn đảm bảo biến điều kiện sẽ thay đổi để đến lúc nào đó vòng lặp sẽ dừng.
