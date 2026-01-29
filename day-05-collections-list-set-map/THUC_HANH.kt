package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 05: Collections (List, Set, Map)                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN: Tạo file Day05.kt trong Android Studio            ║
 * ║  Đường dẫn: app/src/main/java/com/example/myapplication/     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// Data class cho Bài 5
data class SanPham(val ten: String, val gia: Int)

fun main() {
    println("========== DAY 05: COLLECTIONS ==========\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. List cơ bản
    println("--- List ---")
    val fruits = listOf("Táo", "Cam", "Chuối")
    println("Danh sách: $fruits")
    println("Phần tử đầu: ${fruits.first()}")
    println("Phần tử cuối: ${fruits.last()}")
    println("Số phần tử: ${fruits.size}")
    
    // 2. Mutable List
    println("\n--- Mutable List ---")
    val danhSach = mutableListOf("An", "Bình")
    danhSach.add("Cường")
    println("Sau khi thêm: $danhSach")
    danhSach.remove("Bình")
    println("Sau khi xóa: $danhSach")
    
    // 3. Set (loại bỏ trùng lặp)
    println("\n--- Set ---")
    val soTrung = setOf(1, 2, 2, 3, 3, 3)
    println("Set loại bỏ trùng: $soTrung")
    
    // 4. Map
    println("\n--- Map ---")
    val nguoiDung = mapOf(
        "ten" to "Minh",
        "tuoi" to 25
    )
    println("Tên: ${nguoiDung["ten"]}")
    println("Tuổi: ${nguoiDung["tuoi"]}")
    
    // 5. Filter và Map
    println("\n--- Filter và Map ---")
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    val evenNumbers = numbers.filter { it % 2 == 0 }
    println("Số chẵn: $evenNumbers")
    
    val doubled = numbers.map { it * 2 }
    println("Nhân đôi: $doubled")
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới                     ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n--- BÀI 1: Thao tác List ---")
    // TODO: 
    // 1. Tạo mutableList: ["Táo", "Cam", "Chuối"]
    // 2. In phần tử đầu và cuối
    // 3. Thêm "Xoài"
    // 4. Xóa "Cam"
    // 5. In danh sách cuối cùng
    
    // Viết code ở đây:
    
    
    println("\n--- BÀI 2: Lọc điểm ---")
    // TODO:
    // 1. Cho listOf(5, 8, 3, 9, 6, 7, 4, 10)
    // 2. Lọc điểm >= 5
    // 3. Đếm số người đậu
    // 4. Tính điểm trung bình (dùng .average())
    
    val diemSo = listOf(5, 8, 3, 9, 6, 7, 4, 10)
    // Viết code ở đây:
    
    
    println("\n--- BÀI 3: Set loại bỏ trùng ---")
    // TODO: Chuyển list thành set để loại bỏ trùng
    
    val listTrung = listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
    // Viết code ở đây:
    
    
    println("\n--- BÀI 4: Map thông tin ---")
    // TODO: Tạo map chứa thông tin sinh viên
    // In ra: "Sinh viên: [hoTen], Xếp loại: [xepLoai]"
    
    // Viết code ở đây:
    
    
    println("\n--- BÀI 5: Tổng hợp ---")
    // TODO:
    // 1. Lọc sản phẩm giá >= 200000
    // 2. Sắp xếp theo giá tăng dần
    // 3. Tính tổng tiền
    
    val sanPhams = listOf(
        SanPham("Áo", 200000),
        SanPham("Quần", 300000),
        SanPham("Giày", 500000),
        SanPham("Mũ", 100000)
    )
    // Viết code ở đây:
    
    
    println("\n========== KẾT THÚC DAY 05 ==========")
}
