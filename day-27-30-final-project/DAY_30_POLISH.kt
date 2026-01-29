/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 30: POLISH & FINISH                                      ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// ===== 1. THEME VỚI DARK MODE =====

// ui/theme/Theme.kt
package com.example.todoapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8DEF8),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF4F378B),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// ===== 2. SWIPE TO DELETE =====

// ui/components/SwipeableTaskItem.kt
package com.example.todoapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.local.TaskEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTaskItem(
    task: TaskEntity,
    onToggle: () -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else false
        }
    )
    
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    else -> Color.Transparent
                },
                label = "bg"
            )
            
            val scale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1f else 0.75f,
                label = "scale"
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete,
                    "Delete",
                    modifier = Modifier.scale(scale),
                    tint = Color.White
                )
            }
        },
        enableDismissFromStartToEnd = false,
        modifier = modifier
    ) {
        TaskItem(
            task = task,
            onToggle = onToggle,
            onClick = onClick,
            onDelete = onDelete
        )
    }
}

// ===== 3. PULL TO REFRESH =====

// Thêm vào TasksScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenWithPullRefresh() {
    var isRefreshing by remember { mutableStateOf(false) }
    
    val pullToRefreshState = rememberPullToRefreshState()
    
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            // Refresh logic
            isRefreshing = false
        },
        state = pullToRefreshState
    ) {
        // LazyColumn content
    }
}

// ===== 4. SNACKBAR CHO UNDO =====

@Composable
fun TasksScreenWithUndo(viewModel: TasksViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var deletedTask by remember { mutableStateOf<TaskEntity?>(null) }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        // When delete
        fun onDeleteTask(task: TaskEntity) {
            deletedTask = task
            viewModel.deleteTask(task)
            
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = "Task deleted",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Short
                )
                
                if (result == SnackbarResult.ActionPerformed) {
                    // Undo - add task back
                    deletedTask?.let { viewModel.addTask(it) }
                }
                deletedTask = null
            }
        }
    }
}

// ===== 5. ANIMATIONS =====

// Task list với animations
@Composable
fun AnimatedTaskList(tasks: List<TaskEntity>) {
    LazyColumn {
        items(
            items = tasks,
            key = { it.id }
        ) { task ->
            // Animate item appearance
            var visible by remember { mutableStateOf(false) }
            
            LaunchedEffect(Unit) {
                visible = true
            }
            
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                TaskItem(
                    task = task,
                    onToggle = { },
                    onClick = { },
                    onDelete = { }
                )
            }
        }
    }
}

// ===== 6. FAB VỚI ANIMATION =====

@Composable
fun AnimatedFab(onClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 45f else 0f,
        label = "rotation"
    )
    
    FloatingActionButton(
        onClick = {
            isExpanded = !isExpanded
            onClick()
        }
    ) {
        Icon(
            Icons.Default.Add,
            "Add",
            modifier = Modifier.rotate(rotation)
        )
    }
}

// ===== 7. STATISTICS CARD =====

@Composable
fun StatsCard(totalTasks: Int, completedTasks: Int) {
    val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Progress",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(Modifier.height(8.dp))
            
            Text(
                "$completedTasks of $totalTasks tasks completed",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  FINAL CHECKLIST:                                             ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  CORE FEATURES:                                               ║
 * ║  [✓] Create task                                              ║
 * ║  [✓] Read/List tasks                                          ║
 * ║  [✓] Update/Edit task                                         ║
 * ║  [✓] Delete task                                              ║
 * ║  [✓] Toggle complete                                          ║
 * ║  [✓] Priority levels                                          ║
 * ║  [✓] Categories                                               ║
 * ║                                                               ║
 * ║  UX FEATURES:                                                 ║
 * ║  [✓] Search                                                   ║
 * ║  [✓] Filter (All/Active/Completed)                            ║
 * ║  [✓] Swipe to delete                                          ║
 * ║  [✓] Undo with Snackbar                                       ║
 * ║  [✓] Empty state                                              ║
 * ║  [✓] Loading state                                            ║
 * ║                                                               ║
 * ║  POLISH:                                                      ║
 * ║  [✓] Dark/Light theme                                         ║
 * ║  [✓] Material You dynamic colors                              ║
 * ║  [✓] Animations                                               ║
 * ║  [✓] Progress stats                                           ║
 * ║                                                               ║
 * ║  ARCHITECTURE:                                                ║
 * ║  [✓] MVVM pattern                                             ║
 * ║  [✓] Room database                                            ║
 * ║  [✓] Hilt DI                                                  ║
 * ║  [✓] Navigation Compose                                       ║
 * ║  [✓] StateFlow                                                ║
 * ║  [✓] Coroutines                                               ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * 🎉 CHÚC MỪNG ĐÃ HOÀN THÀNH KHÓA HỌC!
 * 
 * Bạn đã học:
 * - Kotlin basics
 * - Jetpack Compose UI
 * - State management
 * - Navigation
 * - ViewModel
 * - Coroutines & Flow
 * - Room Database
 * - Retrofit (API)
 * - Repository pattern
 * - Hilt DI
 * - MVVM Architecture
 * - Theming
 * - Animations
 * - Testing
 * 
 * NEXT STEPS:
 * 1. Build your own app ideas
 * 2. Learn more advanced topics (WorkManager, DataStore, Paging)
 * 3. Publish to Google Play Store!
 */
