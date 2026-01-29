/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 10 - BÀI 2: CARD                                         ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card cơ bản
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Card cơ bản",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Đây là nội dung bên trong Card",
                            color = Color.Gray
                        )
                    }
                }
                
                // Card với shadow lớn hơn
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Card với shadow lớn",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "elevation = 8.dp tạo shadow đậm hơn",
                            color = Color.Gray
                        )
                    }
                }
                
                // Card với màu nền custom
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD) // Màu xanh nhạt
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Card màu xanh nhạt",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Dùng CardDefaults.cardColors()",
                            color = Color.Gray
                        )
                    }
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
 * ║  Card { } - Khung chứa có shadow và bo góc                    ║
 * ║  → Thường dùng để nhóm nội dung liên quan                     ║
 * ║  → Tự động có shadow nhẹ và bo góc                            ║
 * ║                                                               ║
 * ║  elevation = CardDefaults.cardElevation(8.dp)                 ║
 * ║  → Độ cao bóng đổ, số càng lớn shadow càng đậm                ║
 * ║                                                               ║
 * ║  colors = CardDefaults.cardColors(containerColor = ...)       ║
 * ║  → Thay đổi màu nền của Card                                  ║
 * ║                                                               ║
 * ║  Color(0xFFE3F2FD)                                            ║
 * ║  → Màu hex trong Android, 0xFF là opacity (FF = 100%)         ║
 * ║  → E3F2FD là mã màu xanh dương nhạt                           ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo Card màu vàng nhạt (0xFFFFF9C4)
 * 2. Thêm padding bên ngoài Card với Modifier.padding()
 * 3. Thử elevation = 16.dp xem shadow thay đổi thế nào
 */
