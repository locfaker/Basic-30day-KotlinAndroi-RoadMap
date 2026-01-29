# Bài tập Day 20: Repository Pattern

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: PostRepository cơ bản (Dễ)
Tạo PostRepository với:
- Interface + Implementation
- getPosts(): Flow
- refreshPosts(): suspend
- Room DAO + Retrofit API

---

### Bài 2: UserRepository offline-first (Trung bình)
Tạo UserRepository:
- Lấy users từ Room (observe)
- Sync từ API khi có mạng
- Fallback cached data khi offline
- Pull-to-refresh

---

### Bài 3: Todo với Local-Remote Sync (Trung bình)
Tạo TodoRepository:
- CRUD operations
- Create: Save local + sync remote
- Update: Update local + sync remote
- Delete: Delete local + sync remote
- Mark for sync khi offline

---

### Bài 4: Notes với Conflict Resolution (Khó)
Tạo NotesRepository với sync logic:
- Track lastModified local và remote
- Detect conflicts (cả 2 bên đều thay đổi)
- Strategy: Last Write Wins hoặc Manual Merge

---

### Bài 5: Products với Pagination (Khó)
Tạo ProductRepository với:
- Pagination (page, limit)
- Cache mỗi page
- Refresh invalidate cache
- Load more append data

---

### Bài 6: Complete E-commerce Repository (Nâng cao)
Tạo các repositories:
```
├── UserRepository (auth, profile)
├── ProductRepository (list, detail, search)
├── CartRepository (local only)
├── OrderRepository (sync with server)
└── WishlistRepository (local + sync)
```

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Repository Pattern giải quyết vấn đề gì?**
   > Gợi ý: Separation of concerns, testability, single source of truth.

2. **Tại sao UI observe Room thay vì API trực tiếp?**
   > Gợi ý: Offline-first, immediate response, automatic updates.

3. **DTO vs Entity vs Domain Model - khi nào dùng cái nào?**
   > Gợi ý: API response, database, business logic.

4. **Làm sao handle case offline create/update?**
   > Gợi ý: Pending queue, sync khi có mạng.

5. **Repository nên throw exception hay return Result?**
   > Gợi ý: Depends on use case, Result safer for UI.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun refreshPosts()
}

class PostRepositoryImpl(
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

**Bài 2 - Offline-first:**
```kotlin
class UserRepositoryImpl(
    private val api: ApiService,
    private val dao: UserDao,
    private val connectivityManager: ConnectivityManager
) : UserRepository {
    
    override fun getUsers(): Flow<List<User>> = dao.getAllUsers().map { it.map(UserEntity::toUser) }
    
    override suspend fun refreshUsers(): Result<Unit> {
        if (!isOnline()) {
            return Result.failure(Exception("Offline - using cached data"))
        }
        
        return try {
            val users = api.getUsers()
            dao.replaceAll(users.map(UserDto::toEntity))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun isOnline(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
```

**Bài 3 - Sync with pending queue:**
```kotlin
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val completed: Boolean,
    val syncStatus: SyncStatus = SyncStatus.SYNCED
)

enum class SyncStatus { SYNCED, PENDING_CREATE, PENDING_UPDATE, PENDING_DELETE }

class TodoRepositoryImpl(...) {
    suspend fun createTodo(title: String) {
        val todo = TodoEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            completed = false,
            syncStatus = SyncStatus.PENDING_CREATE
        )
        dao.insert(todo)
        syncPending()
    }
    
    suspend fun syncPending() {
        if (!isOnline()) return
        
        val pending = dao.getPendingSync()
        pending.forEach { todo ->
            try {
                when (todo.syncStatus) {
                    SyncStatus.PENDING_CREATE -> api.createTodo(todo.toRequest())
                    SyncStatus.PENDING_UPDATE -> api.updateTodo(todo.id, todo.toRequest())
                    SyncStatus.PENDING_DELETE -> api.deleteTodo(todo.id)
                    else -> { }
                }
                dao.updateSyncStatus(todo.id, SyncStatus.SYNCED)
            } catch (e: Exception) { /* retry later */ }
        }
    }
}
```

**Bài 5 - Pagination:**
```kotlin
class ProductRepositoryImpl(...) : ProductRepository {
    private var currentPage = 0
    private var hasMore = true
    
    fun getProducts(): Flow<List<Product>> = dao.getAllProducts().map { it.map(ProductEntity::toProduct) }
    
    suspend fun loadNextPage(): Boolean {
        if (!hasMore) return false
        
        val products = api.getProducts(page = currentPage + 1, limit = 20)
        if (products.isEmpty()) {
            hasMore = false
            return false
        }
        
        dao.insertAll(products.map(ProductDto::toEntity))
        currentPage++
        return true
    }
    
    suspend fun refresh() {
        currentPage = 0
        hasMore = true
        val products = api.getProducts(page = 1, limit = 20)
        dao.replaceAll(products.map(ProductDto::toEntity))
        currentPage = 1
    }
}
```
