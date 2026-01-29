/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 13 - BÀI 3: BACK STACK                                   ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            NavHost(navController, startDestination = "a") {
                composable("a") {
                    ScreenTemplate(
                        name = "A",
                        color = Color(0xFFE53935),
                        info = "Màn hình đầu tiên (startDestination)",
                        onNext = { navController.navigate("b") },
                        onBack = null  // Không có back vì đây là màn hình đầu
                    )
                }
                
                composable("b") {
                    ScreenTemplate(
                        name = "B",
                        color = Color(0xFF43A047),
                        info = "Stack: A → B",
                        onNext = { navController.navigate("c") },
                        onBack = { navController.popBackStack() }
                    )
                }
                
                composable("c") {
                    ScreenTemplate(
                        name = "C",
                        color = Color(0xFF1976D2),
                        info = "Stack: A → B → C",
                        onNext = { 
                            // popBackStack đến route cụ thể
                            navController.popBackStack("a", inclusive = false)
                        },
                        onBack = { navController.popBackStack() },
                        nextLabel = "Về A (xóa B, C)"
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenTemplate(
    name: String,
    color: Color,
    info: String,
    onNext: (() -> Unit)?,
    onBack: (() -> Unit)?,
    nextLabel: String = "Đi tiếp →"
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Màn hình $name",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(info, color = Color.Gray)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        if (onNext != null) {
            Button(onClick = onNext) {
                Text(nextLabel)
            }
        }
        
        if (onBack != null) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = onBack) {
                Text("← Quay lại")
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH BACK STACK:                                       ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  BACK STACK là gì?                                            ║
 * ║  → "Chồng" các màn hình đã đi qua                             ║
 * ║  → Từ A đi B đi C: Stack = [A, B, C]                          ║
 * ║  → Nhấn back: quay lại màn hình trước                         ║
 * ║                                                               ║
 * ║  popBackStack():                                              ║
 * ║  → Xóa màn hình hiện tại, quay lại màn hình trước             ║
 * ║  → Ở C gọi popBackStack() → về B                              ║
 * ║                                                               ║
 * ║  popBackStack("route", inclusive):                            ║
 * ║  → Xóa tất cả cho đến route chỉ định                          ║
 * ║  → inclusive = true: xóa cả route đó                          ║
 * ║  → inclusive = false: giữ lại route đó                        ║
 * ║                                                               ║
 * ║  VÍ DỤ: Stack = [A, B, C]                                     ║
 * ║  popBackStack("a", inclusive = false)                         ║
 * ║  → Xóa B, C, giữ lại A                                        ║
 * ║  → Stack = [A]                                                ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm màn hình D sau C
 * 2. Từ D, thêm nút "Về B" dùng popBackStack("b", false)
 * 3. Thử inclusive = true xem khác gì
 */
