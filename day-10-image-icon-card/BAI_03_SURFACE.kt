/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 10 - BÀI 3: SURFACE VÀ SHAPE                             ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text("Các loại Shape:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Hình vuông góc vuông
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Vuông", color = Color.White)
                    }
                    
                    // Hình bo góc
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Blue, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Bo góc", color = Color.White)
                    }
                    
                    // Hình tròn
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Green, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tròn", color = Color.White)
                    }
                }
                
                // Surface với shadow
                Text("Surface với shadow:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Surface")
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
 * ║  SHAPE - Hình dạng:                                           ║
 * ║  → RoundedCornerShape(16.dp) = bo góc 16dp                    ║
 * ║  → CircleShape = hình tròn                                    ║
 * ║  → RectangleShape = hình chữ nhật (mặc định)                  ║
 * ║                                                               ║
 * ║  Cách dùng với background:                                    ║
 * ║  .background(Color.Blue, RoundedCornerShape(16.dp))           ║
 * ║                                                               ║
 * ║  SURFACE:                                                     ║
 * ║  → Như Card nhưng linh hoạt hơn                               ║
 * ║  → shape = hình dạng                                          ║
 * ║  → shadowElevation = độ cao bóng                              ║
 * ║  → color = màu nền                                            ║
 * ║                                                               ║
 * ║  BOX:                                                         ║
 * ║  → Container đơn giản, xếp chồng các thành phần               ║
 * ║  → contentAlignment = căn giữa nội dung                       ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo Box hình oval với RoundedCornerShape(40.dp) và size(120.dp, 80.dp)
 * 2. Tạo Surface hình tròn với CircleShape
 * 3. Thử nhiều mức shadowElevation khác nhau (2.dp, 8.dp, 16.dp)
 */
