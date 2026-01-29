package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 06: Functions (Hàm)                                      ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN: Tạo file Day06.kt trong Android Studio            ║
 * ║  Đường dẫn: app/src/main/java/com/example/myapplication/     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// Data class cho bài 4
data class SinhVien(val maSV: String, val ten: String, val diem: Double)

fun main() {
    println("╔════════════════════════════════════════════════════╗")
    println("║          DAY 06: FUNCTIONS (HÀM)                   ║")
    println("╚════════════════════════════════════════════════════╝\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. Hàm cơ bản không tham số
    println("--- Ví dụ 1: Hàm không tham số ---")
    chaoMung()  // Gọi hàm
    
    // 2. Hàm có tham số
    println("\n--- Ví dụ 2: Hàm có tham số ---")
    chaoTen("Minh")
    chaoTen("An")
    
    // 3. Hàm có giá trị trả về
    println("\n--- Ví dụ 3: Hàm có return ---")
    val ketQua = tinhTong(5, 3)
    println("5 + 3 = $ketQua")
    println("10 + 20 = ${tinhTong(10, 20)}")
    
    // 4. Default Parameters
    println("\n--- Ví dụ 4: Default Parameters ---")
    inThongBao("Xin chào!")                              // Dùng mặc định
    inThongBao("Cảnh báo!", loai = "warning")           // Đổi loại
    inThongBao("Lỗi nghiêm trọng!", loai = "error")     // Đổi loại
    
    // 5. Lambda
    println("\n--- Ví dụ 5: Lambda ---")
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    // Lambda lọc số chẵn
    val soChan = numbers.filter { it % 2 == 0 }
    println("Số chẵn: $soChan")
    
    // Lambda nhân đôi
    val nhanDoi = numbers.map { it * 2 }
    println("Nhân đôi: $nhanDoi")
    
    // 6. Extension Function
    println("\n--- Ví dụ 6: Extension Function ---")
    println("5 bình phương = ${5.binhPhuong()}")
    println("Chữ hoa: ${"hello world".vietHoa()}")
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới                     ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n" + "=".repeat(50))
    println("BÀI TẬP THỰC HÀNH")
    println("=".repeat(50))
    
    println("\n--- BÀI 1: Tính diện tích ---")
    // TODO: Viết và gọi các hàm tính diện tích
    // tinhDienTichHinhVuong(5)
    // tinhDienTichHinhChuNhat(4, 6)
    // tinhDienTichHinhTron(3.0)
    
    
    println("\n--- BÀI 2: Hàm kiểm tra ---")
    // TODO: Viết hàm kiểm tra tuổi, email, mật khẩu
    // Gọi hàm dangKy() để test
    
    
    println("\n--- BÀI 3: Default Parameters ---")
    // TODO: Viết hàm taoThongBao() với default params
    // Gọi với nhiều cách khác nhau
    
    
    println("\n--- BÀI 4: Lambda với Collection ---")
    val danhSachSV = listOf(
        SinhVien("SV001", "Nguyễn An", 8.5),
        SinhVien("SV002", "Trần Bình", 6.0),
        SinhVien("SV003", "Lê Cường", 9.2),
        SinhVien("SV004", "Phạm Dũng", 4.5),
        SinhVien("SV005", "Hoàng Em", 7.8)
    )
    // TODO: 
    // 1. Lọc SV đạt (điểm >= 5)
    // 2. Lấy danh sách tên
    // 3. Tính điểm trung bình
    // 4. Tìm SV điểm cao nhất
    // 5. Sắp xếp theo điểm giảm dần
    
    
    println("\n--- BÀI 5: Extension Functions ---")
    // TODO: Viết extension cho Int và String
    // Int.laSoNguyenTo()
    // String.demSoTu()
    // String.vietHoaChuDau()
    
    
    println("\n--- BÀI 6: Higher-Order Function ---")
    // TODO: Viết hàm mayTinh nhận lambda làm tham số
    // Tạo các lambda: cong, tru, nhan, chia, luythua
    
    
    println("\n" + "=".repeat(50))
    println("KẾT THÚC DAY 06")
    println("=".repeat(50))
}


// ===== CÁC HÀM MẪU (Đã viết sẵn) =====

fun chaoMung() {
    println("Chào mừng bạn đến với bài học về Hàm!")
}

fun chaoTen(ten: String) {
    println("Xin chào $ten! Chúc bạn học tốt!")
}

fun tinhTong(a: Int, b: Int): Int {
    return a + b
}

fun inThongBao(noiDung: String, loai: String = "info") {
    val icon = when (loai) {
        "info" -> "ℹ️"
        "warning" -> "⚠️"
        "error" -> "❌"
        else -> "📌"
    }
    println("$icon [$loai] $noiDung")
}

// Extension Functions mẫu
fun Int.binhPhuong(): Int = this * this
fun String.vietHoa(): String = this.uppercase()


// ===== VIẾT CÁC HÀM CỦA BẠN Ở ĐÂY =====

// Bài 1: Tính diện tích
// fun tinhDienTichHinhVuong(...) = ...
// fun tinhDienTichHinhChuNhat(...) = ...
// fun tinhDienTichHinhTron(...) = ...


// Bài 2: Kiểm tra
// fun kiemTraTuoi(...) = ...
// fun kiemTraEmail(...) = ...
// fun kiemTraMatKhau(...) = ...


// Bài 5: Extension Functions
// fun Int.laSoNguyenTo(): Boolean { ... }
// fun String.demSoTu(): Int = ...
// fun String.vietHoaChuDau(): String = ...
