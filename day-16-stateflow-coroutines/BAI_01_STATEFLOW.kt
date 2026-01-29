/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 16 - BÀI 1: STATEFLOW                                    ║
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// UI State
data class CounterUiState(
    val count: Int = 0,
    val message: String = ""
)

// ===== VIEWMODEL VỚI STATEFLOW =====
class CounterViewModel : ViewModel() {
    // MutableStateFlow - có thể thay đổi (private)
    private val _uiState = MutableStateFlow(CounterUiState())
    
    // StateFlow - chỉ đọc (public)
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()
    
    fun increment() {
        _uiState.value = _uiState.value.copy(
            count = _uiState.value.count + 1,
            message = "Đã tăng!"
        )
    }
    
    fun decrement() {
        _uiState.value = _uiState.value.copy(
            count = _uiState.value.count - 1,
            message = "Đã giảm!"
        )
    }
    
    fun reset() {
        _uiState.value = CounterUiState(message = "Đã reset!")
    }
}

// ===== MAIN =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: CounterViewModel = viewModel()
            
            // Collect StateFlow thành State
            val uiState by viewModel.uiState.collectAsState()
            
            CounterScreen(
                uiState = uiState,
                onIncrement = { viewModel.increment() },
                onDecrement = { viewModel.decrement() },
                onReset = { viewModel.reset() }
            )
        }
    }
}

@Composable
fun CounterScreen(
    uiState: CounterUiState,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("StateFlow Demo", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("${uiState.count}", fontSize = 72.sp, fontWeight = FontWeight.Bold)
        
        if (uiState.message.isNotEmpty()) {
            Text(uiState.message, color = MaterialTheme.colorScheme.primary)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onDecrement) { Text("-1") }
            OutlinedButton(onClick = onReset) { Text("Reset") }
            Button(onClick = onIncrement) { Text("+1") }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH STATEFLOW:                                        ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  SO SÁNH mutableStateOf vs StateFlow:                         ║
 * ║                                                               ║
 * ║  mutableStateOf:                                              ║
 * ║  → Simple, dùng cho UI state đơn giản                         ║
 * ║  → var state by mutableStateOf(...)                           ║
 * ║                                                               ║
 * ║  StateFlow:                                                   ║
 * ║  → Mạnh hơn, dùng cho app phức tạp                            ║
 * ║  → Có thể share giữa nhiều observers                          ║
 * ║  → Thread-safe                                                ║
 * ║  → Phù hợp với kiến trúc Clean Architecture                   ║
 * ║                                                               ║
 * ║  PATTERN:                                                     ║
 * ║  private val _uiState = MutableStateFlow(InitialState())      ║
 * ║  val uiState: StateFlow<UiState> = _uiState.asStateFlow()     ║
 * ║                                                               ║
 * ║  → _uiState (private, mutable) - ViewModel thay đổi           ║
 * ║  → uiState (public, read-only) - UI chỉ đọc                   ║
 * ║                                                               ║
 * ║  COLLECT trong Composable:                                    ║
 * ║  val uiState by viewModel.uiState.collectAsState()            ║
 * ║  → Chuyển Flow thành Compose State                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm field lastUpdated: Long (timestamp) vào UiState
 * 2. Cập nhật lastUpdated mỗi lần thay đổi count
 * 3. Hiển thị thời gian update dưới dạng "HH:mm:ss"
 */
