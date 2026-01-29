# Bài tập Day 19: Retrofit

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: JSONPlaceholder Posts (Dễ)
Sử dụng API: `https://jsonplaceholder.typicode.com/`
- Tạo PostDto và ApiService
- GET /posts - lấy tất cả posts
- Hiển thị trong LazyColumn
- Handle loading và error

---

### Bài 2: Users với Details (Trung bình)
- GET /users - lấy danh sách users
- Click user → navigate đến detail
- GET /users/{id} - lấy chi tiết user
- Hiển thị email, phone, website, company

---

### Bài 3: Create/Delete Post (Trung bình)
- Form tạo post mới (title, body)
- POST /posts - tạo mới
- DELETE /posts/{id} - xóa
- Show Snackbar khi thành công

---

### Bài 4: Posts với Comments (Khó)
- GET /posts - danh sách posts
- GET /posts/{id}/comments - comments của post
- Click post → hiển thị comments
- Hiển thị số comments trong list

---

### Bài 5: Search với Query Params (Khó)
Tạo SearchViewModel:
- GET /posts?userId={id} - filter by user
- GET /posts?_page={n}&_limit={n} - pagination
- Combine filters
- Pull to refresh

---

### Bài 6: Random User API (Nâng cao)
Sử dụng API: `https://randomuser.me/api/`
- GET /?results=10 - lấy 10 users
- Hiển thị avatar, name, email, location
- "Load More" button
- Cache kết quả (không gọi lại khi scroll)

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao cần Retrofit thay vì HttpURLConnection?**
   > Gợi ý: Type-safe, less boilerplate, converters.

2. **Converter Factory làm gì?**
   > Gợi ý: Convert response body to Kotlin objects.

3. **@SerializedName dùng khi nào?**
   > Gợi ý: JSON key khác với field name.

4. **Logging Interceptor chỉ dùng khi nào?**
   > Gợi ý: Debug only, không production.

5. **suspend function trong interface hoạt động thế nào?**
   > Gợi ý: Retrofit generates implementation with coroutines.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}

class PostsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState = _uiState.asStateFlow()
    
    init { loadPosts() }
    
    fun loadPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val posts = RetrofitClient.api.getPosts()
                _uiState.update { it.copy(posts = posts, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
```

**Bài 3:**
```kotlin
interface ApiService {
    @POST("posts")
    suspend fun createPost(@Body post: CreatePostRequest): PostDto
    
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}

fun createPost(title: String, body: String) {
    viewModelScope.launch {
        try {
            val post = api.createPost(CreatePostRequest(title, body, 1))
            _uiState.update { it.copy(posts = it.posts + post) }
            _events.emit(Event.ShowSnackbar("Đã tạo post"))
        } catch (e: Exception) {
            _events.emit(Event.ShowSnackbar("Lỗi: ${e.message}"))
        }
    }
}
```

**Bài 5 - Pagination:**
```kotlin
interface ApiService {
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int = 10,
        @Query("userId") userId: Int? = null
    ): List<PostDto>
}

fun loadNextPage() {
    viewModelScope.launch {
        val currentPage = _uiState.value.currentPage
        try {
            val newPosts = api.getPosts(page = currentPage + 1)
            _uiState.update {
                it.copy(
                    posts = it.posts + newPosts,
                    currentPage = currentPage + 1,
                    hasMore = newPosts.isNotEmpty()
                )
            }
        } catch (e: Exception) { /* handle */ }
    }
}
```

**Bài 6 - Random User:**
```kotlin
data class RandomUserResponse(
    val results: List<RandomUser>
)

data class RandomUser(
    val name: Name,
    val email: String,
    val picture: Picture,
    val location: Location
)

interface RandomUserApi {
    @GET(".")
    suspend fun getUsers(@Query("results") count: Int = 10): RandomUserResponse
}
```
