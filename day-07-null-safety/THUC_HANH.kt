package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 07: Null Safety                                          ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN: Tạo file Day07.kt trong Android Studio            ║
 * ║  Đường dẫn: app/src/main/java/com/example/myapplication/     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// Data classes cho các bài tập
data class DiaChi(val thanhPho: String?, val quan: String?, val duong: String?)
data class NguoiDung(val ten: String, val diaChi: DiaChi?)
data class User(val id: Int, val name: String, val email: String?)

fun main() {
    println("╔════════════════════════════════════════════════════╗")
    println("║          DAY 07: NULL SAFETY                       ║")
    println("╚════════════════════════════════════════════════════╝\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. Nullable vs Non-nullable
    println("--- Ví dụ 1: Khai báo Nullable ---")
    val name: String = "Minh"        // Không thể null
    val nickname: String? = null     // Có thể null
    println("Tên: $name")
    println("Biệt danh: $nickname")
    
    // 2. Safe Call ?.
    println("\n--- Ví dụ 2: Safe Call ?. ---")
    val text: String? = "Hello World"
    val nullText: String? = null
    
    println("Độ dài text: ${text?.length}")      // 11
    println("Độ dài nullText: ${nullText?.length}") // null (không crash)
    
    // 3. Elvis Operator ?:
    println("\n--- Ví dụ 3: Elvis Operator ?: ---")
    val email: String? = null
    val displayEmail = email ?: "Chưa có email"
    println("Email: $displayEmail")
    
    // 4. Safe Call Chain
    println("\n--- Ví dụ 4: Safe Call Chain ---")
    val user = NguoiDung("An", DiaChi("Hà Nội", "Hoàn Kiếm", null))
    val userNoAddress = NguoiDung("Bình", null)
    
    println("TP của An: ${user.diaChi?.thanhPho ?: "Chưa cập nhật"}")
    println("TP của Bình: ${userNoAddress.diaChi?.thanhPho ?: "Chưa cập nhật"}")
    
    // 5. let với nullable
    println("\n--- Ví dụ 5: ?.let { } ---")
    val phone: String? = "0123456789"
    val nullPhone: String? = null
    
    phone?.let {
        println("Số điện thoại: $it")
    }
    
    nullPhone?.let {
        println("Này không in ra vì nullPhone = null")
    } ?: println("Không có số điện thoại")
    
    // 6. Smart Cast
    println("\n--- Ví dụ 6: Smart Cast ---")
    val maybeString: String? = "Kotlin"
    if (maybeString != null) {
        // Kotlin tự biết maybeString không null ở đây
        println("Độ dài: ${maybeString.length}")  // Không cần ?.
    }
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới                     ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n" + "=".repeat(50))
    println("BÀI TẬP THỰC HÀNH")
    println("=".repeat(50))
    
    println("\n--- BÀI 1: Khai báo Nullable ---")
    val tenDayDu: String? = "Nguyễn Văn A"
    val bietDanh: String? = null
    val soDienThoai: String? = "0123456789"
    val emailUser: String? = null
    
    // TODO: 
    // 1. In độ dài tenDayDu
    // 2. In bietDanh hoặc "Chưa có biệt danh"
    // 3. In soDienThoai viết hoa hoặc "N/A"
    // 4. Kiểm tra email null thì nhắc cập nhật
    
    
    println("\n--- BÀI 2: Safe Call Chain ---")
    // TODO: Tạo 3 user và lấy tên thành phố an toàn
    // user1: có đầy đủ địa chỉ
    // user2: có địa chỉ nhưng thành phố null
    // user3: không có địa chỉ
    
    
    println("\n--- BÀI 3: Elvis với Return ---")
    // TODO: Viết hàm xuLyDonHang và gọi với các trường hợp test
    
    
    println("\n--- BÀI 4: let và also ---")
    val users = listOf(
        User(1, "An", "an@gmail.com"),
        User(2, "Bình", null),
        User(3, "Cường", "cuong@gmail.com"),
        User(4, "Dũng", null)
    )
    // TODO: Duyệt và xử lý email của mỗi user
    
    
    println("\n--- BÀI 5: Tránh dùng !! ---")
    // TODO: Viết lại hàm inThongTin không dùng !!
    
    
    println("\n--- BÀI 6: Smart Cast với when ---")
    // TODO: Viết hàm phanTichDuLieu và test với nhiều loại dữ liệu
    
    
    println("\n" + "=".repeat(50))
    println("KẾT THÚC DAY 07")
    println("=".repeat(50))
}


// ===== VIẾT CÁC HÀM CỦA BẠN Ở ĐÂY =====

// Bài 3: Xử lý đơn hàng
// fun xuLyDonHang(maDonHang: String?, soLuong: Int?) { ... }


// Bài 5: In thông tin (KHÔNG dùng !!)
// fun inThongTinAnToan(name: String?, age: Int?, city: String?) { ... }


// Bài 6: Phân tích dữ liệu với Smart Cast
// fun phanTichDuLieu(data: Any?) { ... }
