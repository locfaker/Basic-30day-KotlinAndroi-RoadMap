/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 23 - BÀI 3: HIỂN THỊ LỖI TRONG UI                        ║
 * ║                                                               ║
 * ║  Copy code này vào project và Run                             ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Error Screen Component
@Composable
fun ErrorScreen(
    message: String,
    icon: ImageVector = Icons.Default.Error,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Đã xảy ra lỗi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = message,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(onClick = onRetry) {
                Icon(Icons.Default.Refresh, null)
                Spacer(Modifier.width(8.dp))
                Text("Thử lại")
            }
        }
    }
}

// Network Error Screen
@Composable
fun NetworkErrorScreen(onRetry: () -> Unit) {
    ErrorScreen(
        message = "Không có kết nối mạng.\nVui lòng kiểm tra và thử lại.",
        icon = Icons.Default.WifiOff,
        onRetry = onRetry
    )
}

// Empty State Screen
@Composable
fun EmptyScreen(message: String = "Không có dữ liệu") {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Inbox,
            null,
            modifier = Modifier.size(80.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(message, color = Color.Gray)
    }
}

// Snackbar cho lỗi nhẹ
@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String,
    onDismiss: () -> Unit
) {
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "Đóng",
            duration = SnackbarDuration.Long
        )
        onDismiss()
    }
}

// Main Screen với Error Handling
@Composable
fun MainScreen(viewModel: UserViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when (val state = uiState) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            
            is UiState.Success -> {
                if (state.users.isEmpty()) {
                    EmptyScreen("Chưa có user nào")
                } else {
                    UserList(state.users, Modifier.padding(padding))
                }
            }
            
            is UiState.Error -> {
                // Phân biệt loại lỗi
                when {
                    state.isNetworkError -> {
                        NetworkErrorScreen { viewModel.loadUsers() }
                    }
                    else -> {
                        ErrorScreen(
                            message = state.message,
                            onRetry = { viewModel.loadUsers() }
                        )
                    }
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  BEST PRACTICES CHO ERROR UI:                                 ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. LỖI NGHIÊM TRỌNG (blocking):                              ║
 * ║  → Full-screen error                                          ║
 * ║  → Nút "Thử lại"                                              ║
 * ║  → Ví dụ: Network error, Server error                         ║
 * ║                                                               ║
 * ║  2. LỖI NHẸ (non-blocking):                                   ║
 * ║  → Snackbar hoặc Toast                                        ║
 * ║  → Auto dismiss sau vài giây                                  ║
 * ║  → Ví dụ: "Không thể lưu", "Đã xóa"                           ║
 * ║                                                               ║
 * ║  3. LỖI VALIDATION:                                           ║
 * ║  → Inline error dưới input field                              ║
 * ║  → Highlight field lỗi                                        ║
 * ║  → Ví dụ: "Email không hợp lệ"                                ║
 * ║                                                               ║
 * ║  4. EMPTY STATE:                                              ║
 * ║  → Không phải lỗi thực sự                                     ║
 * ║  → Hướng dẫn user thêm data                                   ║
 * ║  → Ví dụ: "Chưa có note nào. Nhấn + để thêm"                  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
