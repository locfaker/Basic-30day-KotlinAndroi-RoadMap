/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 08: JETPACK COMPOSE CƠ BẢN                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  BÀI 4: MODIFIER - THÊM PADDING VÀ KÍCH THƯỚC                 ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                // Modifier dùng để thay đổi giao diện
                modifier = Modifier
                    .fillMaxSize()           // Chiếm toàn bộ màn hình
                    .background(Color.LightGray)  // Màu nền xám
                    .padding(16.dp)          // Padding 16dp xung quanh
            ) {
                Text(
                    text = "Text có padding",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.Yellow)  // Nền vàng cho Text
                        .padding(8.dp)             // Padding cho Text
                )
                
                Text(
                    text = "Text không có modifier",
                    fontSize = 20.sp
                )
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH:                                                  ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  Modifier là gì?                                              ║
 * ║  → Dùng để thay đổi giao diện của thành phần                  ║
 * ║  → Thêm kích thước, màu nền, padding, margin, v.v.            ║
 * ║                                                               ║
 * ║  Các Modifier phổ biến:                                       ║
 * ║  • fillMaxSize() → Chiếm toàn bộ không gian                   ║
 * ║  • background(Color) → Màu nền                                ║
 * ║  • padding(dp) → Khoảng cách bên trong                        ║
 * ║                                                               ║
 * ║  Lưu ý: dp = density-independent pixels                       ║
 * ║  16.dp = 16 đơn vị pixel độc lập với mật độ màn hình          ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi padding của Column thành 32.dp
 * 2. Thay đổi background của Column thành Color.White
 * 3. Thêm .padding(16.dp) cho Text thứ 2
 */
