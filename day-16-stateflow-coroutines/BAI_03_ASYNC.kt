/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 16 - BÀI 3: XỬ LÝ BẤT ĐỒNG BỘ                            ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlin.random.Random

// Data
data class User(val id: Int, val name: String, val email: String)

// UI State
sealed class UsersUiState {
    object Loading : UsersUiState()
    data class Success(val users: List<User>) : UsersUiState()
    data class Error(val message: String) : UsersUiState()
}

// ===== VIEWMODEL =====
class UsersViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState = _uiState.asStateFlow()
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UsersUiState.Loading
            
            delay(1500) // Giả lập network
            
            // Random success/error để demo
            if (Random.nextBoolean()) {
                val users = listOf(
                    User(1, "Nguyễn Văn An", "an@email.com"),
                    User(2, "Trần Thị Bình", "binh@email.com"),
                    User(3, "Lê Văn Cường", "cuong@email.com"),
                    User(4, "Phạm Thị Dung", "dung@email.com")
                )
                _uiState.value = UsersUiState.Success(users)
            } else {
                _uiState.value = UsersUiState.Error("Lỗi kết nối mạng!")
            }
        }
    }
}

// ===== MAIN =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: UsersViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Async Demo") }
                    )
                }
            ) { padding ->
                // Xử lý các trạng thái khác nhau
                when (val state = uiState) {
                    is UsersUiState.Loading -> {
                        LoadingContent(padding)
                    }
                    is UsersUiState.Success -> {
                        SuccessContent(padding, state.users) { viewModel.loadUsers() }
                    }
                    is UsersUiState.Error -> {
                        ErrorContent(padding, state.message) { viewModel.loadUsers() }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingContent(padding: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Đang tải dữ liệu...")
        }
    }
}

@Composable
fun SuccessContent(padding: PaddingValues, users: List<User>, onRefresh: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
    ) {
        Text(
            "Danh sách users (${users.size})",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(users) { user ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(user.name, fontWeight = FontWeight.Bold)
                        Text(user.email, color = Color.Gray)
                    }
                }
            }
        }
        
        Button(
            onClick = onRefresh,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tải lại")
        }
    }
}

@Composable
fun ErrorContent(padding: PaddingValues, message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("❌", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Thử lại")
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH XỬ LÝ BẤT ĐỒNG BỘ:                                ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  SEALED CLASS cho UI State:                                   ║
 * ║  sealed class UsersUiState {                                  ║
 * ║      object Loading : UsersUiState()                          ║
 * ║      data class Success(...) : UsersUiState()                 ║
 * ║      data class Error(...) : UsersUiState()                   ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  TẠI SAO DÙNG SEALED CLASS?                                   ║
 * ║  → Định nghĩa TẤT CẢ các trạng thái có thể xảy ra             ║
 * ║  → Compiler đảm bảo khi dùng when { } phải xử lý hết          ║
 * ║  → Không bao giờ bỏ sót trường hợp                            ║
 * ║                                                               ║
 * ║  PATTERN:                                                     ║
 * ║  when (state) {                                               ║
 * ║      is Loading -> ShowLoading()                              ║
 * ║      is Success -> ShowData(state.users)                      ║
 * ║      is Error -> ShowError(state.message)                     ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  FLOW:                                                        ║
 * ║  1. App khởi động → Loading                                   ║
 * ║  2. Fetch data → delay → Success hoặc Error                   ║
 * ║  3. User nhấn Retry → quay lại Loading                        ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm trạng thái Empty khi list rỗng
 * 2. Thay Random success/error bằng random 80% success
 * 3. Thêm Pull-to-Refresh để reload data
 */
