/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 11 - BÀI 3: DÙNG items() VỚI LIST                        ║
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class cho sản phẩm
data class Product(
    val id: Int,
    val name: String,
    val price: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Danh sách sản phẩm
            val products = listOf(
                Product(1, "iPhone 15", 25000000),
                Product(2, "Samsung Galaxy S24", 22000000),
                Product(3, "Xiaomi 14", 15000000),
                Product(4, "Oppo Find X6", 18000000),
                Product(5, "Google Pixel 8", 20000000)
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "Sản phẩm (${products.size})",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // items(list) { item -> } = duyệt qua từng phần tử
                items(products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "ID: ${product.id}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Text(
                text = "${product.price / 1000000}tr VND",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF43A047)
            )
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH:                                                  ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  DATA CLASS:                                                  ║
 * ║  data class Product(val id: Int, val name: String, ...)       ║
 * ║  → Định nghĩa cấu trúc dữ liệu cho 1 sản phẩm                 ║
 * ║                                                               ║
 * ║  items(list) { item -> }:                                     ║
 * ║  → Duyệt qua từng phần tử trong list                          ║
 * ║  → item là phần tử hiện tại (kiểu Product)                    ║
 * ║                                                               ║
 * ║  VÍ DỤ:                                                       ║
 * ║  items(products) { product ->                                 ║
 * ║      // product.name = tên sản phẩm                           ║
 * ║      // product.price = giá                                   ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  SO SÁNH:                                                     ║
 * ║  items(20) { index -> }    → index là số 0-19                 ║
 * ║  items(list) { item -> }   → item là phần tử trong list       ║
 * ║                                                               ║
 * ║  IMPORT CẦN THIẾT:                                            ║
 * ║  import androidx.compose.foundation.lazy.items                ║
 * ║  (Khác với items(count) không cần import)                     ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm sản phẩm mới vào list products
 * 2. Thêm field "brand" vào data class Product
 * 3. Hiển thị brand trong ProductCard
 * 4. Định dạng giá với dấu chấm: 25.000.000 VND
 */
