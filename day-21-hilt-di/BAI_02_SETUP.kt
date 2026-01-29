/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 21 - BÀI 2: SETUP HILT                                   ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ============================================
// BƯỚC 1: Application class với @HiltAndroidApp
// File: MyApplication.kt
// ============================================

package com.example.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp  // Bắt buộc!
class MyApplication : Application()

// Thêm vào AndroidManifest.xml:
// <application
//     android:name=".MyApplication"
//     ...
// >

// ============================================
// BƯỚC 2: Activity với @AndroidEntryPoint
// File: MainActivity.kt
// ============================================

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // Cho phép inject vào Activity
class MainActivity : ComponentActivity() {
    // Bây giờ có thể inject dependencies
}

// ============================================
// BƯỚC 3: Module để provide dependencies
// File: di/AppModule.kt
// ============================================

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Scope: toàn app
object AppModule {
    
    @Provides
    @Singleton  // Chỉ tạo 1 instance
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    
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
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
    
    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao,
        apiService: ApiService
    ): UserRepository {
        return UserRepositoryImpl(userDao, apiService)
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH HILT ANNOTATIONS:                                 ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  @HiltAndroidApp                                              ║
 * ║  → Đặt trên Application class                                 ║
 * ║  → Trigger Hilt code generation                               ║
 * ║                                                               ║
 * ║  @AndroidEntryPoint                                           ║
 * ║  → Đặt trên Activity, Fragment, Service...                    ║
 * ║  → Cho phép inject dependencies                               ║
 * ║                                                               ║
 * ║  @Module + @InstallIn                                         ║
 * ║  → Module chứa các @Provides functions                        ║
 * ║  → @InstallIn xác định scope                                  ║
 * ║                                                               ║
 * ║  @Provides                                                    ║
 * ║  → Hàm cung cấp dependency                                    ║
 * ║  → Hilt tự gọi khi cần                                        ║
 * ║                                                               ║
 * ║  @Singleton                                                   ║
 * ║  → Chỉ tạo 1 instance trong toàn app                          ║
 * ║                                                               ║
 * ║  SCOPES:                                                      ║
 * ║  SingletonComponent: Toàn app                                 ║
 * ║  ActivityComponent: Per activity                              ║
 * ║  ViewModelComponent: Per ViewModel                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
