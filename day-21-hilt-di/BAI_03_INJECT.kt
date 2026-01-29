/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 21 - BÀI 3: INJECT DEPENDENCIES                          ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ============================================
// INJECT VÀO VIEWMODEL với @HiltViewModel
// ============================================

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            repository.users.collect { users ->
                _uiState.value = UsersUiState(users = users)
            }
        }
    }
}

// ============================================
// SỬ DỤNG VIEWMODEL TRONG COMPOSE
// ============================================

import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Hilt tự động inject dependencies vào ViewModel
            val viewModel: UserViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            UsersScreen(uiState, onRefresh = { viewModel.loadUsers() })
        }
    }
}

// ============================================
// INJECT VÀO CLASS THÔNG THƯỜNG
// ============================================

// Thêm @Inject constructor → Hilt biết cách tạo
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
) : UserRepository {
    // ...
}

// Trong Module, bind interface với implementation
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  @Provides vs @Binds:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  @Provides:                                                   ║
 * ║  → Dùng khi cần viết code tạo object                          ║
 * ║  → Dùng cho third-party classes (Retrofit, Room...)           ║
 * ║  → object Module với fun                                      ║
 * ║                                                               ║
 * ║  @Provides                                                    ║
 * ║  fun provideRetrofit(): Retrofit {                            ║
 * ║      return Retrofit.Builder().build()                        ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  @Binds:                                                      ║
 * ║  → Dùng khi bind interface với implementation                 ║
 * ║  → Dùng cho classes có @Inject constructor                    ║
 * ║  → abstract class Module với abstract fun                     ║
 * ║                                                               ║
 * ║  @Binds                                                       ║
 * ║  abstract fun bind(impl: RepoImpl): Repository                ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  TỔNG KẾT HILT:                                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. @HiltAndroidApp trên Application                          ║
 * ║  2. @AndroidEntryPoint trên Activity/Fragment                 ║
 * ║  3. @HiltViewModel + @Inject constructor trên ViewModel       ║
 * ║  4. @Module + @InstallIn để provide dependencies              ║
 * ║  5. hiltViewModel() trong Compose để lấy ViewModel            ║
 * ║                                                               ║
 * ║  DEPENDENCY GRAPH:                                            ║
 * ║  Activity                                                     ║
 * ║     └── ViewModel                                             ║
 * ║            └── Repository                                     ║
 * ║                   ├── DAO ← Database ← Context                ║
 * ║                   └── ApiService ← Retrofit                   ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Setup Hilt trong project của bạn
 * 2. Migrate ViewModel từ AndroidViewModel sang @HiltViewModel
 * 3. Tạo Module cho Room và Retrofit
 */
