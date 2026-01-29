/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 08 - BÀI 6: TẠO HÀM @COMPOSABLE RIÊNG                    ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * Bài này giải thích tại sao có lúc dùng MainScreen(), có lúc không
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Thay vì viết code trực tiếp ở đây
            // Ta gọi hàm MainScreen()
            MainScreen()
        }
    }
}

// ===== HÀM @COMPOSABLE RIÊNG =====
// Đây là hàm tự tạo để hiển thị UI
// @Composable = đánh dấu đây là hàm UI của Compose

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Gọi các hàm Composable khác
        TieuDe()
        Spacer(modifier = Modifier.height(16.dp))
        NoiDung()
        Spacer(modifier = Modifier.height(16.dp))
        ChanTrang()
    }
}

@Composable
fun TieuDe() {
    Text(
        text = "Đây là tiêu đề",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Blue
    )
}

@Composable
fun NoiDung() {
    Text(
        text = "Đây là nội dung chính của ứng dụng",
        fontSize = 16.sp
    )
}

@Composable
fun ChanTrang() {
    Text(
        text = "© 2024 - Ứng dụng của tôi",
        fontSize = 12.sp,
        color = Color.Gray
    )
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH: TẠI SAO CẦN HÀM @COMPOSABLE RIÊNG?               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  CÁCH 1: Code trực tiếp trong setContent (ĐƠN GIẢN)           ║
 * ║  ─────────────────────────────────────────────────            ║
 * ║  setContent {                                                 ║
 * ║      Column {                                                 ║
 * ║          Text("Hello")                                        ║
 * ║          Text("World")                                        ║
 * ║      }                                                        ║
 * ║  }                                                            ║
 * ║  → Dùng khi app đơn giản, ít code                             ║
 * ║                                                               ║
 * ║  CÁCH 2: Tạo hàm @Composable riêng (CÓ TỔ CHỨC)               ║
 * ║  ─────────────────────────────────────────────────            ║
 * ║  setContent {                                                 ║
 * ║      MainScreen()  // Gọi hàm riêng                           ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  @Composable                                                  ║
 * ║  fun MainScreen() { ... }                                     ║
 * ║  → Dùng khi app phức tạp, nhiều code                          ║
 * ║  → Dễ đọc, dễ bảo trì, tái sử dụng được                       ║
 * ║                                                               ║
 * ║  CẢ 2 CÁCH ĐỀU CHẠY ĐƯỢC!                                     ║
 * ║  → Bài học đầu: dùng cách 1 cho đơn giản                      ║
 * ║  → App thực tế: dùng cách 2 để tổ chức code                   ║
 * ║                                                               ║
 * ║  @Composable là gì?                                           ║
 * ║  → Đánh dấu hàm này là "hàm UI của Compose"                   ║
 * ║  → Bắt buộc phải có khi tạo hàm hiển thị UI                   ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi nội dung trong hàm TieuDe()
 * 2. Tạo thêm hàm @Composable fun ThongTin() hiển thị thông tin bạn
 * 3. Gọi hàm ThongTin() trong MainScreen()
 */
