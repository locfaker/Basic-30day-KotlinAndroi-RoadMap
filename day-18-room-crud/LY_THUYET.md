# Day 18: Room Database - DAO và CRUD Operations

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **DAO** (Data Access Object) là gì
2. Thực hiện **CRUD** operations (Create, Read, Update, Delete)
3. Viết **Query** với Room
4. Sử dụng **Flow** để observe data changes
5. Tạo **Database** class

---

## PHẦN 1: DAO LÀ GÌ?

### 1.1 Định nghĩa

DAO (Data Access Object) là **interface** định nghĩa các phương thức để tương tác với database.

```kotlin
@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)
    
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
}
```

### 1.2 Cấu trúc tổng quan

```
App                          Database
 │                              │
 ├── ViewModel ─────────────────┤
 │       │                      │
 │       ▼                      │
 │     DAO ──────────────────► Room
 │       │                      │
 │       ▼                      │
 │    Entity ◄──────────────── SQLite
```

---

## PHẦN 2: CRUD OPERATIONS

### 2.1 INSERT - Thêm dữ liệu

```kotlin
@Dao
interface NoteDao {
    // Insert một entity
    @Insert
    suspend fun insert(note: NoteEntity)
    
    // Insert nhiều entities
    @Insert
    suspend fun insertAll(notes: List<NoteEntity>)
    
    // Insert và trả về ID
    @Insert
    suspend fun insertAndGetId(note: NoteEntity): Long
    
    // Xử lý conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: NoteEntity)
}
```

**OnConflictStrategy:**
| Strategy | Hành vi |
|----------|---------|
| `ABORT` | Hủy transaction (default) |
| `REPLACE` | Xóa cũ, thêm mới |
| `IGNORE` | Bỏ qua nếu trùng |

### 2.2 UPDATE - Cập nhật

```kotlin
@Dao
interface NoteDao {
    // Update entity (theo primary key)
    @Update
    suspend fun update(note: NoteEntity)
    
    // Update nhiều entities
    @Update
    suspend fun updateAll(notes: List<NoteEntity>)
    
    // Update với Query (linh hoạt hơn)
    @Query("UPDATE notes SET is_pinned = :isPinned WHERE id = :id")
    suspend fun updatePinned(id: Int, isPinned: Boolean)
    
    @Query("UPDATE notes SET title = :title, content = :content WHERE id = :id")
    suspend fun updateContent(id: Int, title: String, content: String)
}
```

### 2.3 DELETE - Xóa

```kotlin
@Dao
interface NoteDao {
    // Delete entity
    @Delete
    suspend fun delete(note: NoteEntity)
    
    // Delete nhiều entities
    @Delete
    suspend fun deleteAll(notes: List<NoteEntity>)
    
    // Delete by ID
    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Int)
    
    // Delete all
    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
    
    // Delete completed
    @Query("DELETE FROM notes WHERE is_completed = 1")
    suspend fun deleteCompleted()
}
```

---

## PHẦN 3: QUERY - TRUY VẤN

### 3.1 SELECT cơ bản

```kotlin
@Dao
interface NoteDao {
    // Lấy tất cả (Flow - reactive)
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>
    
    // Lấy tất cả (suspend - one-time)
    @Query("SELECT * FROM notes")
    suspend fun getAllNotesOnce(): List<NoteEntity>
    
    // Lấy theo ID
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?
    
    // Lấy theo ID (Flow)
    @Query("SELECT * FROM notes WHERE id = :id")
    fun observeNoteById(id: Int): Flow<NoteEntity?>
}
```

### 3.2 WHERE conditions

```kotlin
@Dao
interface NoteDao {
    // Điều kiện đơn
    @Query("SELECT * FROM notes WHERE is_pinned = 1")
    fun getPinnedNotes(): Flow<List<NoteEntity>>
    
    // Nhiều điều kiện
    @Query("SELECT * FROM notes WHERE is_pinned = :pinned AND is_completed = :completed")
    fun getNotes(pinned: Boolean, completed: Boolean): Flow<List<NoteEntity>>
    
    // LIKE search
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    
    // IN clause
    @Query("SELECT * FROM notes WHERE id IN (:ids)")
    suspend fun getNotesByIds(ids: List<Int>): List<NoteEntity>
}
```

### 3.3 ORDER BY và LIMIT

```kotlin
@Dao
interface NoteDao {
    // Sắp xếp
    @Query("SELECT * FROM notes ORDER BY created_at DESC")
    fun getNotesOrderByDate(): Flow<List<NoteEntity>>
    
    // Pinned first, then by date
    @Query("""
        SELECT * FROM notes 
        ORDER BY is_pinned DESC, created_at DESC
    """)
    fun getSortedNotes(): Flow<List<NoteEntity>>
    
    // Limit
    @Query("SELECT * FROM notes ORDER BY created_at DESC LIMIT :limit")
    fun getRecentNotes(limit: Int): Flow<List<NoteEntity>>
    
    // Pagination
    @Query("SELECT * FROM notes ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getNotesPage(limit: Int, offset: Int): List<NoteEntity>
}
```

### 3.4 Aggregate functions

```kotlin
@Dao
interface NoteDao {
    // Count
    @Query("SELECT COUNT(*) FROM notes")
    fun getNotesCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM notes WHERE is_completed = 1")
    suspend fun getCompletedCount(): Int
    
    // Check exists
    @Query("SELECT EXISTS(SELECT 1 FROM notes WHERE id = :id)")
    suspend fun noteExists(id: Int): Boolean
}
```

---

## PHẦN 4: FLOW VÀ ROOM

### 4.1 Tại sao dùng Flow?

Flow cho phép **observe** changes trong database - khi data thay đổi, UI tự động cập nhật.

```kotlin
// DAO
@Query("SELECT * FROM notes")
fun getAllNotes(): Flow<List<NoteEntity>>  // Flow, không phải suspend

// ViewModel
class NotesViewModel(private val dao: NoteDao) : ViewModel() {
    val notes: StateFlow<List<NoteEntity>> = dao.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

// Compose
@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val notes by viewModel.notes.collectAsState()
    // UI tự động cập nhật khi notes thay đổi
}
```

---

## PHẦN 5: DATABASE CLASS

### 5.1 Tạo Database

```kotlin
@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

### 5.2 Database với nhiều Entity

```kotlin
@Database(
    entities = [
        UserEntity::class,
        NoteEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
}
```

---

## PHẦN 6: VÍ DỤ HOÀN CHỈNH

### 6.1 Entity

```kotlin
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
```

### 6.2 DAO

```kotlin
@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity?
    
    @Insert
    suspend fun insert(todo: TodoEntity)
    
    @Update
    suspend fun update(todo: TodoEntity)
    
    @Delete
    suspend fun delete(todo: TodoEntity)
    
    @Query("UPDATE todos SET isCompleted = :completed WHERE id = :id")
    suspend fun updateCompleted(id: Int, completed: Boolean)
}
```

### 6.3 ViewModel

```kotlin
class TodoViewModel(private val dao: TodoDao) : ViewModel() {
    val todos = dao.getAllTodos().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    
    fun addTodo(title: String) {
        viewModelScope.launch {
            dao.insert(TodoEntity(title = title))
        }
    }
    
    fun toggleTodo(todo: TodoEntity) {
        viewModelScope.launch {
            dao.updateCompleted(todo.id, !todo.isCompleted)
        }
    }
    
    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            dao.delete(todo)
        }
    }
}
```

---

## 📝 TÓM TẮT

| Annotation | Chức năng |
|------------|-----------|
| `@Dao` | Đánh dấu interface là DAO |
| `@Insert` | Thêm data |
| `@Update` | Cập nhật data |
| `@Delete` | Xóa data |
| `@Query` | Custom SQL query |
| `@Database` | Đánh dấu class là Database |

---

## ➡️ NGÀY MAI
**Day 19: Retrofit - API Calls**
- Retrofit là gì?
- Thiết lập Retrofit
- Định nghĩa API interface
- GET, POST requests
