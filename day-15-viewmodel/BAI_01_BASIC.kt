/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 15 - BÀI 1: VIEWMODEL LÀ GÌ                              ║
 * ║                                                               ║
 * ║  ⚠️ THÊM DEPENDENCY TRƯỚC:                                    ║
 * ║  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
 * ║                                                               ║
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// ===== VIEWMODEL =====
class CounterViewModel : ViewModel() {
    // State trong ViewModel
    var count by mutableStateOf(0)
        private set  // Chỉ ViewModel mới thay đổi được
    
    // Các hàm thay đổi state
    fun increment() {
        count++
    }
    
    fun decrement() {
        count--
    }
    
    fun reset() {
        count = 0
    }
}

// ===== MAIN ACTIVITY =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Lấy ViewModel instance
            val viewModel: CounterViewModel = viewModel()
            
            CounterScreen(viewModel)
        }
    }
}

@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Counter với ViewModel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "${viewModel.count}",
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { viewModel.decrement() }) {
                Text("-1")
            }
            
            OutlinedButton(onClick = { viewModel.reset() }) {
                Text("Reset")
            }
            
            Button(onClick = { viewModel.increment() }) {
                Text("+1")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Hướng dẫn test
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🧪 THỬ NGHIỆM:", fontWeight = FontWeight.Bold)
                Text("1. Tăng số lên 5-10")
                Text("2. Xoay màn hình (Ctrl+F11 trên emulator)")
                Text("3. Số vẫn giữ nguyên! (nhờ ViewModel)")
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH VIEWMODEL:                                        ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  VẤN ĐỀ KHÔNG CÓ VIEWMODEL:                                   ║
 * ║  → Khi xoay màn hình, Activity bị destroy rồi tạo lại         ║
 * ║  → State trong Composable bị reset về ban đầu                 ║
 * ║  → User mất dữ liệu đang làm việc                             ║
 * ║                                                               ║
 * ║  VIEWMODEL GIẢI QUYẾT:                                        ║
 * ║  → ViewModel tồn tại lâu hơn Activity                         ║
 * ║  → Khi xoay màn hình, ViewModel vẫn còn                       ║
 * ║  → State được bảo toàn                                        ║
 * ║                                                               ║
 * ║  CÚ PHÁP:                                                     ║
 * ║  1. Tạo class kế thừa ViewModel():                            ║
 * ║     class MyViewModel : ViewModel() { ... }                   ║
 * ║                                                               ║
 * ║  2. Lấy instance trong Composable:                            ║
 * ║     val viewModel: MyViewModel = viewModel()                  ║
 * ║                                                               ║
 * ║  3. Dùng state và hàm từ viewModel:                           ║
 * ║     viewModel.count, viewModel.increment()                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm hàm incrementBy(n: Int) để tăng n đơn vị
 * 2. Thêm state name và hàm setName(newName: String)
 * 3. Xoay màn hình và kiểm tra cả count và name đều được giữ
 */
