/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 13 - BÀI 1: NAVIGATION CƠ BẢN                            ║
 * ║                                                               ║
 * ║  ⚠️ THÊM DEPENDENCY TRƯỚC:                                    ║
 * ║  implementation("androidx.navigation:navigation-compose:2.7.6")
 * ║                                                               ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Tạo NavController
            val navController = rememberNavController()
            
            // 2. Định nghĩa NavHost với các route
            NavHost(
                navController = navController,
                startDestination = "home"  // Màn hình đầu tiên
            ) {
                // Route "home" → hiển thị HomeScreen
                composable("home") {
                    HomeScreen(
                        onGoToDetail = {
                            navController.navigate("detail")
                        }
                    )
                }
                
                // Route "detail" → hiển thị DetailScreen
                composable("detail") {
                    DetailScreen(
                        onGoBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onGoToDetail: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🏠 Màn hình Home",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onGoToDetail) {
            Text("Đi đến Detail →")
        }
    }
}

@Composable
fun DetailScreen(onGoBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📄 Màn hình Detail",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedButton(onClick = onGoBack) {
            Text("← Quay lại Home")
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH NAVIGATION CƠ BẢN:                                ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. rememberNavController()                                   ║
 * ║  → Tạo đối tượng điều khiển navigation                        ║
 * ║                                                               ║
 * ║  2. NavHost(navController, startDestination) { ... }          ║
 * ║  → Container chứa các màn hình                                ║
 * ║  → startDestination = route đầu tiên hiển thị                 ║
 * ║                                                               ║
 * ║  3. composable("route") { Screen() }                          ║
 * ║  → Định nghĩa 1 route và màn hình tương ứng                   ║
 * ║  → "home", "detail" là tên route (String)                     ║
 * ║                                                               ║
 * ║  4. navController.navigate("route")                           ║
 * ║  → Chuyển đến màn hình khác                                   ║
 * ║                                                               ║
 * ║  5. navController.popBackStack()                              ║
 * ║  → Quay lại màn hình trước đó                                 ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm màn hình thứ 3 "settings" với route "settings"
 * 2. Thêm nút trong Home để đi đến Settings
 * 3. Thêm nút trong Detail để đi đến Settings
 */
