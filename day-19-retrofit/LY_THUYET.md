# Day 19: Retrofit - API Calls

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **Retrofit** là gì và cách hoạt động
2. Thiết lập Retrofit trong project
3. Định nghĩa **API interface**
4. Thực hiện **GET/POST** requests
5. Xử lý **JSON** responses

---

## PHẦN 1: RETROFIT LÀ GÌ?

### 1.1 Định nghĩa

Retrofit là **HTTP client library** cho Android, giúp:
- Gọi REST API dễ dàng
- Convert JSON ↔ Kotlin objects (với Gson/Moshi)
- Integrate với Coroutines

### 1.2 Cấu trúc

```
App → Retrofit → OkHttp → Internet → API Server
         ↓           ↓
    Converters   Interceptors
    (Gson/Moshi)  (Logging, Auth)
```

---

## PHẦN 2: THÊM DEPENDENCIES

```kotlin
dependencies {
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Gson (JSON parsing)
    implementation("com.google.code.gson:gson:2.10.1")
    
    // OkHttp Logging (debug)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}
```

**AndroidManifest.xml:**
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## PHẦN 3: DATA MODELS

### 3.1 Định nghĩa DTO (Data Transfer Object)

```kotlin
// Response từ API
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("avatar_url")  // Map JSON key khác tên field
    val avatarUrl: String?
)

data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

// Response wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?
)
```

---

## PHẦN 4: API INTERFACE

### 4.1 Định nghĩa API endpoints

```kotlin
import retrofit2.http.*

interface ApiService {
    // GET - Lấy danh sách
    @GET("users")
    suspend fun getUsers(): List<UserDto>
    
    // GET - Lấy theo ID
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): UserDto
    
    // GET - Với query parameters
    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): List<PostDto>
    
    // POST - Tạo mới
    @POST("posts")
    suspend fun createPost(@Body post: CreatePostRequest): PostDto
    
    // PUT - Cập nhật toàn bộ
    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") postId: Int,
        @Body post: UpdatePostRequest
    ): PostDto
    
    // PATCH - Cập nhật một phần
    @PATCH("posts/{id}")
    suspend fun patchPost(
        @Path("id") postId: Int,
        @Body fields: Map<String, Any>
    ): PostDto
    
    // DELETE
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") postId: Int)
}

// Request bodies
data class CreatePostRequest(
    val title: String,
    val body: String,
    val userId: Int
)

data class UpdatePostRequest(
    val title: String,
    val body: String
)
```

### 4.2 Annotations quan trọng

| Annotation | Dùng cho |
|------------|----------|
| `@GET` | HTTP GET request |
| `@POST` | HTTP POST request |
| `@PUT` | HTTP PUT request |
| `@PATCH` | HTTP PATCH request |
| `@DELETE` | HTTP DELETE request |
| `@Path` | URL path parameter |
| `@Query` | Query string parameter |
| `@Body` | Request body (JSON) |
| `@Header` | Custom header |
| `@Headers` | Multiple headers |

---

## PHẦN 5: RETROFIT INSTANCE

### 5.1 Tạo Retrofit object

```kotlin
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
```

### 5.2 Auth Interceptor (Bearer Token)

```kotlin
class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        val token = tokenProvider()
        if (token == null) {
            return chain.proceed(originalRequest)
        }
        
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        
        return chain.proceed(newRequest)
    }
}

// Sử dụng
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor { getStoredToken() })
    .build()
```

---

## PHẦN 6: GỌI API TRONG VIEWMODEL

### 6.1 Xử lý Loading/Error

```kotlin
data class UsersUiState(
    val users: List<UserDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class UsersViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState = _uiState.asStateFlow()
    
    private val api = RetrofitClient.apiService
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val users = api.getUsers()
                _uiState.update { it.copy(users = users, isLoading = false) }
            } catch (e: IOException) {
                _uiState.update { 
                    it.copy(error = "Lỗi mạng: ${e.message}", isLoading = false) 
                }
            } catch (e: HttpException) {
                _uiState.update { 
                    it.copy(error = "Lỗi server: ${e.code()}", isLoading = false) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Lỗi: ${e.message}", isLoading = false) 
                }
            }
        }
    }
}
```

### 6.2 POST request

```kotlin
fun createPost(title: String, body: String) {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        
        try {
            val request = CreatePostRequest(
                title = title,
                body = body,
                userId = 1
            )
            val newPost = api.createPost(request)
            _uiState.update { 
                it.copy(posts = it.posts + newPost, isLoading = false) 
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message, isLoading = false) }
        }
    }
}
```

---

## PHẦN 7: RESULT WRAPPER

### 7.1 Sealed class cho API result

```kotlin
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

// Extension function để gọi API an toàn
suspend fun <T> safeApiCall(call: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (e: IOException) {
        ApiResult.Error("Lỗi kết nối mạng")
    } catch (e: HttpException) {
        ApiResult.Error("Lỗi server: ${e.code()}", e.code())
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Lỗi không xác định")
    }
}

// Sử dụng
fun loadUsers() {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        
        when (val result = safeApiCall { api.getUsers() }) {
            is ApiResult.Success -> {
                _uiState.update { it.copy(users = result.data, isLoading = false) }
            }
            is ApiResult.Error -> {
                _uiState.update { it.copy(error = result.message, isLoading = false) }
            }
            ApiResult.Loading -> { /* handled above */ }
        }
    }
}
```

---

## PHẦN 8: VÍ DỤ HOÀN CHỈNH

```kotlin
// ApiService.kt
interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
    
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): PostDto
    
    @POST("posts")
    suspend fun createPost(@Body post: CreatePostRequest): PostDto
}

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    
    val api: JsonPlaceholderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}

// PostsViewModel.kt
class PostsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState = _uiState.asStateFlow()
    
    init { loadPosts() }
    
    fun loadPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
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

---

## 📝 TÓM TẮT

| Khái niệm | Mô tả |
|-----------|-------|
| Retrofit | HTTP client library |
| ApiService | Interface định nghĩa endpoints |
| Gson | JSON converter |
| Interceptor | Middleware (logging, auth) |
| `@GET`, `@POST`... | HTTP method annotations |
| `@Path`, `@Query`, `@Body` | Parameter annotations |

---

## ➡️ NGÀY MAI
**Day 20: Retrofit + Repository Pattern**
- Repository Pattern là gì?
- Kết hợp Room + Retrofit
- Offline-first architecture
