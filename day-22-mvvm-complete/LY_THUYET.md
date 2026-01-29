# Day 22: MVVM Architecture Complete

---

## 🎯 MỤC TIÊU
Hoàn thiện kiến trúc MVVM với tất cả các layer đã học.

---

## PHẦN 1: MVVM OVERVIEW

```
┌─────────────────────────────────────────────────────────────────┐
│                       PRESENTATION LAYER                         │
│  ┌─────────────┐         ┌─────────────────────────────────┐   │
│  │   Screen    │ ←─────→ │         ViewModel               │   │
│  │ (Composable)│  State  │ - UI State (StateFlow)          │   │
│  └─────────────┘  Events │ - UI Events (Actions)           │   │
│                          │ - Business Logic                 │   │
│                          └───────────────┬─────────────────┘   │
│                                          │                      │
├──────────────────────────────────────────┼──────────────────────┤
│                       DOMAIN LAYER       │                      │
│                          ┌───────────────▼─────────────────┐   │
│                          │         Repository              │   │
│                          │      (Interface)                │   │
│                          └───────────────┬─────────────────┘   │
│                                          │                      │
├──────────────────────────────────────────┼──────────────────────┤
│                        DATA LAYER        │                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                 RepositoryImpl                           │   │
│  │  ┌─────────────────┐       ┌─────────────────┐          │   │
│  │  │   Local Source  │       │  Remote Source  │          │   │
│  │  │   (Room DAO)    │       │   (Retrofit)    │          │   │
│  │  └─────────────────┘       └─────────────────┘          │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

---

## PHẦN 2: UI STATE PATTERN

```kotlin
// Sealed interface cho Screen State
sealed interface NotesUiState {
    object Loading : NotesUiState
    data class Success(val notes: List<Note>) : NotesUiState
    data class Error(val message: String) : NotesUiState
}

// Hoặc Data class với tất cả states
data class NotesScreenState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedNoteId: Int? = null
)
```

---

## PHẦN 3: UI EVENTS

```kotlin
// Sealed class cho user actions
sealed class NotesEvent {
    object LoadNotes : NotesEvent()
    object RefreshNotes : NotesEvent()
    data class SearchNotes(val query: String) : NotesEvent()
    data class SelectNote(val id: Int) : NotesEvent()
    data class DeleteNote(val id: Int) : NotesEvent()
    object ClearError : NotesEvent()
}

// ViewModel xử lý events
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NotesScreenState())
    val uiState = _uiState.asStateFlow()
    
    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.LoadNotes -> loadNotes()
            is NotesEvent.RefreshNotes -> refreshNotes()
            is NotesEvent.SearchNotes -> search(event.query)
            is NotesEvent.SelectNote -> selectNote(event.id)
            is NotesEvent.DeleteNote -> deleteNote(event.id)
            is NotesEvent.ClearError -> clearError()
        }
    }
}
```

---

## PHẦN 4: ONE-TIME EVENTS (Side Effects)

```kotlin
// Channel cho one-time events (navigation, snackbar)
sealed class NotesEffect {
    data class ShowSnackbar(val message: String) : NotesEffect()
    data class NavigateToDetail(val noteId: Int) : NotesEffect()
    object NavigateBack : NotesEffect()
}

@HiltViewModel
class NotesViewModel @Inject constructor(...) : ViewModel() {
    
    private val _effects = Channel<NotesEffect>()
    val effects = _effects.receiveAsFlow()
    
    private fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id)
            _effects.send(NotesEffect.ShowSnackbar("Note deleted"))
        }
    }
}

// Collect trong Compose
@Composable
fun NotesScreen(viewModel: NotesViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is NotesEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is NotesEffect.NavigateToDetail -> {
                    navController.navigate("detail/${effect.noteId}")
                }
                NotesEffect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
}
```

---

## PHẦN 5: COMPLETE EXAMPLE

```kotlin
// ===== DOMAIN =====
data class Note(val id: Int, val title: String, val content: String)

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun createNote(title: String, content: String)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Int)
}

// ===== DATA =====
class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes() = dao.getAllNotes().map { it.map(NoteEntity::toNote) }
    override suspend fun getNoteById(id: Int) = dao.getNoteById(id)?.toNote()
    override suspend fun createNote(title: String, content: String) {
        dao.insert(NoteEntity(title = title, content = content))
    }
    override suspend fun updateNote(note: Note) = dao.update(note.toEntity())
    override suspend fun deleteNote(id: Int) = dao.deleteById(id)
}

// ===== PRESENTATION =====
data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class NotesEvent {
    object Load : NotesEvent()
    data class Delete(val id: Int) : NotesEvent()
}

sealed class NotesEffect {
    data class ShowSnackbar(val message: String) : NotesEffect()
}

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState = _uiState.asStateFlow()
    
    private val _effects = Channel<NotesEffect>()
    val effects = _effects.receiveAsFlow()
    
    init {
        viewModelScope.launch {
            repository.getNotes().collect { notes ->
                _uiState.update { it.copy(notes = notes, isLoading = false) }
            }
        }
    }
    
    fun onEvent(event: NotesEvent) {
        when (event) {
            NotesEvent.Load -> { /* Already observing */ }
            is NotesEvent.Delete -> deleteNote(event.id)
        }
    }
    
    private fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id)
            _effects.send(NotesEffect.ShowSnackbar("Deleted"))
        }
    }
}
```

---

## 📝 TÓM TẮT

| Concept | Purpose |
|---------|---------|
| UI State | Represent screen state |
| UI Event | User actions |
| UI Effect | One-time events (navigation, snackbar) |
| Repository | Abstract data sources |
| ViewModel | Business logic, state management |
