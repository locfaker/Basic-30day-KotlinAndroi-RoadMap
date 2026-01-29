/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 13 - BÀI 2: TRUYỀN THAM SỐ                               ║
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Data
data class Product(val id: Int, val name: String, val price: Int)

val products = listOf(
    Product(1, "iPhone 15", 25000000),
    Product(2, "Samsung Galaxy S24", 22000000),
    Product(3, "Xiaomi 14", 15000000)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            NavHost(navController, startDestination = "list") {
                // Màn hình danh sách
                composable("list") {
                    ProductListScreen(
                        onProductClick = { productId ->
                            // Truyền productId qua URL
                            navController.navigate("detail/$productId")
                        }
                    )
                }
                
                // Màn hình chi tiết với tham số
                composable(
                    route = "detail/{productId}",
                    arguments = listOf(
                        navArgument("productId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    // Lấy tham số từ backStackEntry
                    val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                    ProductDetailScreen(
                        productId = productId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductListScreen(onProductClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Danh sách sản phẩm",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(products) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onProductClick(product.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(product.name, fontWeight = FontWeight.Bold)
                        Text("${product.price / 1000000}tr")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(productId: Int, onBack: () -> Unit) {
    val product = products.find { it.id == productId }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (product != null) {
            Text("Chi tiết sản phẩm", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("ID: ${product.id}")
            Text(product.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Giá: ${product.price} VND")
        } else {
            Text("Không tìm thấy sản phẩm")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedButton(onClick = onBack) { Text("← Quay lại") }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH TRUYỀN THAM SỐ:                                   ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  ĐỊNH NGHĨA ROUTE VỚI THAM SỐ:                                ║
 * ║  composable(                                                  ║
 * ║      route = "detail/{productId}",                            ║
 * ║      arguments = listOf(                                      ║
 * ║          navArgument("productId") { type = NavType.IntType }  ║
 * ║      )                                                        ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  NAVIGATE VỚI THAM SỐ:                                        ║
 * ║  navController.navigate("detail/123")                         ║
 * ║  → 123 là giá trị của productId                               ║
 * ║                                                               ║
 * ║  LẤY THAM SỐ:                                                 ║
 * ║  val productId = backStackEntry.arguments?.getInt("productId")║
 * ║                                                               ║
 * ║  CÁC KIỂU DỮ LIỆU:                                            ║
 * ║  NavType.IntType    → Int                                     ║
 * ║  NavType.StringType → String                                  ║
 * ║  NavType.BoolType   → Boolean                                 ║
 * ║  NavType.FloatType  → Float                                   ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm tham số "productName" kiểu String
 * 2. Hiển thị productName trực tiếp thay vì tìm từ list
 * 3. Thêm màn hình "edit/{productId}" để chỉnh sửa sản phẩm
 */
