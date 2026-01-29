/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 19 - BÀI 3: APP HIỂN THỊ DATA TỪ API                     ║
 * ║                                                               ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ║  (Đảm bảo đã thêm dependencies và permission)                 ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// ===== DATA =====
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

// ===== API =====
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

object ApiClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

// ===== UI STATE =====
sealed class UiState {
    object Loading : UiState()
    data class Success(val users: List<User>) : UiState()
    data class Error(val message: String) : UiState()
}

// ===== VIEWMODEL =====
class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState
    
    init { loadUsers() }
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val users = ApiClient.api.getUsers()
                _uiState.value = UiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}

// ===== MAIN =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: UserViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            UsersScreen(uiState) { viewModel.loadUsers() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(uiState: UiState, onRefresh: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users từ API") },
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), 
                    contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(16.dp))
                        Text("Đang tải từ API...")
                    }
                }
            }
            
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.users) { user ->
                        UserCard(user)
                    }
                }
            }
            
            is UiState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), 
                    contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Error, null, 
                            modifier = Modifier.size(64.dp), tint = Color.Red)
                        Spacer(Modifier.height(16.dp))
                        Text(uiState.message, color = Color.Red)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = onRefresh) {
                            Text("Thử lại")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(user.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, null, Modifier.size(16.dp), tint = Color.Gray)
                Spacer(Modifier.width(8.dp))
                Text(user.email, color = Color.Gray)
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, null, Modifier.size(16.dp), tint = Color.Gray)
                Spacer(Modifier.width(8.dp))
                Text(user.phone, color = Color.Gray)
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  TỔNG KẾT RETROFIT:                                           ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. Thêm dependencies Retrofit + Gson                         ║
 * ║  2. Thêm INTERNET permission                                  ║
 * ║  3. Tạo data classes cho response                             ║
 * ║  4. Tạo interface ApiService với @GET/@POST...                ║
 * ║  5. Tạo Retrofit instance (singleton)                         ║
 * ║  6. Gọi API từ ViewModel với coroutines                       ║
 * ║  7. Xử lý Loading/Success/Error trong UI                      ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm Pull-to-Refresh
 * 2. Khi click vào user, hiển thị chi tiết (navigate)
 * 3. Load posts của user đó ở màn hình chi tiết
 * 4. Thêm cache với Room (offline-first)
 */
