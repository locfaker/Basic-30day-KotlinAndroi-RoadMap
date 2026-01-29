/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 08: JETPACK COMPOSE CƠ BẢN                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  BÀI 1: CHỈ CÓ TEXT ĐƠN GIẢN                                  ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Chỉ 1 Text đơn giản
            Text(
                text = "Xin chào Compose!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH:                                                  ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  setContent { ... }                                           ║
 * ║  → Đặt nội dung UI cho màn hình                               ║
 * ║                                                               ║
 * ║  Text(...)                                                    ║
 * ║  → Hiển thị văn bản                                           ║
 * ║                                                               ║
 * ║  text = "..."                                                 ║
 * ║  → Nội dung văn bản                                           ║
 * ║                                                               ║
 * ║  fontSize = 24.sp                                             ║
 * ║  → Kích thước chữ (sp = scale-independent pixels)             ║
 * ║                                                               ║
 * ║  fontWeight = FontWeight.Bold                                 ║
 * ║  → Chữ đậm                                                    ║
 * ║                                                               ║
 * ║  color = Color.Blue                                           ║
 * ║  → Màu chữ xanh dương                                         ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi text thành tên của bạn
 * 2. Thay đổi fontSize thành 32.sp
 * 3. Thay đổi color thành Color.Red
 * 4. Thử fontWeight = FontWeight.Normal
 */
