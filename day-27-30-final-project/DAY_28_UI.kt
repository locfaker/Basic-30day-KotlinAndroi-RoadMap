/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 28: UI COMPONENTS & TASKS SCREEN                         ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ===== 1. THEME =====

// ui/theme/Color.kt
package com.example.todoapp.ui.theme

import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFF6200EE)
val PrimaryDark = Color(0xFFBB86FC)
val Secondary = Color(0xFF03DAC6)

val HighPriority = Color(0xFFE53935)
val MediumPriority = Color(0xFFFF9800)
val LowPriority = Color(0xFF4CAF50)

val BackgroundLight = Color(0xFFF5F5F5)
val BackgroundDark = Color(0xFF121212)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFF1E1E1E)

// ===== 2. VIEWMODEL =====

// ui/screens/tasks/TasksViewModel.kt
package com.example.todoapp.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TaskFilter { ALL, ACTIVE, COMPLETED }

data class TasksUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val filter: TaskFilter = TaskFilter.ALL,
    val searchQuery: String = "",
    val isLoading: Boolean = true
)

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()
    
    private val _filter = MutableStateFlow(TaskFilter.ALL)
    private val _searchQuery = MutableStateFlow("")
    
    init {
        viewModelScope.launch {
            combine(_filter, _searchQuery) { filter, query -> 
                Pair(filter, query) 
            }.collectLatest { (filter, query) ->
                val tasksFlow = when {
                    query.isNotBlank() -> repository.searchTasks(query)
                    filter == TaskFilter.ACTIVE -> repository.getActiveTasks()
                    filter == TaskFilter.COMPLETED -> repository.getCompletedTasks()
                    else -> repository.getAllTasks()
                }
                
                tasksFlow.collect { tasks ->
                    _uiState.value = TasksUiState(
                        tasks = tasks,
                        filter = filter,
                        searchQuery = query,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    fun setFilter(filter: TaskFilter) {
        _filter.value = filter
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun toggleTask(taskId: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.toggleTaskStatus(taskId, completed)
        }
    }
    
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
    
    fun deleteCompletedTasks() {
        viewModelScope.launch {
            repository.deleteCompletedTasks()
        }
    }
}

// ===== 3. UI COMPONENTS =====

// ui/components/TaskItem.kt
package com.example.todoapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.data.model.Priority

@Composable
fun TaskItem(
    task: TaskEntity,
    onToggle: () -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val priorityColor = when (task.priority) {
        Priority.HIGH -> Color(0xFFE53935)
        Priority.MEDIUM -> Color(0xFFFF9800)
        Priority.LOW -> Color(0xFF4CAF50)
    }
    
    val backgroundColor by animateColorAsState(
        targetValue = if (task.isCompleted) 
            Color.Gray.copy(alpha = 0.1f) 
        else 
            MaterialTheme.colorScheme.surface,
        label = "bg"
    )
    
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Priority indicator
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(priorityColor)
            )
            
            Spacer(Modifier.width(12.dp))
            
            // Checkbox
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() }
            )
            
            Spacer(Modifier.width(8.dp))
            
            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (task.isCompleted) 
                        TextDecoration.LineThrough else null,
                    color = if (task.isCompleted) Color.Gray else Color.Unspecified,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Category chip
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = { },
                        label = { Text(task.category.label, style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = {
                            Icon(Icons.Default.Label, null, Modifier.size(16.dp))
                        },
                        modifier = Modifier.height(24.dp)
                    )
                    
                    AssistChip(
                        onClick = { },
                        label = { Text(task.priority.label, style = MaterialTheme.typography.labelSmall) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = priorityColor.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
            
            // Delete button
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }
}

// ===== 4. TASKS SCREEN =====

// ui/screens/tasks/TasksScreen.kt
package com.example.todoapp.ui.screens.tasks

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onNavigateToAddTask: () -> Unit,
    onNavigateToEditTask: (Int) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSearch by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                actions = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            if (showSearch) Icons.Default.Close else Icons.Default.Search,
                            "Search"
                        )
                    }
                    IconButton(onClick = { viewModel.deleteCompletedTasks() }) {
                        Icon(Icons.Default.DeleteSweep, "Clear completed")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddTask) {
                Icon(Icons.Default.Add, "Add task")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search bar
            AnimatedVisibility(visible = showSearch) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search tasks...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    singleLine = true
                )
            }
            
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskFilter.entries.forEach { filter ->
                    FilterChip(
                        selected = uiState.filter == filter,
                        onClick = { viewModel.setFilter(filter) },
                        label = { Text(filter.name) }
                    )
                }
            }
            
            Spacer(Modifier.height(8.dp))
            
            // Tasks list
            when {
                uiState.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                
                uiState.tasks.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.CheckCircle,
                                null,
                                modifier = Modifier.size(80.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "No tasks yet!",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                "Tap + to add your first task",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.tasks,
                            key = { it.id }
                        ) { task ->
                            TaskItem(
                                task = task,
                                onToggle = { viewModel.toggleTask(task.id, !task.isCompleted) },
                                onClick = { onNavigateToEditTask(task.id) },
                                onDelete = { viewModel.deleteTask(task) },
                                modifier = Modifier.animateItem()
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  CHECKLIST DAY 28:                                            ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  [✓] Định nghĩa Colors                                        ║
 * ║  [✓] Tạo TasksViewModel                                       ║
 * ║  [✓] Tạo TaskItem component                                   ║
 * ║  [✓] Tạo TasksScreen với LazyColumn                           ║
 * ║  [✓] Implement filter chips                                   ║
 * ║  [✓] Implement search                                         ║
 * ║  [✓] Empty state                                              ║
 * ║  [✓] Loading state                                            ║
 * ║  [✓] Delete & Toggle actions                                  ║
 * ║                                                               ║
 * ║  → Tiếp tục Day 29: Add/Edit & Navigation                     ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
