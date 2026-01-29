/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 12 - BÀI 4: TODO APP HOÀN CHỈNH                          ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class cho Todo item
data class TodoItem(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp() {
    // States
    var todos by remember {
        mutableStateOf(
            listOf(
                TodoItem(1, "Học Jetpack Compose", false),
                TodoItem(2, "Làm bài tập Day 12", false),
                TodoItem(3, "Đọc documentation", true)
            )
        )
    }
    var newTodoText by remember { mutableStateOf("") }
    
    // Computed values
    val completedCount = todos.count { it.isCompleted }
    val totalCount = todos.size
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Todo App", fontWeight = FontWeight.Bold)
                        Text(
                            "$completedCount/$totalCount hoàn thành",
                            fontSize = 12.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    if (completedCount > 0) {
                        IconButton(
                            onClick = {
                                todos = todos.filter { !it.isCompleted }
                            }
                        ) {
                            Icon(Icons.Default.DeleteSweep, "Clear completed")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Progress bar
            if (totalCount > 0) {
                LinearProgressIndicator(
                    progress = { completedCount.toFloat() / totalCount },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF43A047)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Input
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newTodoText,
                    onValueChange = { newTodoText = it },
                    placeholder = { Text("Thêm công việc...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = {
                        if (newTodoText.isNotBlank()) {
                            val newId = (todos.maxOfOrNull { it.id } ?: 0) + 1
                            todos = todos + TodoItem(newId, newTodoText)
                            newTodoText = ""
                        }
                    },
                    enabled = newTodoText.isNotBlank()
                ) {
                    Icon(Icons.Default.Add, "Add")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Todo list
            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.CheckCircle,
                            null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text("Không có công việc", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos, key = { it.id }) { todo ->
                        TodoItemRow(
                            todo = todo,
                            onToggle = {
                                todos = todos.map {
                                    if (it.id == todo.id) {
                                        it.copy(isCompleted = !it.isCompleted)
                                    } else it
                                }
                            },
                            onDelete = {
                                todos = todos.filter { it.id != todo.id }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemRow(
    todo: TodoItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isCompleted) 
                Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF43A047)
                )
            )
            
            Text(
                text = todo.title,
                modifier = Modifier.weight(1f),
                textDecoration = if (todo.isCompleted) 
                    TextDecoration.LineThrough else TextDecoration.None,
                color = if (todo.isCompleted) Color.Gray else Color.Black
            )
            
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Close,
                    "Delete",
                    tint = Color(0xFFE53935)
                )
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH TODO APP:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  Đây là ví dụ TỔNG HỢP tất cả kiến thức Day 12:               ║
 * ║                                                               ║
 * ║  1. DATA CLASS:                                               ║
 * ║  data class TodoItem(id, title, isCompleted)                  ║
 * ║                                                               ║
 * ║  2. STATE:                                                    ║
 * ║  var todos by remember { mutableStateOf(listOf(...)) }        ║
 * ║                                                               ║
 * ║  3. COMPUTED VALUES:                                          ║
 * ║  val completedCount = todos.count { it.isCompleted }          ║
 * ║  → Tính toán từ state, tự động cập nhật                       ║
 * ║                                                               ║
 * ║  4. THÊM ITEM:                                                ║
 * ║  todos = todos + TodoItem(newId, newText)                     ║
 * ║                                                               ║
 * ║  5. CẬP NHẬT ITEM (toggle):                                   ║
 * ║  todos = todos.map {                                          ║
 * ║      if (it.id == targetId) it.copy(isCompleted = ...) else it║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  6. XÓA ITEM:                                                 ║
 * ║  todos = todos.filter { it.id != targetId }                   ║
 * ║                                                               ║
 * ║  7. STATE HOISTING:                                           ║
 * ║  TodoItemRow nhận todo, onToggle, onDelete từ parent          ║
 * ║  → Stateless, dễ tái sử dụng                                  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm field "priority" (Low/Medium/High) cho TodoItem
 * 2. Cho phép sửa title của todo (nhấn vào text để edit)
 * 3. Thêm chức năng filter: All / Active / Completed
 * 4. Lưu todos vào SharedPreferences (sẽ học ở Day sau)
 */
