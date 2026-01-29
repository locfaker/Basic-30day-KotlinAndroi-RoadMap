/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 20 - BÀI 2: IMPLEMENT REPOSITORY                         ║
 * ║                                                               ║
 * ║  Full implementation với ViewModel                            ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

// ========== DATA LAYER ==========

// Entity
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String
)

// DAO
@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
    
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
    
    @Delete
    suspend fun deleteUser(user: User)
}

// API
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

// ========== REPOSITORY ==========

class UserRepository(
    private val userDao: UserDao,
    private val apiService: ApiService
) {
    // Flow từ database - Single Source of Truth
    val users: Flow<List<User>> = userDao.getAllUsers()
    
    // Refresh từ network
    suspend fun refresh(): Result<Unit> {
        return try {
            val users = apiService.getUsers()
            userDao.insertUsers(users)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ========== VIEWMODEL ==========

data class UsersUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UsersUiState(isLoading = true))
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()
    
    init {
        // Observe database
        viewModelScope.launch {
            repository.users.collect { users ->
                _uiState.value = _uiState.value.copy(
                    users = users,
                    isLoading = false
                )
            }
        }
        
        // Initial refresh
        refresh()
    }
    
    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.refresh().onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH LUỒNG DỮ LIỆU:                                    ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. App start                                                 ║
 * ║  2. ViewModel observe repository.users (Flow từ DB)           ║
 * ║  3. ViewModel gọi repository.refresh()                        ║
 * ║  4. Repository gọi API → lấy data                             ║
 * ║  5. Repository insert vào Database                            ║
 * ║  6. Database thay đổi → Flow emit giá trị mới                 ║
 * ║  7. ViewModel nhận data → update UI State                     ║
 * ║  8. UI hiển thị data                                          ║
 * ║                                                               ║
 * ║  KHI OFFLINE:                                                 ║
 * ║  → refresh() thất bại                                         ║
 * ║  → Nhưng Flow từ DB vẫn có data cũ                            ║
 * ║  → UI vẫn hiển thị được                                       ║
 * ║                                                               ║
 * ║  Result<T>:                                                   ║
 * ║  → Result.success(value): Thành công                          ║
 * ║  → Result.failure(exception): Thất bại                        ║
 * ║  → .onSuccess { }, .onFailure { }: Xử lý kết quả              ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
