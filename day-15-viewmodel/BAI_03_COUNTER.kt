/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 15 - BÀI 3: TODO APP VỚI VIEWMODEL                       ║
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Data class
data class Todo(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)

// UI State
data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val newTodoText: String = ""
) {
    val completedCount: Int get() = todos.count { it.isCompleted }
    val totalCount: Int get() = todos.size
}

// ===== VIEWMODEL =====
class TodoViewModel : ViewModel() {
    var uiState by mutableStateOf(TodoUiState())
        private set
    
    private var nextId = 1
    
    fun updateNewTodoText(text: String) {
        uiState = uiState.copy(newTodoText = text)
    }
    
    fun addTodo() {
        if (uiState.newTodoText.isBlank()) return
        
        val newTodo = Todo(id = nextId++, title = uiState.newTodoText)
        uiState = uiState.copy(
            todos = uiState.todos + newTodo,
            newTodoText = ""
        )
    }
    
    fun toggleTodo(todoId: Int) {
        uiState = uiState.copy(
            todos = uiState.todos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(isCompleted = !todo.isCompleted)
                } else todo
            }
        )
    }
    
    fun deleteTodo(todoId: Int) {
        uiState = uiState.copy(
            todos = uiState.todos.filter { it.id != todoId }
        )
    }
    
    fun clearCompleted() {
        uiState = uiState.copy(
            todos = uiState.todos.filter { !it.isCompleted }
        )
    }
}

// ===== MAIN ACTIVITY =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: TodoViewModel = viewModel()
            TodoScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    val uiState = viewModel.uiState
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Todo App với ViewModel")
                        Text(
                            "${uiState.completedCount}/${uiState.totalCount} hoàn thành",
                            fontSize = 12.sp
                        )
                    }
                },
                actions = {
                    if (uiState.completedCount > 0) {
                        IconButton(onClick = { viewModel.clearCompleted() }) {
                            Icon(Icons.Default.DeleteSweep, "Clear")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Input
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = uiState.newTodoText,
                    onValueChange = { viewModel.updateNewTodoText(it) },
                    placeholder = { Text("Thêm công việc...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = { viewModel.addTodo() },
                    enabled = uiState.newTodoText.isNotBlank()
                ) {
                    Icon(Icons.Default.Add, "Add")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress
            if (uiState.totalCount > 0) {
                LinearProgressIndicator(
                    progress = { uiState.completedCount.toFloat() / uiState.totalCount },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Todo List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.todos, key = { it.id }) { todo ->
                    TodoItemRow(
                        todo = todo,
                        onToggle = { viewModel.toggleTodo(todo.id) },
                        onDelete = { viewModel.deleteTodo(todo.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoItemRow(
    todo: Todo,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggle() }
            )
            
            Text(
                text = todo.title,
                modifier = Modifier.weight(1f),
                textDecoration = if (todo.isCompleted) 
                    TextDecoration.LineThrough else TextDecoration.None,
                color = if (todo.isCompleted) Color.Gray else Color.Black
            )
            
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, "Delete", tint = Color.Red)
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH TODO APP VỚI VIEWMODEL:                           ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  KIẾN TRÚC:                                                   ║
 * ║  ┌─────────┐      ┌─────────────┐      ┌──────────┐           ║
 * ║  │   UI    │ ←─── │  ViewModel  │ ←─── │ UI State │           ║
 * ║  │(Screen) │ ───→ │  (Logic)    │                             ║
 * ║  └─────────┘      └─────────────┘      └──────────┘           ║
 * ║       ↓                  ↑                                    ║
 * ║    Events          State Updates                              ║
 * ║                                                               ║
 * ║  UI → gọi hàm ViewModel (addTodo, toggleTodo...)              ║
 * ║  ViewModel → cập nhật UI State                                ║
 * ║  UI State thay đổi → UI tự động cập nhật                      ║
 * ║                                                               ║
 * ║  COMPUTED PROPERTIES trong UI State:                          ║
 * ║  val completedCount: Int get() = todos.count { it.isCompleted }
 * ║  → Tính toán từ todos, không lưu riêng                        ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm chức năng filter: All / Active / Completed
 * 2. Thêm chức năng edit todo title
 * 3. Xoay màn hình và kiểm tra todos vẫn còn
 */
