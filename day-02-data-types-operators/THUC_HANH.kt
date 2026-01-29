package com.example.myapplication

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 02: Kiểu dữ liệu & Toán tử                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  HƯỚNG DẪN: Tạo file Day02.kt trong Android Studio            ║
 * ║  Đường dẫn: app/src/main/java/com/example/myapplication/     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

fun main() {
    println("========== DAY 02: KIỂU DỮ LIỆU & TOÁN TỬ ==========\n")
    
    // ===== VÍ DỤ MẪU =====
    
    // 1. Chuyển đổi kiểu dữ liệu
    val chuoiSo = "100"
    val so = chuoiSo.toInt()
    println("Chuỗi '$chuoiSo' chuyển thành số: $so")
    println("Cộng thêm 50: ${so + 50}")
    
    // 2. Chuyển đổi an toàn (không crash khi sai)
    val chuoiSai = "abc"
    val soAnToan = chuoiSai.toIntOrNull()
    println("Chuyển 'abc' thành số: $soAnToan")  // null
    
    // 3. Toán tử số học
    println("\n--- Toán tử số học ---")
    println("10 + 3 = ${10 + 3}")
    println("10 - 3 = ${10 - 3}")
    println("10 * 3 = ${10 * 3}")
    println("10 / 3 = ${10 / 3}")       // 3 (chia nguyên!)
    println("10.0 / 3 = ${10.0 / 3}")   // 3.333... (chia thực)
    println("10 % 3 = ${10 % 3}")       // 1 (dư)
    
    // 4. Thao tác String
    println("\n--- Thao tác String ---")
    val ten = "kotlin language"
    println("Gốc: $ten")
    println("Chữ hoa: ${ten.uppercase()}")
    println("Độ dài: ${ten.length}")
    println("Ký tự đầu: ${ten[0]}")
    println("Chứa 'kotlin': ${ten.contains("kotlin")}")
    
    // 5. Toán tử logic
    println("\n--- Toán tử logic ---")
    val a = true
    val b = false
    println("true && false = ${a && b}")  // false
    println("true || false = ${a || b}")  // true
    println("!true = ${!a}")              // false
    
    
    // ╔═══════════════════════════════════════════════════════════╗
    // ║  BÀI TẬP - Viết code của bạn bên dưới                     ║
    // ╚═══════════════════════════════════════════════════════════╝
    
    println("\n--- BÀI 1: Chuyển đổi kiểu ---")
    // TODO: Cho chuoi = "250"
    // 1. Chuyển thành Int và cộng thêm 100, in ra kết quả
    // 2. Chuyển thành Double
    // 3. Thử chuyển "hello" thành Int bằng toIntOrNull()
    
    val chuoi = "250"
    // Viết code ở đây:
    
    
    println("\n--- BÀI 2: Thao tác String ---")
    // TODO: Cho hoTen = "nguyen van a"
    // 1. Chuyển thành chữ hoa và in ra
    // 2. Lấy 6 ký tự đầu tiên (dùng .substring(0, 6))
    // 3. Thay "van" thành "thi" (dùng .replace())
    // 4. In ra độ dài chuỗi
    
    val hoTen = "nguyen van a"
    // Viết code ở đây:
    
    
    println("\n--- BÀI 3: Tính BMI ---")
    // TODO: Công thức BMI = canNang / (chieuCao * chieuCao)
    // Cho canNang = 70.0 (kg), chieuCao = 1.75 (m)
    // Tính và in: "BMI của bạn là: [kết quả]"
    
    val canNang = 70.0
    val chieuCao = 1.75
    // Viết code ở đây:
    
    
    println("\n--- BÀI 4: Kiểm tra chẵn lẻ ---")
    // TODO: Cho so = 7
    // Dùng toán tử % để kiểm tra số chẵn hay lẻ
    // In ra: "7 là số chẵn: true/false"
    
    val soKiemTra = 7
    // Viết code ở đây:
    
    
    println("\n--- BÀI 5: Điều kiện lái xe ---")
    // TODO: Cho tuoi = 20, coBangLai = true, khongSayRuou = true
    // Viết biểu thức logic: Được lái xe khi >= 18 VÀ có bằng VÀ không say
    // In ra: "Được lái xe: true/false"
    
    val tuoi = 20
    val coBangLai = true
    val khongSayRuou = true
    // Viết code ở đây:
    
    
    println("\n========== KẾT THÚC DAY 02 ==========")
}
