# Bài tập Day 17: Room Entity

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: User Entity (Dễ)
Tạo UserEntity với các field:
- id (auto generate)
- username (unique)
- email (unique)
- passwordHash
- createdAt

---

### Bài 2: Product Entity (Dễ)
Tạo ProductEntity cho app bán hàng:
- id
- name
- description (nullable)
- price (Double)
- stock (Int)
- imageUrl (nullable)
- isAvailable (default true)

---

### Bài 3: Note Entity với Metadata (Trung bình)
Tạo NoteEntity với:
- id
- title
- content
- color (hex string)
- isPinned
- Embedded: NoteMetadata (createdAt, updatedAt, wordCount)

---

### Bài 4: Order với Foreign Key (Trung bình)
Tạo cấu trúc:
```
CustomerEntity (id, name, email, phone)
    ↓
OrderEntity (id, customerId FK, totalAmount, status, createdAt)
    ↓
OrderItemEntity (orderId FK, productId FK, quantity, price)
```

---

### Bài 5: Task Entity với Enum (Khó)
Tạo TaskEntity với:
```kotlin
enum class TaskStatus { TODO, IN_PROGRESS, DONE }
enum class TaskPriority { LOW, MEDIUM, HIGH, URGENT }

data class TaskEntity(
    id, title, description,
    status: TaskStatus,
    priority: TaskPriority,
    dueDate: Date?,
    tags: List<String>,  // Cần TypeConverter
    assignedTo: String?,
    createdAt, updatedAt
)
```

Viết TypeConverters cho Enum và List<String>.

---

### Bài 6: Complete E-commerce Schema (Nâng cao)
Thiết kế schema cho app bán hàng:

```
CategoryEntity
    ↓ (1-n)
ProductEntity
    ↓ (n-n qua CartItemEntity)
CartEntity → CartItemEntity
    ↓
OrderEntity → OrderItemEntity
    ↓
UserEntity
```

Entities:
- CategoryEntity (id, name, icon, parentId nullable)
- ProductEntity (id, categoryId FK, name, price, discount, rating, reviewCount)
- CartEntity (id, userId FK, createdAt)
- CartItemEntity (cartId, productId, quantity)
- OrderEntity (id, userId, status, totalAmount, shippingAddress, paymentMethod)
- OrderItemEntity (orderId, productId, quantity, priceAtTime)

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao dùng Room thay vì SQLite trực tiếp?**
   > Gợi ý: Type safety, compile-time verification, less boilerplate.

2. **autoGenerate = true hoạt động thế nào?**
   > Gợi ý: SQLite AUTOINCREMENT.

3. **Khi nào dùng @Embedded vs Foreign Key?**
   > Gợi ý: Owned object vs referenced object.

4. **TypeConverter cần khi nào?**
   > Gợi ý: Non-primitive types (List, Date, Enum, custom objects).

5. **onDelete = CASCADE có ý nghĩa gì?**
   > Gợi ý: Khi xóa parent → tự động xóa children.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    @ColumnInfo(name = "password_hash")
    val passwordHash: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
```

**Bài 3:**
```kotlin
data class NoteMetadata(
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "word_count")
    val wordCount: Int = 0
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val color: String = "#FFFFFF",
    @ColumnInfo(name = "is_pinned")
    val isPinned: Boolean = false,
    @Embedded
    val metadata: NoteMetadata = NoteMetadata()
)
```

**Bài 5 - TypeConverters:**
```kotlin
class TaskConverters {
    @TypeConverter
    fun fromStatus(status: TaskStatus): String = status.name
    
    @TypeConverter
    fun toStatus(value: String): TaskStatus = TaskStatus.valueOf(value)
    
    @TypeConverter
    fun fromPriority(priority: TaskPriority): String = priority.name
    
    @TypeConverter
    fun toPriority(value: String): TaskPriority = TaskPriority.valueOf(value)
    
    @TypeConverter
    fun fromTags(tags: List<String>): String = tags.joinToString(",")
    
    @TypeConverter
    fun toTags(value: String): List<String> = 
        if (value.isEmpty()) emptyList() else value.split(",")
    
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
    
    @TypeConverter
    fun toDate(value: Long?): Date? = value?.let { Date(it) }
}
```
