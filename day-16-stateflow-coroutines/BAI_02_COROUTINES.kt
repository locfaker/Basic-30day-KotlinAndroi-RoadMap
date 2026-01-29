/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 16 - BÀI 2: COROUTINES CƠ BẢN                            ║
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// UI State
data class LoadingUiState(
    val count: Int = 0,
    val isLoading: Boolean = false,
    val message: String = ""
)

// ===== VIEWMODEL VỚI COROUTINES =====
class LoadingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoadingUiState())
    val uiState = _uiState.asStateFlow()
    
    // Hàm async để load data
    fun loadData() {
        // viewModelScope = coroutine scope của ViewModel
        // Tự động cancel khi ViewModel bị destroy
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true, 
                message = "Đang tải..."
            )
            
            // Giả lập delay network 2 giây
            delay(2000)
            
            _uiState.value = _uiState.value.copy(
                count = _uiState.value.count + 10,
                isLoading = false,
                message = "Đã tải xong!"
            )
        }
    }
    
    // Hàm increment nhanh (không async)
    fun increment() {
        _uiState.value = _uiState.value.copy(
            count = _uiState.value.count + 1
        )
    }
}

// ===== MAIN =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LoadingViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Coroutines Demo", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Hiển thị count
                Text("${uiState.count}", fontSize = 72.sp, fontWeight = FontWeight.Bold)
                
                // Message
                Text(uiState.message, color = MaterialTheme.colorScheme.primary)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Loading indicator
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Buttons
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { viewModel.increment() }) {
                        Text("+1 (sync)")
                    }
                    
                    Button(
                        onClick = { viewModel.loadData() },
                        enabled = !uiState.isLoading
                    ) {
                        Text("+10 (async 2s)")
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Giải thích
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🔍 Khác biệt:", fontWeight = FontWeight.Bold)
                        Text("+1 (sync): Thay đổi ngay lập tức")
                        Text("+10 (async): Chờ 2 giây, có loading")
                        Text("")
                        Text("Thử nhấn +10, sau đó nhấn +1 nhiều lần")
                        Text("→ UI vẫn responsive, không bị đóng băng!")
                    }
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH COROUTINES:                                       ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  viewModelScope.launch { }                                    ║
 * ║  → Chạy code trong background (không block UI)                ║
 * ║  → Tự động cancel khi ViewModel bị destroy                    ║
 * ║                                                               ║
 * ║  delay(2000)                                                  ║
 * ║  → "Tạm dừng" coroutine 2 giây                                ║
 * ║  → KHÔNG block UI thread                                      ║
 * ║  → Chỉ dùng được trong suspend function hoặc coroutine        ║
 * ║                                                               ║
 * ║  TẠI SAO CẦN COROUTINES?                                      ║
 * ║  → Network calls mất thời gian (1-5 giây)                     ║
 * ║  → Nếu chạy trên UI thread → App bị đóng băng                 ║
 * ║  → Coroutines chạy background, UI vẫn mượt                    ║
 * ║                                                               ║
 * ║  FLOW:                                                        ║
 * ║  1. User nhấn button                                          ║
 * ║  2. viewModelScope.launch {} bắt đầu coroutine                ║
 * ║  3. isLoading = true (UI hiện loading)                        ║
 * ║  4. delay(2000) - chờ (UI vẫn responsive)                     ║
 * ║  5. isLoading = false, count + 10 (UI update kết quả)         ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi delay thành 3000 (3 giây)
 * 2. Thêm nút "Cancel" để hủy loading
 * 3. Hiển thị thời gian đã loading (đếm giây)
 */
