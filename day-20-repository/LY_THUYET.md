# Day 20: Repository Pattern - Kết hợp Room + Retrofit

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **Repository Pattern** và tại sao cần dùng
2. Kết hợp **Room** (local) và **Retrofit** (remote)
3. Implement **Offline-first** architecture
4. Tách biệt data layer khỏi UI layer

---

## PHẦN 1: REPOSITORY PATTERN LÀ GÌ?

### 1.1 Vấn đề không có Repository

```kotlin
// ❌ ViewModel gọi trực tiếp Room và Retrofit
class PostsViewModel : ViewModel() {
    private val api = RetrofitClient.api
    private val dao = AppDatabase.getDatabase(context).postDao()
    
    fun loadPosts() {
        viewModelScope.launch {
            try {
                val posts = api.getPosts()  // Network
                dao.insertAll(posts)        // Database
                // ...
            } catch (e: Exception) {
                val cachedPosts = dao.getAllPosts()  // Fallback
                // ...
            }
        }
    }
}
// Vấn đề: Logic phức tạp, khó test, không reusable
```

### 1.2 Giải pháp: Repository

Repository là **single source of truth** cho data:
- ViewModel chỉ biết Repository, không biết Room/Retrofit
- Dễ test (mock Repository)
- Dễ thay đổi data source

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                              │
│                    (ViewModel, Compose)                      │
│                            │                                 │
│                            ▼                                 │
│                    ┌─────────────┐                          │
│                    │ Repository  │ ← Single Source Truth    │
│                    └─────────────┘                          │
│                      /         \                            │
│                     ▼           ▼                           │
│              ┌─────────┐  ┌─────────────┐                  │
│              │  Room   │  │  Retrofit   │                  │
│              │ (Local) │  │  (Remote)   │                  │
│              └─────────┘  └─────────────┘                  │
│                                                              │
│                       Data Layer                             │
└─────────────────────────────────────────────────────────────┘
```

---

## PHẦN 2: ĐỊNH NGHĨA REPOSITORY

### 2.1 Repository Interface

```kotlin
interface PostRepository {
    fun getAllPosts(): Flow<List<Post>>
    suspend fun getPostById(id: Int): Post?
    suspend fun createPost(title: String, body: String): Post
    suspend fun updatePost(post: Post)
    suspend fun deletePost(id: Int)
    suspend fun refreshPosts()
}
```

### 2.2 Repository Implementation

```kotlin
class PostRepositoryImpl(
    private val api: ApiService,
    private val dao: PostDao
) : PostRepository {
    
    // Luôn lấy từ database (single source of truth)
    override fun getAllPosts(): Flow<List<Post>> {
        return dao.getAllPosts().map { entities ->
            entities.map { it.toPost() }
        }
    }
    
    override suspend fun getPostById(id: Int): Post? {
        return dao.getPostById(id)?.toPost()
    }
    
    override suspend fun createPost(title: String, body: String): Post {
        val request = CreatePostRequest(title, body, userId = 1)
        val response = api.createPost(request)
        val entity = response.toEntity()
        dao.insert(entity)
        return entity.toPost()
    }
    
    override suspend fun updatePost(post: Post) {
        api.updatePost(post.id, post.toUpdateRequest())
        dao.update(post.toEntity())
    }
    
    override suspend fun deletePost(id: Int) {
        api.deletePost(id)
        dao.deleteById(id)
    }
    
    override suspend fun refreshPosts() {
        val remotePosts = api.getPosts()
        dao.deleteAll()
        dao.insertAll(remotePosts.map { it.toEntity() })
    }
}
```

---

## PHẦN 3: MAPPERS

### 3.1 Tại sao cần Mappers?

- **DTO** (Data Transfer Object): Dữ liệu từ API
- **Entity**: Dữ liệu trong Room
- **Domain Model**: Dữ liệu dùng trong app

```kotlin
// DTO từ API
data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

// Entity cho Room
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val cachedAt: Long = System.currentTimeMillis()
)

// Domain model cho UI
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
```

### 3.2 Extension functions cho mapping

```kotlin
// DTO → Entity
fun PostDto.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

// Entity → Domain
fun PostEntity.toPost(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

// Domain → Entity
fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}
```

---

## PHẦN 4: OFFLINE-FIRST PATTERN

### 4.1 Chiến lược

1. UI luôn **observe từ Room**
2. Background **fetch từ API** và update Room
3. Khi offline, **dùng cached data**

```kotlin
class PostRepositoryImpl(
    private val api: ApiService,
    private val dao: PostDao
) : PostRepository {
    
    // UI observe Flow này - luôn từ Room
    override fun getAllPosts(): Flow<List<Post>> {
        return dao.getAllPosts().map { entities ->
            entities.map { it.toPost() }
        }
    }
    
    // Background refresh
    override suspend fun refreshPosts() {
        try {
            val remotePosts = withContext(Dispatchers.IO) {
                api.getPosts()
            }
            dao.replaceAll(remotePosts.map { it.toEntity() })
        } catch (e: IOException) {
            // Network error - cached data vẫn được dùng
            throw RefreshException("Không thể cập nhật, đang dùng dữ liệu cache")
        }
    }
}
```

### 4.2 ViewModel sử dụng Repository

```kotlin
class PostsViewModel(
    private val repository: PostRepository
) : ViewModel() {
    
    val posts = repository.getAllPosts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        refreshPosts()
    }
    
    fun refreshPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                repository.refreshPosts()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
    
    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletePost(id)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Không thể xóa: ${e.message}") }
            }
        }
    }
}
```

---

## PHẦN 5: CẤU TRÚC PROJECT

```
app/src/main/java/com/example/myapp/
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   └── PostEntity.kt
│   │   ├── dao/
│   │   │   └── PostDao.kt
│   │   └── AppDatabase.kt
│   │
│   ├── remote/
│   │   ├── dto/
│   │   │   └── PostDto.kt
│   │   ├── api/
│   │   │   └── ApiService.kt
│   │   └── RetrofitClient.kt
│   │
│   ├── mapper/
│   │   └── PostMapper.kt
│   │
│   └── repository/
│       ├── PostRepository.kt (interface)
│       └── PostRepositoryImpl.kt
│
├── domain/
│   └── model/
│       └── Post.kt
│
└── ui/
    ├── posts/
    │   ├── PostsViewModel.kt
    │   └── PostsScreen.kt
    └── ...
```

---

## PHẦN 6: RESULT/RESOURCE WRAPPER

```kotlin
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data class Loading<T>(val data: T? = null) : Resource<T>()
}

// Repository trả về Flow<Resource>
fun getPosts(): Flow<Resource<List<Post>>> = flow {
    emit(Resource.Loading())
    
    // Emit cached data first
    val cachedPosts = dao.getAllPostsOnce().map { it.toPost() }
    emit(Resource.Loading(cachedPosts))
    
    try {
        // Fetch from network
        val remotePosts = api.getPosts()
        dao.replaceAll(remotePosts.map { it.toEntity() })
        
        // Emit final data
        val updatedPosts = dao.getAllPostsOnce().map { it.toPost() }
        emit(Resource.Success(updatedPosts))
        
    } catch (e: Exception) {
        // Network failed, but we have cache
        if (cachedPosts.isNotEmpty()) {
            emit(Resource.Success(cachedPosts))
        } else {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
```

---

## PHẦN 7: VÍ DỤ HOÀN CHỈNH

```kotlin
// PostRepository.kt
interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun refreshPosts()
    suspend fun createPost(title: String, body: String)
    suspend fun deletePost(id: Int)
}

// PostRepositoryImpl.kt
class PostRepositoryImpl(
    private val api: ApiService,
    private val dao: PostDao
) : PostRepository {
    
    override fun getPosts(): Flow<List<Post>> =
        dao.getAllPosts().map { entities -> entities.map { it.toPost() } }
    
    override suspend fun refreshPosts() {
        val posts = api.getPosts()
        dao.replaceAll(posts.map { it.toEntity() })
    }
    
    override suspend fun createPost(title: String, body: String) {
        val response = api.createPost(CreatePostRequest(title, body, 1))
        dao.insert(response.toEntity())
    }
    
    override suspend fun deletePost(id: Int) {
        dao.deleteById(id)
        try { api.deletePost(id) } catch (_: Exception) { }
    }
}

// PostsViewModel.kt
class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    val posts = repo.getPosts().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try { repo.refreshPosts() } finally { _isRefreshing.value = false }
        }
    }
}
```

---

## 📝 TÓM TẮT

| Khái niệm | Mô tả |
|-----------|-------|
| Repository | Single source of truth cho data |
| DTO | Data từ API |
| Entity | Data trong Room |
| Domain Model | Data dùng trong app |
| Mapper | Convert giữa các loại data |
| Offline-first | UI observe Room, background sync API |

---

## ➡️ NGÀY MAI
**Day 21: Dependency Injection với Hilt**
- DI là gì?
- Setup Hilt
- Inject Repository vào ViewModel
