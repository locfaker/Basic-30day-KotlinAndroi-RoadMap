/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 18 - BÀI 1: VIEWMODEL VỚI ROOM                           ║
 * ║                                                               ║
 * ║  Tạo file: viewmodel/NoteViewModel.kt                         ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// UI State
data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * AndroidViewModel = ViewModel có Application context
 * Cần context để khởi tạo database
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val noteDao: NoteDao = AppDatabase.getDatabase(application).noteDao()
    
    private val _uiState = MutableStateFlow(NotesUiState(isLoading = true))
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()
    
    init {
        // Collect Flow từ database
        viewModelScope.launch {
            noteDao.getAllNotes().collect { notes ->
                _uiState.value = NotesUiState(notes = notes, isLoading = false)
            }
        }
    }
    
    // Thêm note
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val note = Note(title = title, content = content)
            noteDao.insertNote(note)
            // Không cần cập nhật uiState vì Flow tự emit
        }
    }
    
    // Cập nhật note
    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }
    
    // Toggle completed
    fun toggleCompleted(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note.copy(isCompleted = !note.isCompleted))
        }
    }
    
    // Xóa note
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }
    
    // Xóa tất cả
    fun deleteAllNotes() {
        viewModelScope.launch {
            noteDao.deleteAllNotes()
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH VIEWMODEL VỚI ROOM:                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  AndroidViewModel:                                            ║
 * ║  → ViewModel có Application context                           ║
 * ║  → Dùng getApplication<Application>() để lấy context          ║
 * ║  → Cần context để khởi tạo database                           ║
 * ║                                                               ║
 * ║  FLOW REACTIVE:                                               ║
 * ║  noteDao.getAllNotes() trả về Flow<List<Note>>                ║
 * ║  → Khi database thay đổi, Flow tự động emit giá trị mới       ║
 * ║  → UI tự động cập nhật                                        ║
 * ║                                                               ║
 * ║  viewModelScope.launch { }:                                   ║
 * ║  → Chạy coroutine trong ViewModel scope                       ║
 * ║  → Tự động cancel khi ViewModel destroy                       ║
 * ║                                                               ║
 * ║  FLOW:                                                        ║
 * ║  User action → ViewModel → DAO → Database                     ║
 * ║       ↑                              ↓                        ║
 * ║      UI ←──── StateFlow ←──── Flow emit                       ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm hàm searchNotes(query: String)
 * 2. Thêm hàm getCompletedNotes() và getPendingNotes()
 * 3. Thêm hàm deleteCompletedNotes()
 */
