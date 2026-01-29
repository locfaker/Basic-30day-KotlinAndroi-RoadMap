/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 19 - BÀI 1: SETUP RETROFIT                               ║
 * ║                                                               ║
 * ║  Tạo file: network/ApiService.kt                              ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

// ===== DATA CLASSES (Response từ API) =====

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String
)

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

// ===== API SERVICE INTERFACE =====

interface ApiService {
    
    // GET request đơn giản
    @GET("users")
    suspend fun getUsers(): List<User>
    
    // GET với path parameter
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): User
    
    // GET với query parameters
    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: Int? = null): List<Post>
    
    // GET single post
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post
}

// ===== RETROFIT INSTANCE (Singleton) =====

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    
    // Logging để debug
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH RETROFIT:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  RETROFIT là gì?                                              ║
 * ║  → HTTP client cho Android                                    ║
 * ║  → Tự động chuyển đổi JSON ↔ Kotlin objects                   ║
 * ║  → Type-safe API calls                                        ║
 * ║                                                               ║
 * ║  ANNOTATIONS:                                                 ║
 * ║  @GET("users")           → HTTP GET request                   ║
 * ║  @POST("users")          → HTTP POST request                  ║
 * ║  @PUT, @DELETE           → PUT, DELETE requests               ║
 * ║                                                               ║
 * ║  @Path("id")             → Thay thế {id} trong URL            ║
 * ║  @Query("userId")        → Thêm ?userId=... vào URL           ║
 * ║  @Body                   → Request body (JSON)                ║
 * ║                                                               ║
 * ║  suspend fun:                                                 ║
 * ║  → Gọi từ coroutine                                           ║
 * ║  → Không block UI thread                                      ║
 * ║                                                               ║
 * ║  JSONPlaceholder:                                             ║
 * ║  → API test miễn phí (fake data)                              ║
 * ║  → https://jsonplaceholder.typicode.com/                      ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm data class Comment và endpoint getComments()
 * 2. Thêm endpoint getPostsByUser(userId) với @Query
 * 3. Tạo endpoint getUserPosts(userId) với @Path
 */
