/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 09 - BÀI 3: STATE (TRẠNG THÁI) - RẤT QUAN TRỌNG!         ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
            // STATE = Trạng thái
            // Khi state thay đổi → UI tự động cập nhật
            var count by remember { mutableStateOf(0) }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Hiển thị số đếm
                Text(
                    text = "$count",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Button tăng số
                Button(
                    onClick = {
                        count = count + 1  // Thay đổi state → UI cập nhật
                    }
                ) {
                    Text("Tăng +1", fontSize = 18.sp)
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH STATE - KHÁI NIỆM QUAN TRỌNG NHẤT!                ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  STATE là gì?                                                 ║
 * ║  → Là "trạng thái" của UI                                     ║
 * ║  → Ví dụ: số đếm, text đã nhập, checkbox đã tick chưa...      ║
 * ║                                                               ║
 * ║  var count by remember { mutableStateOf(0) }                  ║
 * ║  └── var count         → Tên biến                             ║
 * ║  └── by remember       → Nhớ giá trị khi UI rebuild           ║
 * ║  └── mutableStateOf(0) → Giá trị ban đầu là 0, có thể đổi     ║
 * ║                                                               ║
 * ║  QUY TẮC VÀNG:                                                ║
 * ║  Khi state thay đổi → Compose TỰ ĐỘNG vẽ lại UI               ║
 * ║                                                               ║
 * ║  Ví dụ:                                                       ║
 * ║  count = 0 → Text hiển thị "0"                                ║
 * ║  Nhấn button → count = 1                                      ║
 * ║  → Compose tự động cập nhật Text thành "1"                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm Button "Giảm -1" để giảm số
 * 2. Thêm Button "Reset" để đặt count = 0
 * 3. Thay đổi màu chữ: xanh khi count > 0, đỏ khi count < 0
 */
