/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 10 - BÀI 4: LÀM PROFILE CARD                             ║
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
                    .padding(16.dp)
            ) {
                // Profile Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar (hình tròn)
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color(0xFF1976D2), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar",
                                modifier = Modifier.size(60.dp),
                                tint = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Tên
                        Text(
                            text = "Nguyễn Văn A",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Chức vụ
                        Text(
                            text = "Android Developer",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Row các thông tin
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Email
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("a@gmail.com", fontSize = 12.sp, color = Color.Gray)
                            }
                            
                            // Phone
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("0901234567", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Nút Edit
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Chỉnh sửa Profile")
                        }
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
 * ║  Đây là ví dụ kết hợp tất cả những gì đã học:                 ║
 * ║                                                               ║
 * ║  1. Card với shape bo góc và shadow                           ║
 * ║  2. Box hình tròn làm avatar                                  ║
 * ║  3. Icon bên trong avatar                                     ║
 * ║  4. Text với các style khác nhau                              ║
 * ║  5. Row xếp ngang thông tin                                   ║
 * ║  6. Button với Icon                                           ║
 * ║                                                               ║
 * ║  Cấu trúc:                                                    ║
 * ║  Card                                                         ║
 * ║    └── Column (căn giữa)                                      ║
 * ║          ├── Box (avatar tròn)                                ║
 * ║          │     └── Icon                                       ║
 * ║          ├── Text (tên)                                       ║
 * ║          ├── Text (chức vụ)                                   ║
 * ║          ├── Row (thông tin)                                  ║
 * ║          │     ├── Row (email)                                ║
 * ║          │     └── Row (phone)                                ║
 * ║          └── Button                                           ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi thông tin thành của bạn
 * 2. Thêm thông tin địa chỉ với Icon.Default.LocationOn
 * 3. Thay đổi màu avatar thành màu khác
 * 4. Thêm chữ cái đầu tên (ví dụ "A") thay vì Icon Person
 */
