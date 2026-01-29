package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 03: Câu lệnh điều kiện (if/else, when)                   ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN: Tạo file Day03.kt trong Android Studio            ║
 * ║  Đường dẫn: app/src/main/java/com/example/myapplication/     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

fun main() {
    println("========== DAY 03: ĐIỀU KIỆN IF/ELSE/WHEN ==========\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. If-Else cơ bản
    val diem = 7
    if (diem >= 5) {
        println("Kết quả: Đỗ")
    } else {
        println("Kết quả: Trượt")
    }
    
    // 2. If là Expression (trả về giá trị)
    val ketQua = if (diem >= 5) "Đỗ" else "Trượt"
    println("Kết quả (Expression): $ketQua")
    
    // 3. When cơ bản
    val thu = 3
    when (thu) {
        2 -> println("Thứ Hai")
        3 -> println("Thứ Ba")
        4 -> println("Thứ Tư")
        5 -> println("Thứ Năm")
        6 -> println("Thứ Sáu")
        7 -> println("Thứ Bảy")
        8 -> println("Chủ Nhật")
        else -> println("Không hợp lệ")
    }
    
    // 4. When với dải số (Range)
    val diemXepLoai = 8.5
    val xepLoai = when (diemXepLoai) {
        in 9.0..10.0 -> "Xuất sắc"
        in 8.0..8.9 -> "Giỏi"
        in 6.5..7.9 -> "Khá"
        in 5.0..6.4 -> "Trung bình"
        else -> "Yếu"
    }
    println("Điểm $diemXepLoai - Xếp loại: $xepLoai")
    
    // 5. When không tham số (như nhiều if)
    val x = 10
    val y = 20
    when {
        x > y -> println("X lớn hơn Y")
        x < y -> println("X nhỏ hơn Y")
        else -> println("X bằng Y")
    }
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới                     ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n--- BÀI 1: Kiểm tra chẵn/lẻ ---")
    // TODO: Cho n = 7, kiểm tra in ra "Số chẵn" hoặc "Số lẻ"
    // Gợi ý: Dùng n % 2 == 0
    
    val n = 7
    // Viết code ở đây:
    
    
    println("\n--- BÀI 2: Phân loại tuổi ---")
    // TODO: Dùng when với dải số để phân loại:
    // Dưới 13: "Trẻ em", 13-19: "Thiếu niên", 
    // 20-59: "Người trưởng thành", Từ 60: "Người cao tuổi"
    
    val tuoi = 25
    // Viết code ở đây:
    
    
    println("\n--- BÀI 3: Xếp loại học lực ---")
    // TODO: Cho diemThi, xếp loại:
    // 9-10: Xuất sắc, 8-8.9: Giỏi, 6.5-7.9: Khá,
    // 5-6.4: Trung bình, Dưới 5: Yếu
    
    val diemThi = 7.5
    // Viết code ở đây:
    
    
    println("\n--- BÀI 4: Tính tiền điện ---")
    // TODO: Tính tiền điện bậc thang
    // 50 số đầu: 1678đ, 51-100: 1734đ, 101-200: 2014đ, >200: 2536đ
    // Cho soDien = 250, tính tổng tiền
    
    val soDien = 250
    var tongTien = 0.0
    // Viết code ở đây:
    // Gợi ý: 
    // - 50 số đầu: 50 * 1678
    // - 50 số tiếp (51-100): 50 * 1734
    // - 100 số tiếp (101-200): 100 * 2014
    // - Còn lại (>200): (soDien - 200) * 2536
    
    
    println("\n--- BÀI 5: Máy tính đơn giản ---")
    // TODO: Cho a, b và phepTinh (+, -, *, /)
    // Dùng when để thực hiện phép tính
    
    val a = 10
    val b = 3
    val phepTinh = "/"
    // Viết code ở đây:
    
    
    println("\n========== KẾT THÚC DAY 03 ==========")
}
