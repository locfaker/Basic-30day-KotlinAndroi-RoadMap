/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 20 - BÀI 3: OFFLINE-FIRST STRATEGY                       ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.repository

import kotlinx.coroutines.flow.*

/**
 * Offline-First = Ưu tiên data local, sync với server khi có mạng
 */
class OfflineFirstRepository(
    private val userDao: UserDao,
    private val apiService: ApiService
) {
    
    /**
     * Lấy users với chiến lược Offline-First
     */
    fun getUsers(): Flow<Resource<List<User>>> = flow {
        // 1. Emit Loading
        emit(Resource.Loading)
        
        // 2. Emit data từ cache (nếu có)
        val cachedUsers = userDao.getAllUsersOnce()
        if (cachedUsers.isNotEmpty()) {
            emit(Resource.Success(cachedUsers))
        }
        
        // 3. Fetch từ network
        try {
            val remoteUsers = apiService.getUsers()
            // 4. Save vào cache
            userDao.insertUsers(remoteUsers)
            // 5. Emit fresh data
            emit(Resource.Success(remoteUsers))
        } catch (e: Exception) {
            // 6. Nếu có cache thì không emit error
            if (cachedUsers.isEmpty()) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }
    
    /**
     * Observe với auto-refresh
     */
    fun observeUsers(): Flow<Resource<List<User>>> = channelFlow {
        // Observe database
        launch {
            userDao.getAllUsers().collect { users ->
                send(Resource.Success(users))
            }
        }
        
        // Refresh from network
        try {
            send(Resource.Loading)
            val remoteUsers = apiService.getUsers()
            userDao.insertUsers(remoteUsers)
        } catch (e: Exception) {
            send(Resource.Error(e.message ?: "Network error"))
        }
    }
}

/**
 * Resource wrapper cho Loading/Success/Error
 */
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  OFFLINE-FIRST STRATEGIES:                                    ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. CACHE-FIRST:                                              ║
 * ║  → Luôn hiển thị cache trước                                  ║
 * ║  → Background refresh                                         ║
 * ║  → Tốt cho data ít thay đổi                                   ║
 * ║                                                               ║
 * ║  2. NETWORK-FIRST:                                            ║
 * ║  → Luôn fetch network trước                                   ║
 * ║  → Fallback cache khi offline                                 ║
 * ║  → Tốt cho data real-time                                     ║
 * ║                                                               ║
 * ║  3. STALE-WHILE-REVALIDATE:                                   ║
 * ║  → Hiển thị cache ngay                                        ║
 * ║  → Background fetch, update UI khi có data mới                ║
 * ║  → Best of both worlds                                        ║
 * ║                                                               ║
 * ║  FLOW:                                                        ║
 * ║  emit(Loading)                                                ║
 * ║     ↓                                                         ║
 * ║  emit(Success(cache))  ← Hiển thị cache ngay                  ║
 * ║     ↓                                                         ║
 * ║  fetch network                                                ║
 * ║     ↓                                                         ║
 * ║  save to cache                                                ║
 * ║     ↓                                                         ║
 * ║  emit(Success(fresh))  ← Update với data mới                  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm timestamp để kiểm tra data có stale không
 * 2. Implement network-first strategy
 * 3. Thêm retry logic khi network fail
 */
