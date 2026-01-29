/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 12 - BÀI 1: REMEMBER                                     ║
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // ✅ ĐÚNG: có remember
                var countWithRemember by remember { mutableStateOf(0) }
                
                Text("Với remember: $countWithRemember", fontSize = 20.sp)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { countWithRemember++ }) {
                    Text("Tăng")
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "⚠️ Lưu ý: Nếu không có 'remember', " +
                           "giá trị sẽ reset về 0 mỗi lần UI rebuild",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH REMEMBER:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  KHÔNG CÓ remember:                                           ║
 * ║  var count = mutableStateOf(0)                                ║
 * ║  → Mỗi lần Compose rebuild UI, biến được tạo mới              ║
 * ║  → Giá trị luôn reset về 0                                    ║
 * ║                                                               ║
 * ║  CÓ remember:                                                 ║
 * ║  var count by remember { mutableStateOf(0) }                  ║
 * ║  → Compose "nhớ" giá trị qua các lần rebuild                  ║
 * ║  → Giá trị được giữ nguyên                                    ║
 * ║                                                               ║
 * ║  KHI NÀO COMPOSE REBUILD?                                     ║
 * ║  → Khi state thay đổi                                         ║
 * ║  → Khi parent composable rebuild                              ║
 * ║                                                               ║
 * ║  CÚ PHÁP:                                                     ║
 * ║  var tên by remember { mutableStateOf(giáTrịBanĐầu) }         ║
 * ║                                                               ║
 * ║  "by" là delegate trong Kotlin, giúp dùng biến trực tiếp      ║
 * ║  không cần .value                                             ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm nút "Giảm" và "Reset"
 * 2. Thêm biến name với giá trị ban đầu là tên của bạn
 * 3. Hiển thị name và cho phép thay đổi bằng TextField
 */
