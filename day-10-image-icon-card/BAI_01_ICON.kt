/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 10 - BÀI 1: ICON                                         ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Các Icon phổ biến:", fontSize = 20.sp)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Dùng Row để xếp icon ngang
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Icon Home
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(48.dp),
                        tint = Color.Blue
                    )
                    
                    // Icon Favorite (trái tim)
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(48.dp),
                        tint = Color.Red
                    )
                    
                    // Icon Settings (bánh răng)
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier.size(48.dp),
                        tint = Color.Gray
                    )
                    
                    // Icon Person
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person",
                        modifier = Modifier.size(48.dp),
                        tint = Color.Green
                    )
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
 * ║  Icon() - Hiển thị icon                                       ║
 * ║  → imageVector = Icons.Default.XXX  (tên icon)                ║
 * ║  → contentDescription = "..."       (mô tả cho accessibility) ║
 * ║  → modifier = Modifier.size(48.dp)  (kích thước)              ║
 * ║  → tint = Color.XXX                 (màu icon)                ║
 * ║                                                               ║
 * ║  Các Icon phổ biến:                                           ║
 * ║  Icons.Default.Home       → Nhà                               ║
 * ║  Icons.Default.Favorite   → Trái tim                          ║
 * ║  Icons.Default.Settings   → Bánh răng                         ║
 * ║  Icons.Default.Person     → Người                             ║
 * ║  Icons.Default.Search     → Kính lúp                          ║
 * ║  Icons.Default.Add        → Dấu cộng                          ║
 * ║  Icons.Default.Delete     → Thùng rác                         ║
 * ║  Icons.Default.Email      → Email                             ║
 * ║  Icons.Default.Phone      → Điện thoại                        ║
 * ║  Icons.Default.Star       → Ngôi sao                          ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm icon Search và Star vào Row
 * 2. Thay đổi size thành 64.dp
 * 3. Thêm Text bên dưới mỗi icon (ví dụ: "Home", "Favorite"...)
 */
