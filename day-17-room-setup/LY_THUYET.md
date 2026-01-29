# Day 17: Room Database - Setup và Entity

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Hiểu **Room** là gì và tại sao dùng
2. Thiết lập Room trong project
3. Tạo **Entity** (bảng dữ liệu)
4. Hiểu cấu trúc Room: Entity, DAO, Database

---

## PHẦN 1: ROOM LÀ GÌ?

### 1.1 Định nghĩa

Room là **ORM (Object Relational Mapping)** của Android, giúp:
- Lưu trữ dữ liệu **cục bộ** (local) trên thiết bị
- Làm việc với SQLite **dễ dàng hơn**
- Compile-time verification cho SQL queries

### 1.2 Khi nào dùng Room?

| Dùng Room | Không cần Room |
|-----------|----------------|
| Lưu user profile | Chỉ cần preferences đơn giản |
| Cache API data | Real-time data không cần cache |
| Todo list, Notes | Temporary data |
| Offline-first app | Online-only app |

### 1.3 Cấu trúc Room

```
┌─────────────────────────────────────────────────────────┐
│                      Room Database                       │
├─────────────────────────────────────────────────────────┤
│                                                          │
│   ┌─────────────┐         ┌─────────────┐              │
│   │   Entity    │ ←────── │    DAO      │              │
│   │  (Bảng)     │         │ (Truy vấn)  │              │
│   └─────────────┘         └─────────────┘              │
│        ↑                         ↑                      │
│        │                         │                      │
│   @Entity                  @Dao                        │
│   data class               interface                    │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

---

## PHẦN 2: THÊM DEPENDENCIES

### 2.1 Trong build.gradle.kts (project)

```kotlin
plugins {
    // Thêm KSP plugin (cho Room)
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}
```

### 2.2 Trong build.gradle.kts (app)

```kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    val roomVersion = "2.6.1"
    
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")  // Coroutines support
    ksp("androidx.room:room-compiler:$roomVersion")
}
```

---

## PHẦN 3: ENTITY - ĐỊNH NGHĨA BẢNG

### 3.1 Entity cơ bản

```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val createdAt: Long = System.currentTimeMillis()
)
```

### 3.2 Các Annotation quan trọng

```kotlin
@Entity(tableName = "notes")  // Tên bảng
data class Note(
    @PrimaryKey(autoGenerate = true)  // Khóa chính, tự tăng
    val id: Int = 0,
    
    @ColumnInfo(name = "title")  // Tên cột custom
    val title: String,
    
    @ColumnInfo(name = "content")
    val content: String,
    
    @ColumnInfo(name = "is_pinned", defaultValue = "0")
    val isPinned: Boolean = false,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
```

### 3.3 Ignore fields

```kotlin
@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    
    @Ignore  // Không lưu vào database
    val tempDiscountPercent: Int = 0
)
```

### 3.4 Composite Primary Key

```kotlin
@Entity(
    tableName = "order_items",
    primaryKeys = ["orderId", "productId"]
)
data class OrderItem(
    val orderId: Int,
    val productId: Int,
    val quantity: Int
)
```

### 3.5 Foreign Key (Khóa ngoại)

```kotlin
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: Int,
    val name: String
)

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE  // Xóa category → xóa products
        )
    ]
)
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String,
    val categoryId: Int  // Tham chiếu đến Category
)
```

### 3.6 Index (Chỉ mục)

```kotlin
@Entity(
    tableName = "users",
    indices = [
        Index(value = ["email"], unique = true),  // Email unique
        Index(value = ["name"])  // Index để search nhanh
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String
)
```

---

## PHẦN 4: VÍ DỤ THỰC TẾ

### 4.1 Todo Entity

```kotlin
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "title")
    val title: String,
    
    @ColumnInfo(name = "description")
    val description: String = "",
    
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    
    @ColumnInfo(name = "priority")
    val priority: Int = 0,  // 0: Low, 1: Medium, 2: High
    
    @ColumnInfo(name = "due_date")
    val dueDate: Long? = null,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
```

### 4.2 Note Entity với Tags (Embedded)

```kotlin
data class NoteMetadata(
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val author: String = ""
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val isPinned: Boolean = false,
    val color: String = "#FFFFFF",
    
    @Embedded  // Nhúng object vào cùng bảng
    val metadata: NoteMetadata = NoteMetadata()
)
```

### 4.3 User Profile Entity

```kotlin
@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey
    val id: String,  // Firebase UID hoặc từ API
    
    @ColumnInfo(name = "display_name")
    val displayName: String,
    
    @ColumnInfo(name = "email")
    val email: String,
    
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,
    
    @ColumnInfo(name = "phone")
    val phone: String? = null,
    
    @ColumnInfo(name = "is_premium")
    val isPremium: Boolean = false,
    
    @ColumnInfo(name = "last_login")
    val lastLogin: Long = System.currentTimeMillis()
)
```

---

## PHẦN 5: TYPE CONVERTERS

Room chỉ hỗ trợ primitive types. Để lưu List, Date, Enum... cần TypeConverter.

### 5.1 TypeConverter cho List

```kotlin
class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",")
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }
}
```

### 5.2 TypeConverter cho Date

```kotlin
class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
```

### 5.3 TypeConverter cho Enum

```kotlin
enum class Priority { LOW, MEDIUM, HIGH }

class EnumConverters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }
    
    @TypeConverter
    fun toPriority(value: String): Priority {
        return Priority.valueOf(value)
    }
}
```

---

## 📝 TÓM TẮT

| Annotation | Chức năng |
|------------|-----------|
| `@Entity` | Đánh dấu data class là bảng |
| `@PrimaryKey` | Khóa chính |
| `@ColumnInfo` | Tùy chỉnh tên cột |
| `@Ignore` | Không lưu field này |
| `@Embedded` | Nhúng object |
| `@ForeignKey` | Khóa ngoại |
| `@TypeConverter` | Chuyển đổi kiểu dữ liệu |

---

## ➡️ NGÀY MAI
**Day 18: Room Database - DAO và CRUD**
- Data Access Object (DAO)
- Insert, Update, Delete
- Query với SELECT
- Flow và Room
