/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 14 - BÀI 1: SCAFFOLD CƠ BẢN                              ║
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var count by remember { mutableStateOf(0) }
            
            // Scaffold = bố cục chuẩn Material Design
            Scaffold(
                // TopAppBar ở trên cùng
                topBar = {
                    TopAppBar(
                        title = { Text("Scaffold Demo") }
                    )
                },
                
                // Floating Action Button
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { count++ }
                    ) {
                        Icon(Icons.Default.Add, "Add")
                    }
                }
            ) { paddingValues ->
                // Nội dung chính - PHẢI dùng paddingValues
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)  // Quan trọng!
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Đã nhấn FAB: $count lần")
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH SCAFFOLD:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  SCAFFOLD là gì?                                              ║
 * ║  → Bố cục chuẩn của Material Design                           ║
 * ║  → Có sẵn các vị trí cho TopBar, BottomBar, FAB, Drawer       ║
 * ║                                                               ║
 * ║  Cấu trúc:                                                    ║
 * ║  ┌─────────────────────────┐                                  ║
 * ║  │      TopAppBar          │                                  ║
 * ║  ├─────────────────────────┤                                  ║
 * ║  │                         │                                  ║
 * ║  │     Content (body)      │                                  ║
 * ║  │                    [FAB]│                                  ║
 * ║  ├─────────────────────────┤                                  ║
 * ║  │     BottomBar           │                                  ║
 * ║  └─────────────────────────┘                                  ║
 * ║                                                               ║
 * ║  QUAN TRỌNG: paddingValues                                    ║
 * ║  → Scaffold truyền paddingValues vào content                  ║
 * ║  → PHẢI dùng: Modifier.padding(paddingValues)                 ║
 * ║  → Nếu không, content sẽ bị TopBar che phủ                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi icon FAB từ Add sang Favorite
 * 2. Thêm màu cho TopAppBar
 * 3. Thử bỏ .padding(paddingValues) xem lỗi gì
 */
