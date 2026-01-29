# Day 21: Dependency Injection với Hilt

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **Dependency Injection (DI)** là gì
2. Setup **Hilt** trong project
3. Inject **Repository** vào ViewModel
4. Inject **Room** và **Retrofit** vào Repository

---

## PHẦN 1: DEPENDENCY INJECTION LÀ GÌ?

### 1.1 Vấn đề không có DI

```kotlin
// ❌ Tạo dependencies trực tiếp trong class
class PostsViewModel : ViewModel() {
    private val api = RetrofitClient.api
    private val dao = AppDatabase.getDatabase(context).postDao()
    private val repository = PostRepositoryImpl(api, dao)
    
    // Vấn đề:
    // 1. Khó test (không thể mock)
    // 2. Tight coupling
    // 3. Thay đổi khó khăn
}
```

### 1.2 Giải pháp: Dependency Injection

```kotlin
// ✅ Nhận dependencies từ bên ngoài
class PostsViewModel(
    private val repository: PostRepository  // Injected
) : ViewModel() {
    // Easy to test with mock repository
}
```

### 1.3 Hilt là gì?

Hilt là **DI library** chính thức của Android, built on top of Dagger:
- Compile-time validation
- Automatic lifecycle management
- Android-specific components

---

## PHẦN 2: SETUP HILT

### 2.1 Project build.gradle.kts

```kotlin
plugins {
    id("com.google.dagger.hilt.android") version "2.50" apply false
}
```

### 2.2 App build.gradle.kts

```kotlin
plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")
    
    // Hilt + ViewModel
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
}
```

### 2.3 Application class

```kotlin
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()
```

**AndroidManifest.xml:**
```xml
<application
    android:name=".MyApplication"
    ...>
```

---

## PHẦN 3: HILT MODULES

### 3.1 Module cho Retrofit

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

### 3.2 Module cho Room

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
        ).build()
    }
    
    @Provides
    fun providePostDao(database: AppDatabase): PostDao {
        return database.postDao()
    }
    
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}
```

### 3.3 Module cho Repository

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindPostRepository(
        impl: PostRepositoryImpl
    ): PostRepository
    
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}
```

---

## PHẦN 4: INJECT VÀO REPOSITORY

```kotlin
class PostRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: PostDao
) : PostRepository {
    
    override fun getPosts(): Flow<List<Post>> =
        dao.getAllPosts().map { it.map(PostEntity::toPost) }
    
    override suspend fun refreshPosts() {
        val posts = api.getPosts()
        dao.replaceAll(posts.map(PostDto::toEntity))
    }
}
```

---

## PHẦN 5: INJECT VÀO VIEWMODEL

### 5.1 Annotate ViewModel

```kotlin
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    
    val posts = repository.getPosts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    
    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                repository.refreshPosts()
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}
```

### 5.2 Sử dụng trong Compose

```kotlin
@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel()  // Hilt inject
) {
    val posts by viewModel.posts.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    LazyColumn {
        items(posts) { post ->
            PostItem(post)
        }
    }
}
```

---

## PHẦN 6: ACTIVITY/FRAGMENT

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}
```

---

## PHẦN 7: SCOPES

| Scope | Lifetime | Dùng cho |
|-------|----------|----------|
| `@Singleton` | App lifetime | Database, Retrofit |
| `@ActivityScoped` | Activity lifetime | Activity-specific |
| `@ViewModelScoped` | ViewModel lifetime | ViewModel dependencies |
| `@FragmentScoped` | Fragment lifetime | Fragment-specific |

```kotlin
@Module
@InstallIn(SingletonComponent::class)  // App lifetime
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase { ... }
}

@Module
@InstallIn(ViewModelComponent::class)  // ViewModel lifetime
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideSomeUseCase(): SomeUseCase { ... }
}
```

---

## PHẦN 8: CẤU TRÚC HOÀN CHỈNH

```
app/src/main/java/com/example/myapp/
├── MyApplication.kt  (@HiltAndroidApp)
│
├── di/
│   ├── NetworkModule.kt
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
│
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   └── dao/PostDao.kt
│   ├── remote/
│   │   └── ApiService.kt
│   └── repository/
│       ├── PostRepository.kt
│       └── PostRepositoryImpl.kt
│
└── ui/
    ├── MainActivity.kt  (@AndroidEntryPoint)
    └── posts/
        ├── PostsViewModel.kt  (@HiltViewModel)
        └── PostsScreen.kt
```

---

## 📝 TÓM TẮT

| Annotation | Dùng cho |
|------------|----------|
| `@HiltAndroidApp` | Application class |
| `@AndroidEntryPoint` | Activity/Fragment |
| `@HiltViewModel` | ViewModel |
| `@Inject constructor` | Class cần inject |
| `@Module` | Define how to provide dependencies |
| `@Provides` | Provide instance |
| `@Binds` | Bind interface to implementation |
| `@Singleton` | Single instance cho app |
| `hiltViewModel()` | Get ViewModel trong Compose |

---

## ➡️ TUẦN 4 SẮP TỚI
**MVVM Architecture Hoàn chỉnh**
- Day 22-23: MVVM + Clean Architecture
- Day 24-25: Error Handling & Theming
- Day 26-27: Animation & Testing
- Day 28-30: Final Project
