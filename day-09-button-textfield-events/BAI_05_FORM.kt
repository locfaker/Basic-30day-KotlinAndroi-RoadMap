/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 09 - BÀI 5: FORM ĐĂNG NHẬP                               ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Các state cho form
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }
            var isSuccess by remember { mutableStateOf(false) }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Tiêu đề
                Text(
                    text = "Đăng Nhập",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // TextField Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // TextField Password (ẩn ký tự)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mật khẩu") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Nút đăng nhập
                Button(
                    onClick = {
                        // Kiểm tra validation
                        when {
                            email.isEmpty() -> {
                                message = "Vui lòng nhập email"
                                isSuccess = false
                            }
                            password.isEmpty() -> {
                                message = "Vui lòng nhập mật khẩu"
                                isSuccess = false
                            }
                            password.length < 6 -> {
                                message = "Mật khẩu phải có ít nhất 6 ký tự"
                                isSuccess = false
                            }
                            else -> {
                                message = "Đăng nhập thành công! Xin chào $email"
                                isSuccess = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Đăng nhập", fontSize = 16.sp)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Hiển thị thông báo
                if (message.isNotEmpty()) {
                    Text(
                        text = message,
                        color = if (isSuccess) Color.Green else Color.Red,
                        fontSize = 14.sp
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
 * ║  OutlinedTextField:                                           ║
 * ║  → TextField với viền (đẹp hơn TextField thường)              ║
 * ║                                                               ║
 * ║  PasswordVisualTransformation():                              ║
 * ║  → Ẩn ký tự password thành dấu chấm (•••••)                   ║
 * ║                                                               ║
 * ║  singleLine = true:                                           ║
 * ║  → Chỉ cho phép nhập 1 dòng, Enter không xuống dòng           ║
 * ║                                                               ║
 * ║  Validation trong onClick:                                    ║
 * ║  → Kiểm tra dữ liệu trước khi xử lý                           ║
 * ║  → email.isEmpty() = kiểm tra rỗng                            ║
 * ║  → password.length < 6 = kiểm tra độ dài                      ║
 * ║                                                               ║
 * ║  Nhiều state:                                                 ║
 * ║  → email, password: lưu giá trị nhập                          ║
 * ║  → message: lưu thông báo                                     ║
 * ║  → isSuccess: true/false để đổi màu thông báo                 ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm TextField "Nhập lại mật khẩu" và kiểm tra khớp
 * 2. Thêm validation kiểm tra email phải chứa "@"
 * 3. Thêm TextField "Họ tên" ở trên cùng
 * 4. Thêm nút "Xóa" để reset tất cả các field
 */
