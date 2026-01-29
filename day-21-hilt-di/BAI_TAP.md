# Bài tập Day 21: Hilt Dependency Injection

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Setup Hilt cơ bản (Dễ)
- Thêm Hilt dependencies
- Tạo @HiltAndroidApp Application
- Annotate MainActivity với @AndroidEntryPoint
- Verify app chạy được

---

### Bài 2: NetworkModule (Trung bình)
Tạo NetworkModule cung cấp:
- OkHttpClient với logging
- Retrofit với base URL
- ApiService

---

### Bài 3: DatabaseModule (Trung bình)
Tạo DatabaseModule cung cấp:
- AppDatabase (@Singleton)
- PostDao
- UserDao 
- NoteDao

---

### Bài 4: RepositoryModule (Trung bình)
Tạo RepositoryModule với @Binds:
- PostRepository → PostRepositoryImpl
- UserRepository → UserRepositoryImpl

---

### Bài 5: Complete Posts Feature (Khó)
Implement hoàn chỉnh:
```
NetworkModule → ApiService
DatabaseModule → PostDao
RepositoryModule → PostRepository
PostsViewModel (@HiltViewModel)
PostsScreen (hiltViewModel())
```

---

### Bài 6: Multi-feature App (Nâng cao)
Tạo app Notes với DI hoàn chỉnh:
```
Modules:
├── NetworkModule (if cloud sync)
├── DatabaseModule
└── RepositoryModule

Features:
├── Notes (ViewModel, Repository, Screen)
├── Folders (ViewModel, Repository, Screen)
└── Settings (ViewModel, DataStore)
```

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Dependency Injection giải quyết vấn đề gì?**
   > Gợi ý: Testing, loose coupling, single responsibility.

2. **@Provides vs @Binds khác gì nhau?**
   > Gợi ý: @Provides cho instance creation, @Binds cho interface binding.

3. **@Singleton scope có ý nghĩa gì?**
   > Gợi ý: Single instance throughout app lifetime.

4. **Tại sao cần @AndroidEntryPoint?**
   > Gợi ý: Enable Hilt injection trong Activity/Fragment.

5. **hiltViewModel() khác viewModel() thế nào?**
   > Gợi ý: hiltViewModel uses Hilt for dependency injection.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 2 - NetworkModule:**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
```

**Bài 3 - DatabaseModule:**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    fun providePostDao(database: AppDatabase): PostDao = database.postDao()
    
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
    
    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao = database.noteDao()
}
```

**Bài 4 - RepositoryModule:**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: PostRepositoryImpl): PostRepository
    
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}

// Repository Implementation cần @Inject constructor
class PostRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: PostDao
) : PostRepository {
    // implementation
}
```

**Bài 5 - Complete:**
```kotlin
// ViewModel
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    val posts = repository.getPosts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

// Screen
@Composable
fun PostsScreen(viewModel: PostsViewModel = hiltViewModel()) {
    val posts by viewModel.posts.collectAsState()
    LazyColumn {
        items(posts) { PostItem(it) }
    }
}

// MainActivity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PostsScreen() }
    }
}
```
