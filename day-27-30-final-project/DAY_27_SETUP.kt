/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 27: SETUP & DATA LAYER                                   ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ===== 1. build.gradle.kts (app) - DEPENDENCIES =====

/*
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

dependencies {
    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
}
*/

// ===== 2. DATA MODELS =====

package com.example.todoapp.data.model

// Priority Enum
enum class Priority(val level: Int, val label: String) {
    LOW(0, "Low"),
    MEDIUM(1, "Medium"),
    HIGH(2, "High")
}

// Category Enum  
enum class Category(val label: String, val icon: String) {
    PERSONAL("Personal", "person"),
    WORK("Work", "work"),
    SHOPPING("Shopping", "shopping_cart"),
    HEALTH("Health", "favorite"),
    OTHER("Other", "more_horiz")
}

// ===== 3. ENTITY =====

package com.example.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val category: Category = Category.PERSONAL,
    val dueDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

// ===== 4. DAO =====

package com.example.todoapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    
    // READ
    @Query("SELECT * FROM tasks ORDER BY isCompleted ASC, priority DESC, createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY priority DESC")
    fun getActiveTasks(): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY createdAt DESC")
    fun getCompletedTasks(): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?
    
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%'")
    fun searchTasks(query: String): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE priority = :priority ORDER BY createdAt DESC")
    fun getTasksByPriority(priority: Priority): Flow<List<TaskEntity>>
    
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long
    
    // UPDATE
    @Update
    suspend fun updateTask(task: TaskEntity)
    
    @Query("UPDATE tasks SET isCompleted = :completed WHERE id = :id")
    suspend fun updateTaskStatus(id: Int, completed: Boolean)
    
    // DELETE
    @Delete
    suspend fun deleteTask(task: TaskEntity)
    
    @Query("DELETE FROM tasks WHERE isCompleted = 1")
    suspend fun deleteCompletedTasks()
    
    // STATS
    @Query("SELECT COUNT(*) FROM tasks")
    fun getTotalCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>
}

// ===== 5. DATABASE =====

package com.example.todoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

// Type Converters cho Enum
class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.level
    
    @TypeConverter
    fun toPriority(level: Int): Priority = Priority.entries.first { it.level == level }
    
    @TypeConverter
    fun fromCategory(category: Category): String = category.name
    
    @TypeConverter
    fun toCategory(name: String): Category = Category.valueOf(name)
}

// ===== 6. REPOSITORY =====

package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.data.model.Priority
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    fun getActiveTasks(): Flow<List<TaskEntity>>
    fun getCompletedTasks(): Flow<List<TaskEntity>>
    fun searchTasks(query: String): Flow<List<TaskEntity>>
    suspend fun getTaskById(id: Int): TaskEntity?
    suspend fun addTask(task: TaskEntity): Long
    suspend fun updateTask(task: TaskEntity)
    suspend fun toggleTaskStatus(id: Int, completed: Boolean)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun deleteCompletedTasks()
}

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    
    override fun getAllTasks() = taskDao.getAllTasks()
    override fun getActiveTasks() = taskDao.getActiveTasks()
    override fun getCompletedTasks() = taskDao.getCompletedTasks()
    override fun searchTasks(query: String) = taskDao.searchTasks(query)
    override suspend fun getTaskById(id: Int) = taskDao.getTaskById(id)
    override suspend fun addTask(task: TaskEntity) = taskDao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)
    override suspend fun toggleTaskStatus(id: Int, completed: Boolean) = 
        taskDao.updateTaskStatus(id, completed)
    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)
    override suspend fun deleteCompletedTasks() = taskDao.deleteCompletedTasks()
}

// ===== 7. DI MODULE =====

package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo_database"
        ).build()
    }
    
    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}

// ===== 8. APPLICATION =====

package com.example.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication : Application()

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  CHECKLIST DAY 27:                                            ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  [✓] Tạo project mới                                          ║
 * ║  [✓] Thêm dependencies                                        ║
 * ║  [✓] Tạo Priority enum                                        ║
 * ║  [✓] Tạo Category enum                                        ║
 * ║  [✓] Tạo TaskEntity                                           ║
 * ║  [✓] Tạo TaskDao với CRUD operations                          ║
 * ║  [✓] Tạo AppDatabase                                          ║
 * ║  [✓] Tạo TaskRepository interface                             ║
 * ║  [✓] Tạo TaskRepositoryImpl                                   ║
 * ║  [✓] Setup Hilt modules                                       ║
 * ║  [✓] Tạo TodoApplication                                      ║
 * ║                                                               ║
 * ║  → Tiếp tục Day 28: UI Components                             ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
