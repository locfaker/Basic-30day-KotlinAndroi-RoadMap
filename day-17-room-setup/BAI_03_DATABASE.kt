/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 17 - BÀI 3: DATABASE                                     ║
 * ║                                                               ║
 * ║  Tạo file: data/AppDatabase.kt                                ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @Database = Đánh dấu đây là Room Database
 * entities = Danh sách các Entity (bảng)
 * version = Phiên bản database (tăng khi thay đổi schema)
 */
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    // Abstract function trả về DAO
    abstract fun noteDao(): NoteDao
    
    companion object {
        // Singleton pattern
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"  // Tên file database
                )
                .fallbackToDestructiveMigration()  // Xóa data cũ khi version thay đổi
                .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH DATABASE:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  DATABASE là gì?                                              ║
 * ║  → Điểm truy cập chính vào SQLite database                    ║
 * ║  → Chứa các DAO                                               ║
 * ║                                                               ║
 * ║  SINGLETON PATTERN:                                           ║
 * ║  → Chỉ có 1 instance duy nhất trong app                       ║
 * ║  → Tránh mở nhiều connection                                  ║
 * ║  → @Volatile đảm bảo visibility giữa threads                  ║
 * ║  → synchronized đảm bảo thread-safe                           ║
 * ║                                                               ║
 * ║  CÁCH DÙNG:                                                   ║
 * ║  val database = AppDatabase.getDatabase(context)              ║
 * ║  val noteDao = database.noteDao()                             ║
 * ║  val notes = noteDao.getAllNotes()                            ║
 * ║                                                               ║
 * ║  CẤU TRÚC FILE:                                               ║
 * ║  app/                                                         ║
 * ║  └── src/main/java/com/example/myapplication/                 ║
 * ║      ├── data/                                                ║
 * ║      │   ├── Note.kt         (Entity)                         ║
 * ║      │   ├── NoteDao.kt      (DAO)                            ║
 * ║      │   └── AppDatabase.kt  (Database)                       ║
 * ║      └── MainActivity.kt                                      ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm Entity User và UserDao vào database
 * 2. Thay đổi version thành 2 và thêm Entity mới
 * 3. Tạo hàm populateDatabase() để thêm data mẫu
 */
