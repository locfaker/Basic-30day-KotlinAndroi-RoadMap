/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 14 - BÀI 4: DIALOG VÀ SNACKBAR                           ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showDialog by remember { mutableStateOf(false) }
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Dialog & Snackbar") }
                    )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Nút hiển thị Dialog
                    Button(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Warning, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hiển thị Dialog")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Nút hiển thị Snackbar
                    OutlinedButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Đây là Snackbar!",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                        Icon(Icons.Default.Notifications, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hiển thị Snackbar")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Snackbar với action
                    Button(onClick = {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Item đã bị xóa",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Long
                            )
                            
                            if (result == SnackbarResult.ActionPerformed) {
                                snackbarHostState.showSnackbar("Đã hoàn tác!")
                            }
                        }
                    }) {
                        Text("Snackbar với Undo")
                    }
                }
                
                // Alert Dialog
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        icon = { Icon(Icons.Default.Warning, null) },
                        title = { Text("Xác nhận") },
                        text = { Text("Bạn có chắc chắn muốn thực hiện hành động này?") },
                        confirmButton = {
                            Button(onClick = {
                                showDialog = false
                                scope.launch {
                                    snackbarHostState.showSnackbar("Đã xác nhận!")
                                }
                            }) {
                                Text("Đồng ý")
                            }
                        },
                        dismissButton = {
                            OutlinedButton(onClick = { showDialog = false }) {
                                Text("Hủy")
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH DIALOG & SNACKBAR:                                ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  ALERTDIALOG:                                                 ║
 * ║  AlertDialog(                                                 ║
 * ║      onDismissRequest = { },  → Khi nhấn ngoài dialog         ║
 * ║      icon = { },              → Icon (optional)               ║
 * ║      title = { },             → Tiêu đề                       ║
 * ║      text = { },              → Nội dung                      ║
 * ║      confirmButton = { },     → Nút xác nhận                  ║
 * ║      dismissButton = { }      → Nút hủy                       ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  SNACKBAR:                                                    ║
 * ║  1. Tạo SnackbarHostState                                     ║
 * ║  2. Thêm snackbarHost vào Scaffold                            ║
 * ║  3. Gọi snackbarHostState.showSnackbar() trong coroutine      ║
 * ║                                                               ║
 * ║  snackbarHostState.showSnackbar(                              ║
 * ║      message = "...",         → Nội dung                      ║
 * ║      actionLabel = "...",     → Text nút action (optional)    ║
 * ║      duration = ...           → Short/Long/Indefinite         ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  Trả về SnackbarResult:                                       ║
 * ║  → Dismissed = tự tắt hoặc nhấn ngoài                         ║
 * ║  → ActionPerformed = nhấn vào action label                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo dialog có TextField để nhập tên
 * 2. Tạo dialog xác nhận xóa với message động ("Xóa {tên}?")
 * 3. Tạo Snackbar hiển thị kết quả thành công/thất bại
 */
