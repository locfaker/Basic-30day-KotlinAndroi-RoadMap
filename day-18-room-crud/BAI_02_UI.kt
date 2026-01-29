/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 18 - BÀI 2: UI HIỂN THỊ DATA TỪ ROOM                     ║
 * ║                                                               ║
 * ║  Copy các file từ Day 17 và file này vào project              ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.app.Application
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

// Import các class từ Day 17 và BAI_01_VIEWMODEL
// import com.example.myapplication.data.*
// import com.example.myapplication.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Tạo ViewModel với Application context
            val viewModel: NoteViewModel = viewModel(
                factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )
            val uiState by viewModel.uiState.collectAsState()
            
            NotesScreen(
                uiState = uiState,
                onAddNote = { title, content -> viewModel.addNote(title, content) },
                onToggleNote = { note -> viewModel.toggleCompleted(note) },
                onDeleteNote = { note -> viewModel.deleteNote(note) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    uiState: NotesUiState,
    onAddNote: (String, String) -> Unit,
    onToggleNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes (${uiState.notes.size})") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.notes.isEmpty() -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Chưa có note nào", color = Color.Gray)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.notes, key = { it.id }) { note ->
                        NoteItem(note, onToggleNote, onDeleteNote)
                    }
                }
            }
        }
    }
    
    // Add Dialog
    if (showAddDialog) {
        AddNoteDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, content ->
                onAddNote(title, content)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun NoteItem(note: Note, onToggle: (Note) -> Unit, onDelete: (Note) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = note.isCompleted,
                onCheckedChange = { onToggle(note) }
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (note.isCompleted) TextDecoration.LineThrough else null,
                    color = if (note.isCompleted) Color.Gray else Color.Black
                )
                Text(
                    text = note.content,
                    color = Color.Gray
                )
            }
            
            IconButton(onClick = { onDelete(note) }) {
                Icon(Icons.Default.Delete, "Delete", tint = Color.Red)
            }
        }
    }
}

@Composable
fun AddNoteDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Thêm Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Tiêu đề") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Nội dung") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, content) },
                enabled = title.isNotBlank()
            ) {
                Text("Thêm")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH UI VỚI ROOM:                                      ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  collectAsState():                                            ║
 * ║  val uiState by viewModel.uiState.collectAsState()            ║
 * ║  → Chuyển StateFlow thành Compose State                       ║
 * ║  → UI tự động recompose khi state thay đổi                    ║
 * ║                                                               ║
 * ║  LUỒNG DỮ LIỆU:                                               ║
 * ║  1. User nhấn Add → gọi onAddNote()                           ║
 * ║  2. ViewModel → noteDao.insertNote()                          ║
 * ║  3. Database thay đổi → Flow emit List<Note> mới              ║
 * ║  4. ViewModel collect → cập nhật uiState                      ║
 * ║  5. UI tự động hiển thị note mới                              ║
 * ║                                                               ║
 * ║  REACTIVE:                                                    ║
 * ║  → Không cần manually refresh                                 ║
 * ║  → UI luôn sync với database                                  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
