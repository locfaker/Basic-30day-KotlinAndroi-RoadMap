/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 08: JETPACK COMPOSE CƠ BẢN                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  BÀI 3: XẾP NGANG VỚI ROW                                     ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Row = xếp các thành phần theo chiều NGANG
            Row {
                Text(
                    text = "A ",
                    fontSize = 24.sp,
                    color = Color.Red
                )
                
                Text(
                    text = "B ",
                    fontSize = 24.sp,
                    color = Color.Green
                )
                
                Text(
                    text = "C",
                    fontSize = 24.sp,
                    color = Color.Blue
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
 * ║  Row { ... }                                                  ║
 * ║  → Container xếp các thành phần theo chiều NGANG              ║
 * ║  → Giống như xếp các chữ cạnh nhau: A B C                     ║
 * ║                                                               ║
 * ║  SO SÁNH:                                                     ║
 * ║  Column = xếp DỌC (↓)                                         ║
 * ║  Row = xếp NGANG (→)                                          ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm Text "D" màu vàng (Color.Yellow) vào Row
 * 2. Thay đổi nội dung thành các emoji: "🍎 " "🍊 " "🍋"
 */
