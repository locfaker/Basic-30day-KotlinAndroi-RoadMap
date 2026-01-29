/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 23 - BÀI 1: RESULT WRAPPER                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.util

/**
 * Custom Result class để wrap responses
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val exception: Throwable? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

// Extension functions
fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

fun <T> Resource<T>.onError(action: (String) -> Unit): Resource<T> {
    if (this is Resource.Error) action(message)
    return this
}

// CÁCH DÙNG TRONG REPOSITORY
class UserRepository {
    suspend fun getUsers(): Resource<List<User>> {
        return try {
            val users = apiService.getUsers()
            Resource.Success(users)
        } catch (e: IOException) {
            Resource.Error("Không có kết nối mạng", e)
        } catch (e: HttpException) {
            Resource.Error("Lỗi server: ${e.code()}", e)
        } catch (e: Exception) {
            Resource.Error("Lỗi không xác định", e)
        }
    }
}

// CÁCH DÙNG TRONG VIEWMODEL
class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState> = _uiState
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UsersUiState.Loading
            
            when (val result = repository.getUsers()) {
                is Resource.Success -> {
                    _uiState.value = UsersUiState.Success(result.data)
                }
                is Resource.Error -> {
                    _uiState.value = UsersUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    // Already set above
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH RESULT PATTERN:                                   ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  TẠI SAO KHÔNG DÙNG TRY-CATCH TRỰC TIẾP?                      ║
 * ║  → ViewModel không biết loại lỗi cụ thể                       ║
 * ║  → Khó tái sử dụng logic xử lý lỗi                            ║
 * ║  → Khó test                                                   ║
 * ║                                                               ║
 * ║  RESOURCE SEALED CLASS:                                       ║
 * ║  → Success: Có data                                           ║
 * ║  → Error: Có message + optional exception                     ║
 * ║  → Loading: Đang tải                                          ║
 * ║                                                               ║
 * ║  KHI DÙNG when { }:                                           ║
 * ║  → Compiler bắt buộc xử lý tất cả cases                       ║
 * ║  → Không bao giờ quên xử lý error                             ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
