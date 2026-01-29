/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 14 - BÀI 3: BOTTOM NAVIGATION                            ║
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Định nghĩa các tab
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Search : BottomNavItem("search", "Search", Icons.Default.Search)
    object Favorites : BottomNavItem("favorites", "Favorites", Icons.Default.Favorite)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navItems = listOf(
                BottomNavItem.Home,
                BottomNavItem.Search,
                BottomNavItem.Favorites,
                BottomNavItem.Profile
            )
            var selectedRoute by remember { mutableStateOf(BottomNavItem.Home.route) }
            
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Bottom Navigation Demo") }
                    )
                },
                
                // Bottom Navigation Bar
                bottomBar = {
                    NavigationBar {
                        navItems.forEach { item ->
                            NavigationBarItem(
                                selected = selectedRoute == item.route,
                                onClick = { selectedRoute = item.route },
                                icon = { Icon(item.icon, item.label) },
                                label = { Text(item.label) }
                            )
                        }
                    }
                }
            ) { padding ->
                // Content thay đổi theo tab được chọn
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    when (selectedRoute) {
                        BottomNavItem.Home.route -> {
                            Text("🏠 Trang Home", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                        BottomNavItem.Search.route -> {
                            Text("🔍 Trang Search", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                        BottomNavItem.Favorites.route -> {
                            Text("❤️ Trang Favorites", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                        BottomNavItem.Profile.route -> {
                            Text("👤 Trang Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH BOTTOM NAVIGATION:                                ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  SEALED CLASS:                                                ║
 * ║  sealed class BottomNavItem(...)                              ║
 * ║  → Định nghĩa tập hợp cố định các navigation item             ║
 * ║  → object Home : BottomNavItem(...) = 1 item cụ thể           ║
 * ║                                                               ║
 * ║  NavigationBar { }:                                           ║
 * ║  → Container cho các tab navigation                           ║
 * ║                                                               ║
 * ║  NavigationBarItem(                                           ║
 * ║      selected = ...,         → true nếu đang được chọn        ║
 * ║      onClick = { },          → Xử lý khi nhấn                 ║
 * ║      icon = { },             → Icon                           ║
 * ║      label = { }             → Text label                     ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  when (selectedRoute) { }:                                    ║
 * ║  → Hiển thị nội dung khác nhau tùy tab được chọn              ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm tab "Settings" với icon Settings
 * 2. Tạo composable riêng cho mỗi màn hình (HomeScreen, SearchScreen...)
 * 3. Kết hợp với Navigation Component để navigate thực sự
 */
