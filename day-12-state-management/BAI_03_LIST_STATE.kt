/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 12 - BÀI 3: QUẢN LÝ LIST STATE                           ║
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // State cho list - dùng mutableStateListOf hoặc List
            var items by remember { 
                mutableStateOf(
                    listOf("Mục 1", "Mục 2", "Mục 3")
                )
            }
            var newItemText by remember { mutableStateOf("") }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Danh sách (${items.size} mục)",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Input thêm mục mới
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newItemText,
                        onValueChange = { newItemText = it },
                        label = { Text("Thêm mục mới") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Button(
                        onClick = {
                            if (newItemText.isNotBlank()) {
                                // THÊM: tạo list mới = list cũ + item mới
                                items = items + newItemText
                                newItemText = ""
                            }
                        }
                    ) {
                        Icon(Icons.Default.Add, "Add")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Hiển thị list
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(item)
                                
                                IconButton(
                                    onClick = {
                                        // XÓA: filter bỏ item này
                                        items = items.filter { it != item }
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        "Delete",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH QUẢN LÝ LIST STATE:                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  CÁCH 1: dùng mutableStateOf với List                         ║
 * ║  var items by remember { mutableStateOf(listOf(...)) }        ║
 * ║  → Để cập nhật: items = items + newItem (tạo list mới)        ║
 * ║  → Hoặc: items = items.filter { ... } (filter list)           ║
 * ║                                                               ║
 * ║  CÁCH 2: dùng mutableStateListOf                              ║
 * ║  val items = remember { mutableStateListOf(...) }             ║
 * ║  → Có thể: items.add(item), items.remove(item)                ║
 * ║  → Giống MutableList thông thường                             ║
 * ║                                                               ║
 * ║  THÊM ITEM:                                                   ║
 * ║  items = items + newItem                                      ║
 * ║  → Tạo list mới = list cũ + item mới                          ║
 * ║                                                               ║
 * ║  XÓA ITEM:                                                    ║
 * ║  items = items.filter { it != itemToRemove }                  ║
 * ║  → Tạo list mới không có item cần xóa                         ║
 * ║                                                               ║
 * ║  CẬP NHẬT ITEM:                                               ║
 * ║  items = items.map {                                          ║
 * ║      if (it.id == targetId) it.copy(name = "new") else it     ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm nút "Xóa tất cả" 
 * 2. Hiển thị "Danh sách trống" khi không có item
 * 3. Thêm số thứ tự trước mỗi item (1. Mục, 2. Mục...)
 */
