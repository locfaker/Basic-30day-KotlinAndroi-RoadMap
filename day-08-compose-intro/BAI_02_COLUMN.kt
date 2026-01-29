/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 08: JETPACK COMPOSE CƠ BẢN                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  BÀI 2: NHIỀU TEXT VỚI COLUMN                                 ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Column = xếp các thành phần theo chiều DỌC
            Column {
                Text(
                    text = "Dòng 1: Xin chào!",
                    fontSize = 20.sp,
                    color = Color.Blue
                )
                
                Text(
                    text = "Dòng 2: Tôi đang học Compose",
                    fontSize = 18.sp,
                    color = Color.Green
                )
                
                Text(
                    text = "Dòng 3: Compose rất dễ!",
                    fontSize = 16.sp,
                    color = Color.Red
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
 * ║  Column { ... }                                               ║
 * ║  → Container xếp các thành phần theo chiều DỌC                ║
 * ║  → Giống như xếp các dòng từ trên xuống dưới                  ║
 * ║                                                               ║
 * ║  Bên trong Column có thể có nhiều Text hoặc thành phần khác   ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm dòng Text thứ 4 với nội dung và màu khác
 * 2. Thử thay đổi thứ tự các Text
 */
