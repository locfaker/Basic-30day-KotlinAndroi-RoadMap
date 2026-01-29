# Bài tập Day 18: Room DAO & CRUD

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Note DAO cơ bản (Dễ)
Tạo NoteDao với:
- Insert note
- Get all notes (Flow)
- Get note by ID
- Delete note
- Update note

---

### Bài 2: Todo DAO với Toggle (Trung bình)
Tạo TodoDao với:
- Insert todo
- Get all todos
- Toggle completed
- Delete completed todos
- Count completed/total

---

### Bài 3: Product DAO với Search (Trung bình)
Tạo ProductDao với:
- Insert/Update/Delete product
- Get all products
- Search by name (LIKE)
- Get products by category
- Get products in price range
- Get out-of-stock products

---

### Bài 4: User DAO với Authentication (Khó)
Tạo UserDao với:
- Register (insert + check email unique)
- Login (get by email & password hash)
- Update profile
- Update password
- Check email exists
- Get user by ID

---

### Bài 5: Order DAO với Joins (Khó)
Tạo OrderDao để query order với details:
```kotlin
// Return Order with Customer name and item count
data class OrderWithDetails(
    val orderId: Int,
    val customerName: String,
    val totalAmount: Double,
    val itemCount: Int,
    val status: String
)
```
- Get orders with details
- Get orders by customer
- Get orders by status
- Calculate total revenue

---

### Bài 6: Complete Notes App DAO (Nâng cao)
Tạo NotesDao hoàn chỉnh:

```kotlin
data class NoteEntity(
    id, title, content, color, isPinned, 
    folderId, tags, createdAt, updatedAt
)

data class FolderEntity(id, name, color)
```

DAO:
- CRUD for notes
- CRUD for folders
- Get notes by folder
- Search notes
- Get pinned notes
- Get notes with tags
- Move note to folder
- Count notes per folder

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao CRUD methods cần `suspend` nhưng Query trả về Flow thì không?**
   > Gợi ý: One-time operation vs continuous observation.

2. **OnConflictStrategy.REPLACE khác IGNORE thế nào?**
   > Gợi ý: Replace xóa cũ thêm mới, Ignore bỏ qua.

3. **Khi nào dùng @Query thay vì @Update/@Delete?**
   > Gợi ý: Update/delete một phần, complex conditions.

4. **Flow từ Room tự động emit lại khi nào?**
   > Gợi ý: Khi table thay đổi.

5. **Làm sao test DAO?**
   > Gợi ý: In-memory database, AndroidJUnit4.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY created_at DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>
    
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?
    
    @Insert
    suspend fun insert(note: NoteEntity)
    
    @Update
    suspend fun update(note: NoteEntity)
    
    @Delete
    suspend fun delete(note: NoteEntity)
}
```

**Bài 2:**
```kotlin
@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY is_completed ASC, created_at DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    @Query("UPDATE todos SET is_completed = NOT is_completed WHERE id = :id")
    suspend fun toggleCompleted(id: Int)
    
    @Query("DELETE FROM todos WHERE is_completed = 1")
    suspend fun deleteCompleted()
    
    @Query("SELECT COUNT(*) FROM todos WHERE is_completed = 1")
    fun getCompletedCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM todos")
    fun getTotalCount(): Flow<Int>
}
```

**Bài 3:**
```kotlin
@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<ProductEntity>>
    
    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    fun getByCategory(categoryId: Int): Flow<List<ProductEntity>>
    
    @Query("SELECT * FROM products WHERE price BETWEEN :min AND :max")
    fun getInPriceRange(min: Double, max: Double): Flow<List<ProductEntity>>
    
    @Query("SELECT * FROM products WHERE stock = 0")
    fun getOutOfStock(): Flow<List<ProductEntity>>
}
```

**Bài 5 - Join Query:**
```kotlin
@Dao
interface OrderDao {
    @Query("""
        SELECT 
            o.id as orderId,
            c.name as customerName,
            o.total_amount as totalAmount,
            COUNT(oi.id) as itemCount,
            o.status
        FROM orders o
        INNER JOIN customers c ON o.customer_id = c.id
        LEFT JOIN order_items oi ON o.id = oi.order_id
        GROUP BY o.id
        ORDER BY o.created_at DESC
    """)
    fun getOrdersWithDetails(): Flow<List<OrderWithDetails>>
    
    @Query("SELECT SUM(total_amount) FROM orders WHERE status = 'completed'")
    suspend fun getTotalRevenue(): Double?
}
```
