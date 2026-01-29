/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 19 - BÀI 2: GỌI API                                      ║
 * ║                                                               ║
 * ║  Tạo file: viewmodel/UserViewModel.kt                         ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.network.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Sealed class cho các trạng thái
sealed class UsersUiState {
    object Loading : UsersUiState()
    data class Success(val users: List<User>) : UsersUiState()
    data class Error(val message: String) : UsersUiState()
}

class UserViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState> = _uiState
    
    private val apiService = RetrofitClient.apiService
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UsersUiState.Loading
            
            try {
                // Gọi API
                val users = apiService.getUsers()
                _uiState.value = UsersUiState.Success(users)
            } catch (e: Exception) {
                // Xử lý lỗi
                _uiState.value = UsersUiState.Error(
                    e.message ?: "Không thể tải dữ liệu"
                )
            }
        }
    }
    
    fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                val user = apiService.getUserById(id)
                // Xử lý user...
            } catch (e: Exception) {
                // Xử lý lỗi...
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH GỌI API:                                          ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  TRY-CATCH để xử lý lỗi:                                      ║
 * ║  try {                                                        ║
 * ║      val users = apiService.getUsers()                        ║
 * ║      // Thành công                                            ║
 * ║  } catch (e: Exception) {                                     ║
 * ║      // Lỗi: mạng, server, parse JSON...                      ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  CÁC LOẠI LỖI THƯỜNG GẶP:                                     ║
 * ║  → UnknownHostException: Không có mạng                        ║
 * ║  → HttpException: Lỗi HTTP (404, 500...)                      ║
 * ║  → JsonSyntaxException: Lỗi parse JSON                        ║
 * ║  → SocketTimeoutException: Timeout                            ║
 * ║                                                               ║
 * ║  SEALED CLASS CHO UI STATE:                                   ║
 * ║  → Loading: Đang tải                                          ║
 * ║  → Success: Thành công, có data                               ║
 * ║  → Error: Thất bại, có message lỗi                            ║
 * ║  → Compiler đảm bảo xử lý hết các trường hợp                  ║
 * ║                                                               ║
 * ║  FLOW:                                                        ║
 * ║  1. UI trigger loadUsers()                                    ║
 * ║  2. State → Loading                                           ║
 * ║  3. Gọi API (suspend)                                         ║
 * ║  4. Thành công → State = Success(data)                        ║
 * ║     Thất bại → State = Error(message)                         ║
 * ║  5. UI tự động update theo state                              ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm hàm loadPosts() gọi apiService.getPosts()
 * 2. Thêm xử lý retry khi gặp lỗi
 * 3. Thêm timeout xử lý với delay()
 */
