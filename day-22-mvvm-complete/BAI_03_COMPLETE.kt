/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 22 - BÀI 3: APP MVVM HOÀN CHỈNH                          ║
 * ║                                                               ║
 * ║  Ví dụ Notes App với đầy đủ kiến trúc MVVM                    ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ============================================
// 1. DATA LAYER
// ============================================

// data/Note.kt (Entity)
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)

// data/NoteDao.kt
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)
    
    @Delete
    suspend fun delete(note: NoteEntity)
}

// data/NoteRepository.kt
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    val notes: Flow<List<NoteEntity>> = noteDao.getAllNotes()
    
    suspend fun addNote(title: String, content: String) {
        noteDao.insert(NoteEntity(title = title, content = content))
    }
    
    suspend fun deleteNote(note: NoteEntity) {
        noteDao.delete(note)
    }
}

// ============================================
// 2. UI LAYER
// ============================================

// ui/NoteUiState.kt
data class NoteUiState(
    val notes: List<NoteEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ui/NoteViewModel.kt
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.notes.collect { notes ->
                _uiState.value = NoteUiState(notes = notes, isLoading = false)
            }
        }
    }
    
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.addNote(title, content)
        }
    }
    
    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}

// ui/NoteScreen.kt
@Composable
fun NoteScreen(viewModel: NoteViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    when {
        uiState.isLoading -> LoadingScreen()
        uiState.error != null -> ErrorScreen(uiState.error!!)
        else -> NoteList(
            notes = uiState.notes,
            onDelete = { viewModel.deleteNote(it) }
        )
    }
}

// ============================================
// 3. DI MODULE
// ============================================

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "notes_db").build()
    }
    
    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao = database.noteDao()
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  TỔNG KẾT MVVM:                                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  ✅ Entity (Room) - Data model                                ║
 * ║  ✅ DAO - Database operations                                 ║
 * ║  ✅ Repository - Single source of truth                       ║
 * ║  ✅ ViewModel - UI logic, manage state                        ║
 * ║  ✅ UI State - Data cho UI                                    ║
 * ║  ✅ Composables - UI hiển thị                                 ║
 * ║  ✅ Hilt - Dependency Injection                               ║
 * ║  ✅ Flow/StateFlow - Reactive streams                         ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
