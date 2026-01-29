/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 11 - BÀI 4: APP DANH BẠ                                  ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class cho liên hệ
data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val contacts = listOf(
                Contact(1, "Nguyễn Văn An", "0901234567", "an@gmail.com"),
                Contact(2, "Trần Thị Bình", "0912345678", "binh@gmail.com"),
                Contact(3, "Lê Văn Cường", "0923456789", "cuong@gmail.com"),
                Contact(4, "Phạm Thị Dung", "0934567890", "dung@gmail.com"),
                Contact(5, "Hoàng Văn Em", "0945678901", "em@gmail.com"),
                Contact(6, "Võ Thị Phượng", "0956789012", "phuong@gmail.com"),
                Contact(7, "Đặng Văn Giang", "0967890123", "giang@gmail.com"),
                Contact(8, "Bùi Thị Hoa", "0978901234", "hoa@gmail.com")
            )
            
            ContactListScreen(contacts)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(contacts: List<Contact>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh bạ") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts, key = { it.id }) { contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar (chữ cái đầu)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF1976D2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Thông tin
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(contact.phone, fontSize = 14.sp, color = Color.Gray)
                }
            }
            
            // Nút gọi
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = "Call",
                    tint = Color(0xFF43A047)
                )
            }
            
            // Nút nhắn tin
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color(0xFF1976D2)
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
 * ║  Đây là ví dụ hoàn chỉnh kết hợp:                             ║
 * ║  1. Data class Contact                                        ║
 * ║  2. LazyColumn với items(list)                                ║
 * ║  3. Scaffold với TopAppBar                                    ║
 * ║  4. Card với Row layout                                       ║
 * ║  5. Avatar hình tròn với chữ cái đầu                          ║
 * ║  6. IconButton cho các action                                 ║
 * ║                                                               ║
 * ║  items(contacts, key = { it.id })                             ║
 * ║  → key giúp Compose theo dõi item khi list thay đổi           ║
 * ║  → Quan trọng khi có add/remove/reorder item                  ║
 * ║                                                               ║
 * ║  contact.name.first().toString()                              ║
 * ║  → Lấy ký tự đầu tiên của tên làm avatar                      ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm nhiều liên hệ hơn (10-15 người)
 * 2. Đổi màu avatar khác nhau cho mỗi người
 * 3. Thêm field "isFavorite" và hiển thị icon sao nếu là yêu thích
 * 4. Thêm FloatingActionButton để "Thêm liên hệ mới"
 */
