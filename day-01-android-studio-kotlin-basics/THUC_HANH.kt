package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 01: Làm quen với biến trong Kotlin                       ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN SỬ DỤNG:                                           ║
 * ║  1. Copy toàn bộ file này                                     ║
 * ║  2. Trong Android Studio, vào thư mục:                        ║
 * ║     app/src/main/java/com/example/myapplication/              ║
 * ║  3. Chuột phải -> New -> Kotlin File/Class                    ║
 * ║  4. Đặt tên: Day01                                            ║
 * ║  5. Paste code vào                                            ║
 * ║  6. Nhấn nút tam giác xanh cạnh "fun main()" để chạy          ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

fun main() {
    println("========== DAY 01: BIẾN TRONG KOTLIN ==========\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. Khai báo biến với val (không thể đổi)
    val ten = "Minh"
    val tuoi = 25
    
    // 2. Khai báo biến với var (có thể đổi)
    var diem = 0
    diem = 10  // OK vì dùng var
    
    // 3. String Template - Chèn biến vào chuỗi
    println("Xin chào $ten, bạn $tuoi tuổi")
    println("Điểm hiện tại: $diem")
    
    // 4. Tính toán trong String Template
    val namSinh = 2000
    val namHienTai = 2024
    println("Bạn sinh năm $namSinh, năm nay ${namHienTai - namSinh} tuổi")
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới mỗi comment         ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n--- BÀI 1: Khai báo thông tin cá nhân ---")
    // TODO: Khai báo các biến sau và in ra:
    // - hoTen: String (Họ tên của bạn)
    // - namSinhCuaBan: Int (Năm sinh)
    // - chieuCao: Double (Chiều cao tính bằng mét, ví dụ 1.75)
    // - laNamGioi: Boolean (true hoặc false)
    
    // Viết code ở đây:
    
    
    println("\n--- BÀI 2: Tính tuổi ---")
    // TODO: Khai báo namSinhNguoiDung = 1995, namHienTai2 = 2024
    // Tính tuổi và in ra theo format: "Bạn sinh năm X, năm nay Y tuổi"
    
    // Viết code ở đây:
    
    
    println("\n--- BÀI 3: Thử val và var ---")
    // TODO: Bỏ comment dòng bên dưới và xem lỗi báo gì
    // val soKhongDoi = 100
    // soKhongDoi = 200  // Thử bỏ comment dòng này
    
    // TODO: Giờ thử với var
    // var soCoTheDoi = 100
    // soCoTheDoi = 200
    // println("Số có thể đổi: $soCoTheDoi")
    
    
    println("\n========== KẾT THÚC DAY 01 ==========")
}
