/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 11 - BÀI 2: LAZYROW                                      ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Danh mục",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // LazyRow = Row có thể scroll ngang
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(10) { index ->
                        // Mỗi item là 1 chip
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF1976D2),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "Mục ${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Màu sắc",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // LazyRow với màu sắc
                val colors = listOf(
                    Color.Red, Color.Green, Color.Blue, 
                    Color.Yellow, Color.Cyan, Color.Magenta
                )
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(colors.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(colors[index], RoundedCornerShape(12.dp))
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
 * ║  LazyRow = Row có thể scroll ngang                            ║
 * ║  → Giống LazyColumn nhưng scroll theo chiều ngang             ║
 * ║                                                               ║
 * ║  Thường dùng cho:                                             ║
 * ║  → Danh mục ngang (category chips)                            ║
 * ║  → Gallery ảnh ngang                                          ║
 * ║  → Tab hoặc filter                                            ║
 * ║                                                               ║
 * ║  horizontalArrangement = Arrangement.spacedBy(12.dp)          ║
 * ║  → Khoảng cách giữa các item theo chiều ngang                 ║
 * ║                                                               ║
 * ║  Truy cập List bằng index:                                    ║
 * ║  val colors = listOf(Color.Red, Color.Green, ...)             ║
 * ║  items(colors.size) { index ->                                ║
 * ║      colors[index]  // Lấy màu tại vị trí index               ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm nhiều màu hơn vào list colors
 * 2. Thêm Text tên màu bên trong mỗi Box
 * 3. Tạo LazyRow thứ 3 với các số từ 1-20
 */
