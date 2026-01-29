/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 14 - BÀI 2: TOPAPPBAR VỚI MENU                           ║
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedAction by remember { mutableStateOf("") }
            var showMenu by remember { mutableStateOf(false) }
            
            Scaffold(
                topBar = {
                    TopAppBar(
                        // Nút back/menu bên trái
                        navigationIcon = {
                            IconButton(onClick = { selectedAction = "Menu" }) {
                                Icon(Icons.Default.Menu, "Menu")
                            }
                        },
                        
                        // Tiêu đề
                        title = { Text("TopAppBar Demo") },
                        
                        // Actions (các nút) bên phải
                        actions = {
                            IconButton(onClick = { selectedAction = "Search" }) {
                                Icon(Icons.Default.Search, "Search")
                            }
                            
                            IconButton(onClick = { selectedAction = "Favorite" }) {
                                Icon(Icons.Default.Favorite, "Favorite")
                            }
                            
                            // Dropdown Menu
                            Box {
                                IconButton(onClick = { showMenu = true }) {
                                    Icon(Icons.Default.MoreVert, "More")
                                }
                                
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Settings") },
                                        onClick = {
                                            selectedAction = "Settings"
                                            showMenu = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Settings, null)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Help") },
                                        onClick = {
                                            selectedAction = "Help"
                                            showMenu = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Info, null)
                                        }
                                    )
                                }
                            }
                        },
                        
                        // Màu sắc
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    Text("Bạn đã nhấn: $selectedAction")
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH TOPAPPBAR:                                        ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  TopAppBar(                                                   ║
 * ║      navigationIcon = { },   → Nút bên trái (back/menu)       ║
 * ║      title = { },            → Tiêu đề                        ║
 * ║      actions = { },          → Các nút bên phải               ║
 * ║      colors = ...            → Màu sắc                        ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  DROPDOWN MENU:                                               ║
 * ║  DropdownMenu(                                                ║
 * ║      expanded = state,       → true/false để show/hide        ║
 * ║      onDismissRequest = { }  → Gọi khi click bên ngoài        ║
 * ║  ) {                                                          ║
 * ║      DropdownMenuItem(...)                                    ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm menu item "Logout" với icon ExitToApp
 * 2. Thay đổi navigationIcon thành ArrowBack
 * 3. Thêm badge (số) trên icon Favorite (dùng BadgedBox)
 */
