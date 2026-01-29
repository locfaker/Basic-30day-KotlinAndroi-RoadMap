/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 29: ADD/EDIT SCREEN & NAVIGATION                         ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ===== 1. ADD/EDIT VIEWMODEL =====

// ui/screens/add_edit/AddEditViewModel.kt
package com.example.todoapp.ui.screens.add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddEditUiState(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,
    val category: Category = Category.PERSONAL,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val taskId: Int? = savedStateHandle.get<Int>("taskId")?.takeIf { it != -1 }
    
    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState.asStateFlow()
    
    init {
        taskId?.let { id ->
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                repository.getTaskById(id)?.let { task ->
                    _uiState.value = AddEditUiState(
                        title = task.title,
                        description = task.description,
                        priority = task.priority,
                        category = task.category,
                        isEditing = true,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title, errorMessage = null)
    }
    
    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }
    
    fun updatePriority(priority: Priority) {
        _uiState.value = _uiState.value.copy(priority = priority)
    }
    
    fun updateCategory(category: Category) {
        _uiState.value = _uiState.value.copy(category = category)
    }
    
    fun saveTask() {
        val state = _uiState.value
        
        // Validation
        if (state.title.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Title is required")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)
            
            val task = TaskEntity(
                id = taskId ?: 0,
                title = state.title.trim(),
                description = state.description.trim(),
                priority = state.priority,
                category = state.category
            )
            
            if (taskId != null) {
                repository.updateTask(task)
            } else {
                repository.addTask(task)
            }
            
            _uiState.value = state.copy(isSaved = true, isLoading = false)
        }
    }
}

// ===== 2. ADD/EDIT SCREEN =====

// ui/screens/add_edit/AddEditScreen.kt
package com.example.todoapp.ui.screens.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Navigate back when saved
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.isEditing) "Edit Task" else "New Task") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.saveTask() },
                        enabled = !uiState.isLoading
                    ) {
                        Icon(Icons.Default.Check, "Save")
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text("Title *") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMessage != null,
                supportingText = {
                    uiState.errorMessage?.let { Text(it, color = Color.Red) }
                },
                singleLine = true
            )
            
            // Description
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )
            
            // Priority
            Text("Priority", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Priority.entries.forEach { priority ->
                    val color = when (priority) {
                        Priority.HIGH -> Color(0xFFE53935)
                        Priority.MEDIUM -> Color(0xFFFF9800)
                        Priority.LOW -> Color(0xFF4CAF50)
                    }
                    
                    FilterChip(
                        selected = uiState.priority == priority,
                        onClick = { viewModel.updatePriority(priority) },
                        label = { Text(priority.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = color.copy(alpha = 0.2f),
                            selectedLabelColor = color
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Category
            Text("Category", style = MaterialTheme.typography.labelLarge)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Category.entries.forEach { category ->
                    FilterChip(
                        selected = uiState.category == category,
                        onClick = { viewModel.updateCategory(category) },
                        label = { Text(category.label) },
                        leadingIcon = {
                            val icon = when (category) {
                                Category.PERSONAL -> Icons.Default.Person
                                Category.WORK -> Icons.Default.Work
                                Category.SHOPPING -> Icons.Default.ShoppingCart
                                Category.HEALTH -> Icons.Default.Favorite
                                Category.OTHER -> Icons.Default.MoreHoriz
                            }
                            Icon(icon, null, Modifier.size(18.dp))
                        }
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            // Save Button
            Button(
                onClick = { viewModel.saveTask() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp))
                } else {
                    Icon(Icons.Default.Save, null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (uiState.isEditing) "Update Task" else "Add Task")
                }
            }
        }
    }
}

// ===== 3. NAVIGATION =====

// ui/navigation/NavGraph.kt
package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.add_edit.AddEditScreen
import com.example.todoapp.ui.screens.tasks.TasksScreen

object Destinations {
    const val TASKS = "tasks"
    const val ADD_TASK = "add_task"
    const val EDIT_TASK = "edit_task/{taskId}"
    
    fun editTask(taskId: Int) = "edit_task/$taskId"
}

@Composable
fun TodoNavGraph() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Destinations.TASKS
    ) {
        // Tasks List
        composable(Destinations.TASKS) {
            TasksScreen(
                onNavigateToAddTask = {
                    navController.navigate(Destinations.ADD_TASK)
                },
                onNavigateToEditTask = { taskId ->
                    navController.navigate(Destinations.editTask(taskId))
                }
            )
        }
        
        // Add Task
        composable(Destinations.ADD_TASK) {
            AddEditScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Edit Task
        composable(
            route = Destinations.EDIT_TASK,
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) {
            AddEditScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

// ===== 4. MAIN ACTIVITY =====

// MainActivity.kt
package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.todoapp.ui.navigation.TodoNavGraph
import com.example.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoNavGraph()
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  CHECKLIST DAY 29:                                            ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  [✓] Tạo AddEditViewModel                                     ║
 * ║  [✓] Tạo AddEditScreen                                        ║
 * ║  [✓] Form validation                                          ║
 * ║  [✓] Priority selector                                        ║
 * ║  [✓] Category selector                                        ║
 * ║  [✓] Setup Navigation graph                                   ║
 * ║  [✓] Pass taskId cho Edit                                     ║
 * ║  [✓] Navigate back sau khi save                               ║
 * ║  [✓] Update MainActivity                                      ║
 * ║                                                               ║
 * ║  → Tiếp tục Day 30: Polish & Finish                           ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
