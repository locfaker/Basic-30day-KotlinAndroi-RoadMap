/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 23 - BÀI 2: CÁC LOẠI LỖI                                 ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.util

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Custom Exception types
 */
sealed class AppException(message: String) : Exception(message) {
    class NetworkException(message: String = "Không có kết nối mạng") : AppException(message)
    class ServerException(val code: Int, message: String) : AppException(message)
    class TimeoutException(message: String = "Kết nối quá thời gian") : AppException(message)
    class UnknownException(message: String = "Lỗi không xác định") : AppException(message)
    class ValidationException(message: String) : AppException(message)
    class AuthException(message: String = "Phiên đăng nhập hết hạn") : AppException(message)
}

/**
 * Extension để map Exception sang AppException
 */
fun Throwable.toAppException(): AppException {
    return when (this) {
        is UnknownHostException -> AppException.NetworkException()
        is IOException -> AppException.NetworkException()
        is SocketTimeoutException -> AppException.TimeoutException()
        is HttpException -> {
            when (code()) {
                401, 403 -> AppException.AuthException()
                404 -> AppException.ServerException(404, "Không tìm thấy dữ liệu")
                500 -> AppException.ServerException(500, "Lỗi server")
                else -> AppException.ServerException(code(), message() ?: "Lỗi server")
            }
        }
        else -> AppException.UnknownException(message ?: "Lỗi không xác định")
    }
}

/**
 * Safe API call wrapper
 */
suspend fun <T> safeApiCall(call: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(call())
    } catch (e: Exception) {
        val appException = e.toAppException()
        Resource.Error(appException.message ?: "Lỗi không xác định", appException)
    }
}

// CÁCH DÙNG
class UserRepository {
    suspend fun getUsers(): Resource<List<User>> = safeApiCall {
        apiService.getUsers()
    }
    
    suspend fun getUser(id: Int): Resource<User> = safeApiCall {
        apiService.getUser(id)
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  XỬ LÝ LỖI THEO LOẠI:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  NetworkException:                                            ║
 * ║  → Hiển thị "Kiểm tra kết nối mạng"                           ║
 * ║  → Nút "Thử lại"                                              ║
 * ║                                                               ║
 * ║  TimeoutException:                                            ║
 * ║  → Hiển thị "Kết nối chậm, thử lại sau"                       ║
 * ║  → Auto retry với exponential backoff                         ║
 * ║                                                               ║
 * ║  ServerException (500):                                       ║
 * ║  → Hiển thị "Lỗi server, vui lòng thử lại"                    ║
 * ║  → Log lỗi để debug                                           ║
 * ║                                                               ║
 * ║  AuthException (401/403):                                     ║
 * ║  → Redirect đến màn hình Login                                ║
 * ║  → Clear session/token                                        ║
 * ║                                                               ║
 * ║  ValidationException:                                         ║
 * ║  → Hiển thị lỗi cụ thể cho user                               ║
 * ║  → Highlight field bị lỗi                                     ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
