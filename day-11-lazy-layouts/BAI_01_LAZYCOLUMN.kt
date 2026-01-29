/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 11 - BÀI 1: LAZYCOLUMN CƠ BẢN                            ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // LazyColumn = Column có thể scroll
            // Chỉ render item đang hiển thị (tiết kiệm bộ nhớ)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // item { } = 1 phần tử đơn lẻ
                item {
                    Text(
                        text = "Danh sách 20 mục",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // items(count) { index -> } = nhiều phần tử giống nhau
                items(20) { index ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = "Mục số ${index + 1}",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH:                                                  ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  TẠI SAO DÙNG LazyColumn THAY VÌ Column?                      ║
 * ║  ─────────────────────────────────────────                    ║
 * ║  Column: Render TẤT CẢ item cùng lúc                          ║
 * ║  → Tốn bộ nhớ nếu có 1000 item                                ║
 * ║                                                               ║
 * ║  LazyColumn: Chỉ render item ĐANG HIỂN THỊ                    ║
 * ║  → Tiết kiệm bộ nhớ, cuộn mượt                                ║
 * ║                                                               ║
 * ║  CÚ PHÁP:                                                     ║
 * ║  ─────────────────────────────────────────                    ║
 * ║  LazyColumn {                                                 ║
 * ║      item { }           → 1 phần tử đơn lẻ (header, footer)   ║
 * ║      items(count) { }   → Nhiều phần tử giống nhau            ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  contentPadding = PaddingValues(16.dp)                        ║
 * ║  → Padding xung quanh toàn bộ list                            ║
 * ║                                                               ║
 * ║  verticalArrangement = Arrangement.spacedBy(8.dp)             ║
 * ║  → Khoảng cách giữa các item                                  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thay đổi items(20) thành items(100) và thử scroll
 * 2. Thay đổi spacedBy thành 16.dp
 * 3. Thêm item footer ở cuối list: "Hết danh sách"
 */
