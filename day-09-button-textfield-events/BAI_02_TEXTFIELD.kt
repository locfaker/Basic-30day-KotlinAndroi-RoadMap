/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 09 - BÀI 2: TEXTFIELD CƠ BẢN                             ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Biến lưu text người dùng nhập
            var text by remember { mutableStateOf("") }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // TextField để nhập text
                TextField(
                    value = text,                    // Giá trị hiện tại
                    onValueChange = { text = it },   // Cập nhật khi user gõ
                    label = { Text("Nhập tên của bạn") }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Hiển thị text đã nhập
                Text(
                    text = "Bạn đã nhập: $text",
                    fontSize = 18.sp
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
 * ║  var text by remember { mutableStateOf("") }                  ║
 * ║  → Tạo biến "text" có thể thay đổi                            ║
 * ║  → remember = nhớ giá trị khi UI rebuild                      ║
 * ║  → mutableStateOf("") = giá trị ban đầu là rỗng ""            ║
 * ║                                                               ║
 * ║  TextField(                                                   ║
 * ║      value = text,             → Hiển thị giá trị hiện tại    ║
 * ║      onValueChange = { },      → Gọi khi user gõ chữ          ║
 * ║      label = { Text(...) }     → Placeholder/label            ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  { text = it }                                                ║
 * ║  → "it" là chữ mới user vừa gõ                                ║
 * ║  → Gán vào biến text để cập nhật                              ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi label thành "Nhập email"
 * 2. Thêm TextField thứ 2 cho "Nhập số điện thoại"
 * 3. Hiển thị cả 2 giá trị bên dưới
 */
