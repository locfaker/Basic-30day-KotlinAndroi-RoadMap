/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 09 - BÀI 4: LÀM COUNTER ĐẦY ĐỦ                           ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
            var count by remember { mutableStateOf(0) }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Tiêu đề
                Text(
                    text = "Counter App",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Số đếm - đổi màu theo giá trị
                Text(
                    text = "$count",
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        count > 0 -> Color.Green
                        count < 0 -> Color.Red
                        else -> Color.Gray
                    }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Row chứa các nút
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Nút giảm
                    Button(onClick = { count = count - 1 }) {
                        Text("-1", fontSize = 20.sp)
                    }
                    
                    // Nút tăng
                    Button(onClick = { count = count + 1 }) {
                        Text("+1", fontSize = 20.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Nút reset
                OutlinedButton(onClick = { count = 0 }) {
                    Text("Reset")
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
 * ║  Đây là ứng dụng Counter hoàn chỉnh với:                      ║
 * ║  1. Số đếm đổi màu theo giá trị (xanh/đỏ/xám)                 ║
 * ║  2. Nút +1 và -1                                              ║
 * ║  3. Nút Reset                                                 ║
 * ║                                                               ║
 * ║  when { } expression:                                         ║
 * ║  → Giống switch-case, trả về giá trị theo điều kiện           ║
 * ║                                                               ║
 * ║  color = when {                                               ║
 * ║      count > 0 -> Color.Green    // Nếu dương → xanh          ║
 * ║      count < 0 -> Color.Red      // Nếu âm → đỏ               ║
 * ║      else -> Color.Gray          // Nếu 0 → xám               ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  OutlinedButton:                                              ║
 * ║  → Button với viền thay vì nền                                ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm nút "+10" và "-10"
 * 2. Thêm Text hiển thị "Số dương" / "Số âm" / "Số 0"
 * 3. Thêm giới hạn: count không được nhỏ hơn -100 hoặc lớn hơn 100
 */
