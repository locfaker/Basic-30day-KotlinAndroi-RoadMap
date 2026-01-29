/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 08: JETPACK COMPOSE CƠ BẢN                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  BÀI 5: KẾT HỢP COLUMN VÀ ROW                                 ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Column chính
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                // Tiêu đề
                Text(
                    text = "Thông tin cá nhân",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                
                // Khoảng cách
                Spacer(modifier = Modifier.height(16.dp))
                
                // Row 1: Họ tên
                Row {
                    Text(text = "Họ tên: ", fontWeight = FontWeight.Bold)
                    Text(text = "Nguyễn Văn A")
                }
                
                // Khoảng cách
                Spacer(modifier = Modifier.height(8.dp))
                
                // Row 2: Tuổi
                Row {
                    Text(text = "Tuổi: ", fontWeight = FontWeight.Bold)
                    Text(text = "25")
                }
                
                // Khoảng cách
                Spacer(modifier = Modifier.height(8.dp))
                
                // Row 3: Email
                Row {
                    Text(text = "Email: ", fontWeight = FontWeight.Bold)
                    Text(text = "nguyenvana@gmail.com", color = Color.Blue)
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH:                                                  ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  Kết hợp Column và Row:                                       ║
 * ║  → Column xếp các Row theo chiều dọc                          ║
 * ║  → Mỗi Row xếp các Text theo chiều ngang                      ║
 * ║                                                               ║
 * ║  Spacer:                                                      ║
 * ║  → Tạo khoảng trống giữa các thành phần                       ║
 * ║  → height(16.dp) = khoảng trống cao 16dp                      ║
 * ║  → width(16.dp) = khoảng trống rộng 16dp                      ║
 * ║                                                               ║
 * ║  Cấu trúc:                                                    ║
 * ║  Column                                                       ║
 * ║    ├── Text (Tiêu đề)                                         ║
 * ║    ├── Spacer                                                 ║
 * ║    ├── Row                                                    ║
 * ║    │     ├── Text (Label)                                     ║
 * ║    │     └── Text (Value)                                     ║
 * ║    ├── Spacer                                                 ║
 * ║    └── Row ...                                                ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm Row cho "Số điện thoại"
 * 2. Thêm Row cho "Địa chỉ"  
 * 3. Thay đổi thông tin thành thông tin của bạn
 */
