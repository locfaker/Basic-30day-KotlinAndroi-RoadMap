/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 18 - BÀI 3: NOTES APP HOÀN CHỈNH                         ║
 * ║                                                               ║
 * ║  Đây là code hoàn chỉnh cho Notes App với Room                ║
 * ║  Copy từng phần vào các file tương ứng                        ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * CẤU TRÚC PROJECT:
 * 
 * app/src/main/java/com/example/myapplication/
 * ├── data/
 * │   ├── Note.kt           (Entity)
 * │   ├── NoteDao.kt        (DAO)
 * │   └── AppDatabase.kt    (Database)
 * ├── viewmodel/
 * │   └── NoteViewModel.kt  (ViewModel)
 * └── MainActivity.kt       (UI)
 * 
 * build.gradle.kts (app):
 * 
 * plugins {
 *     id("com.android.application")
 *     id("org.jetbrains.kotlin.android")
 *     id("com.google.devtools.ksp") version "1.9.21-1.0.15"
 * }
 * 
 * dependencies {
 *     // Room
 *     val room_version = "2.6.1"
 *     implementation("androidx.room:room-runtime:$room_version")
 *     implementation("androidx.room:room-ktx:$room_version")
 *     ksp("androidx.room:room-compiler:$room_version")
 *     
 *     // ViewModel
 *     implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
 *     
 *     // Compose (đã có sẵn)
 *     // ...
 * }
 */

// ===============================================
// FILE: data/Note.kt
// ===============================================
package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)

// ===============================================
// FILE: data/NoteDao.kt
// ===============================================
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    
    @Update
    suspend fun updateNote(note: Note)
    
    @Delete
    suspend fun deleteNote(note: Note)
    
    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}

// ===============================================
// FILE: data/AppDatabase.kt
// ===============================================
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

// ===============================================
// FILE: viewmodel/NoteViewModel.kt  
// ===============================================
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true
)

class NoteViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).noteDao()
    
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState
    
    init {
        viewModelScope.launch {
            dao.getAllNotes().collect { notes ->
                _uiState.value = NotesUiState(notes = notes, isLoading = false)
            }
        }
    }
    
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            dao.insertNote(Note(title = title, content = content))
        }
    }
    
    fun toggleNote(note: Note) {
        viewModelScope.launch {
            dao.updateNote(note.copy(isCompleted = !note.isCompleted))
        }
    }
    
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }
}

// ===============================================
// FILE: MainActivity.kt - Xem BAI_02_UI.kt
// ===============================================

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  TỔNG KẾT ROOM + MVVM:                                        ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  ┌────────────────────────────────────────────────────────┐   ║
 * ║  │                    UI LAYER                            │   ║
 * ║  │  MainActivity + Composables                            │   ║
 * ║  │  ↓ events        ↑ uiState                             │   ║
 * ║  └────────────────────────────────────────────────────────┘   ║
 * ║                         ↕                                     ║
 * ║  ┌────────────────────────────────────────────────────────┐   ║
 * ║  │                 VIEWMODEL LAYER                        │   ║
 * ║  │  NoteViewModel (xử lý logic, quản lý state)            │   ║
 * ║  └────────────────────────────────────────────────────────┘   ║
 * ║                         ↕                                     ║
 * ║  ┌────────────────────────────────────────────────────────┐   ║
 * ║  │                  DATA LAYER                            │   ║
 * ║  │  Room: Entity + DAO + Database                         │   ║
 * ║  │  SQLite Database                                       │   ║
 * ║  └────────────────────────────────────────────────────────┘   ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm chức năng Edit note
 * 2. Thêm chức năng Search
 * 3. Thêm chức năng Filter (All / Completed / Pending)
 * 4. Thêm màu sắc cho note (priority: Low/Medium/High)
 */
