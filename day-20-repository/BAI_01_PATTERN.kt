/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 20 - BÀI 1: REPOSITORY PATTERN                           ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  REPOSITORY PATTERN LÀ GÌ?                                    ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  KHÔNG CÓ REPOSITORY:                                         ║
 * ║  ┌────────────┐      ┌─────────────┐                          ║
 * ║  │ ViewModel  │ ───→ │ Room DAO    │                          ║
 * ║  │            │ ───→ │ Retrofit    │                          ║
 * ║  │            │ ───→ │ SharedPrefs │                          ║
 * ║  └────────────┘      └─────────────┘                          ║
 * ║  → ViewModel phải biết chi tiết từng data source              ║
 * ║  → Khó test, khó thay đổi                                     ║
 * ║                                                               ║
 * ║  CÓ REPOSITORY:                                               ║
 * ║  ┌────────────┐      ┌────────────┐      ┌─────────────┐      ║
 * ║  │ ViewModel  │ ───→ │ Repository │ ───→ │ Room DAO    │      ║
 * ║  │            │      │            │ ───→ │ Retrofit    │      ║
 * ║  └────────────┘      └────────────┘      └─────────────┘      ║
 * ║  → ViewModel chỉ biết Repository                              ║
 * ║  → Repository quyết định lấy data từ đâu                      ║
 * ║  → Dễ test, dễ thay đổi                                       ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.repository

import com.example.myapplication.data.User
import com.example.myapplication.data.UserDao
import com.example.myapplication.network.ApiService
import kotlinx.coroutines.flow.Flow

/**
 * Interface Repository - Định nghĩa contract
 * ViewModel chỉ biết interface này
 */
interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers()
    suspend fun getUserById(id: Int): User?
}

/**
 * Implementation với Room + Retrofit
 */
class UserRepositoryImpl(
    private val userDao: UserDao,      // Local database
    private val apiService: ApiService  // Remote API
) : UserRepository {
    
    // Lấy từ local database (reactive)
    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
    
    // Refresh từ API, save vào local
    override suspend fun refreshUsers() {
        val remoteUsers = apiService.getUsers()
        userDao.insertUsers(remoteUsers)
    }
    
    // Lấy 1 user
    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  LỢI ÍCH CỦA REPOSITORY:                                      ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. SINGLE SOURCE OF TRUTH:                                   ║
 * ║  → Database là nguồn chính                                    ║
 * ║  → UI luôn observe database                                   ║
 * ║  → API chỉ refresh/update database                            ║
 * ║                                                               ║
 * ║  2. SEPARATION OF CONCERNS:                                   ║
 * ║  → ViewModel: UI logic                                        ║
 * ║  → Repository: Data logic                                     ║
 * ║  → DAO/API: Data access                                       ║
 * ║                                                               ║
 * ║  3. TESTABILITY:                                              ║
 * ║  → Dễ mock Repository khi test ViewModel                      ║
 * ║  → Dễ test Repository với fake DAO/API                        ║
 * ║                                                               ║
 * ║  4. FLEXIBILITY:                                              ║
 * ║  → Dễ thêm cache, retry, offline logic                        ║
 * ║  → Dễ thay đổi data source                                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo interface PostRepository
 * 2. Implement PostRepositoryImpl
 * 3. Thêm hàm deleteUser() vào interface
 */
