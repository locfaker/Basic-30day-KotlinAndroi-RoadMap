/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 17 - BÀI 2: DAO (DATA ACCESS OBJECT)                     ║
 * ║                                                               ║
 * ║  Tạo file: data/NoteDao.kt                                    ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @Dao = Đánh dấu đây là Data Access Object
 * Chứa các hàm để tương tác với database
 */
@Dao
interface NoteDao {
    
    // ===== READ (Đọc) =====
    
    /**
     * @Query = Viết câu SQL tùy ý
     * Flow<List<Note>> = Tự động emit khi data thay đổi
     */
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?
    
    @Query("SELECT * FROM notes WHERE isCompleted = :completed")
    fun getNotesByStatus(completed: Boolean): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<Note>>
    
    // ===== CREATE (Tạo) =====
    
    /**
     * @Insert = Thêm row mới
     * onConflict = Xử lý khi trùng primary key
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    
    @Insert
    suspend fun insertNotes(notes: List<Note>)
    
    // ===== UPDATE (Cập nhật) =====
    
    @Update
    suspend fun updateNote(note: Note)
    
    @Query("UPDATE notes SET isCompleted = :completed WHERE id = :noteId")
    suspend fun updateNoteStatus(noteId: Int, completed: Boolean)
    
    // ===== DELETE (Xóa) =====
    
    @Delete
    suspend fun deleteNote(note: Note)
    
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)
    
    @Query("DELETE FROM notes WHERE isCompleted = 1")
    suspend fun deleteCompletedNotes()
    
    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH DAO:                                              ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  DAO là gì?                                                   ║
 * ║  → Interface định nghĩa các thao tác với database             ║
 * ║  → Room tự động tạo implementation                            ║
 * ║                                                               ║
 * ║  ANNOTATIONS:                                                 ║
 * ║  @Insert  → Thêm mới                                          ║
 * ║  @Update  → Cập nhật                                          ║
 * ║  @Delete  → Xóa                                               ║
 * ║  @Query   → SQL tùy ý                                         ║
 * ║                                                               ║
 * ║  SUSPEND FUNCTIONS:                                           ║
 * ║  suspend fun insertNote(...)                                  ║
 * ║  → Chạy trên background thread                                ║
 * ║  → Phải gọi từ coroutine                                      ║
 * ║                                                               ║
 * ║  FLOW:                                                        ║
 * ║  fun getAllNotes(): Flow<List<Note>>                          ║
 * ║  → Tự động emit giá trị mới khi data thay đổi                 ║
 * ║  → Không cần suspend, reactive                                ║
 * ║                                                               ║
 * ║  QUERY PARAMETERS:                                            ║
 * ║  :noteId, :completed, :query                                  ║
 * ║  → Tham số truyền vào hàm                                     ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm query đếm số note: getNotesCount(): Int
 * 2. Thêm query lấy note mới nhất: getLatestNote(): Note?
 * 3. Tạo UserDao với các hàm CRUD tương tự
 */
