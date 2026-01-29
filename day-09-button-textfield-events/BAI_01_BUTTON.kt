/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 09 - BÀI 1: BUTTON CƠ BẢN                                ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Button đơn giản
                Button(
                    onClick = {
                        // Code chạy khi nhấn nút
                        Toast.makeText(context, "Bạn đã nhấn nút!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Nhấn vào đây")
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
 * ║  Button(onClick = { ... }) { ... }                            ║
 * ║  └── onClick = { }  → Code chạy khi nhấn nút                  ║
 * ║  └── { Text(...) }  → Nội dung hiển thị trên nút              ║
 * ║                                                               ║
 * ║  Toast.makeText(context, "...", Toast.LENGTH_SHORT).show()    ║
 * ║  → Hiển thị thông báo nhỏ ở dưới màn hình                     ║
 * ║                                                               ║
 * ║  LocalContext.current                                         ║
 * ║  → Lấy context để dùng cho Toast                              ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi text trên nút thành "Click Me"
 * 2. Thay đổi thông báo Toast thành "Hello World"
 * 3. Thêm 1 Button nữa bên dưới với thông báo khác
 */
