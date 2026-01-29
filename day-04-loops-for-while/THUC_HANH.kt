package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 04: Vòng lặp (for, while)                                ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN: Tạo file Day04.kt trong Android Studio            ║
 * ║  Đường dẫn: app/src/main/java/com/example/myapplication/     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

fun main() {
    println("========== DAY 04: VÒNG LẶP ==========\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. For với dải số
    println("--- For 1 đến 5 ---")
    for (i in 1..5) {
        println("Lần $i")
    }
    
    // 2. For với until (không bao gồm số cuối)
    println("\n--- For 1 until 5 ---")
    for (i in 1 until 5) {
        println("Số: $i")  // 1, 2, 3, 4 (không có 5)
    }
    
    // 3. For đếm ngược
    println("\n--- Đếm ngược ---")
    for (i in 5 downTo 1) {
        println("Đếm: $i")
    }
    
    // 4. For với bước nhảy (step)
    println("\n--- Số lẻ từ 1-10 ---")
    for (i in 1..10 step 2) {
        println("Số lẻ: $i")
    }
    
    // 5. For qua danh sách
    println("\n--- Duyệt danh sách ---")
    val tenSinhVien = listOf("An", "Bình", "Cường")
    for (ten in tenSinhVien) {
        println("Xin chào $ten!")
    }
    
    // 6. While
    println("\n--- While ---")
    var nangLuong = 3
    while (nangLuong > 0) {
        println("Năng lượng còn: $nangLuong")
        nangLuong--
    }
    
    // 7. Break và Continue
    println("\n--- Break: Dừng khi gặp 5 ---")
    for (i in 1..10) {
        if (i == 5) break
        println("Số: $i")
    }
    
    println("\n--- Continue: Bỏ qua số chẵn ---")
    for (i in 1..10) {
        if (i % 2 == 0) continue
        println("Số lẻ: $i")
    }
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới                     ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n--- BÀI 1: Bảng cửu chương ---")
    // TODO: In bảng cửu chương của 5 (5x1=5, 5x2=10, ... 5x10=50)
    // Gợi ý: for (i in 1..10) { println("5 x $i = ${5*i}") }
    
    // Viết code ở đây:
    
    
    println("\n--- BÀI 2: Tính tổng 1 đến N ---")
    // TODO: Tính tổng các số từ 1 đến 10
    // Kết quả mong đợi: 1+2+3+...+10 = 55
    
    val soN = 10
    var tong = 0
    // Viết code ở đây:
    
    
    println("\n--- BÀI 3: Đếm số chẵn ---")
    // TODO: Đếm có bao nhiêu số chẵn từ 1 đến 20
    // Gợi ý: Dùng biến đếm, tăng lên khi gặp số chẵn
    
    var demSoChan = 0
    // Viết code ở đây:
    
    
    println("\n--- BÀI 4: Kiểm tra số nguyên tố ---")
    // TODO: Kiểm tra số 17 có phải số nguyên tố không
    // Số nguyên tố chỉ chia hết cho 1 và chính nó
    // Gợi ý: Duyệt từ 2 đến n-1, nếu chia hết cho số nào thì KHÔNG phải
    
    val soKiemTra = 17
    var laSoNguyenTo = true
    // Viết code ở đây:
    
    
    println("\n--- BÀI 5: Vẽ hình tam giác ---")
    // TODO: Vẽ hình sau (n = 4):
    // *
    // **
    // ***
    // ****
    // Gợi ý: Vòng for lồng nhau, hoặc dùng repeat()
    
    val n = 4
    // Viết code ở đây:
    
    
    println("\n========== KẾT THÚC DAY 04 ==========")
}
