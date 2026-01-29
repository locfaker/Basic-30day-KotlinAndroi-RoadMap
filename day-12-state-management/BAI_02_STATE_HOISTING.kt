/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 12 - BÀI 2: STATE HOISTING                               ║
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // State được định nghĩa ở ĐÂY (parent)
            var count by remember { mutableStateOf(0) }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "State Hoisting Demo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Truyền state xuống child
                CounterDisplay(value = count)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Truyền callback để thay đổi state
                CounterButtons(
                    onIncrement = { count++ },
                    onDecrement = { count-- },
                    onReset = { count = 0 }
                )
            }
        }
    }
}

// ===== STATELESS COMPOSABLE =====
// Không có state riêng, chỉ nhận value từ parent

@Composable
fun CounterDisplay(value: Int) {
    Text(
        text = "$value",
        fontSize = 72.sp,
        fontWeight = FontWeight.Bold,
        color = when {
            value > 0 -> Color(0xFF43A047)
            value < 0 -> Color(0xFFE53935)
            else -> Color.Gray
        }
    )
}

// ===== STATELESS COMPOSABLE VỚI CALLBACKS =====
// Không thay đổi state trực tiếp, gọi callback từ parent

@Composable
fun CounterButtons(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = onDecrement) {
            Text("-1")
        }
        
        OutlinedButton(onClick = onReset) {
            Text("Reset")
        }
        
        Button(onClick = onIncrement) {
            Text("+1")
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH STATE HOISTING:                                   ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  STATE HOISTING là gì?                                        ║
 * ║  → "Nâng" state lên parent composable                         ║
 * ║  → Child composable không có state riêng (stateless)          ║
 * ║  → Child nhận value và callback từ parent                     ║
 * ║                                                               ║
 * ║  TẠI SAO CẦN STATE HOISTING?                                  ║
 * ║  1. Dễ test (stateless dễ test hơn)                           ║
 * ║  2. Dễ tái sử dụng                                            ║
 * ║  3. Nhiều child có thể chia sẻ cùng state                     ║
 * ║  4. Parent kiểm soát logic                                    ║
 * ║                                                               ║
 * ║  PATTERN:                                                     ║
 * ║  Parent:  var state by remember { ... }                       ║
 * ║           ChildA(value = state)                               ║
 * ║           ChildB(onAction = { state = ... })                  ║
 * ║                                                               ║
 * ║  Child (Stateless):                                           ║
 * ║  @Composable                                                  ║
 * ║  fun ChildA(value: Int) { Text("$value") }                    ║
 * ║                                                               ║
 * ║  @Composable                                                  ║
 * ║  fun ChildB(onAction: () -> Unit) {                           ║
 * ║      Button(onClick = onAction) { ... }                       ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm composable CounterInfo(value) hiển thị "Dương/Âm/Số 0"
 * 2. Thêm nút "+10" và "-10" vào CounterButtons
 * 3. Tách CounterDisplay và CounterButtons vào file riêng (nếu muốn)
 */
