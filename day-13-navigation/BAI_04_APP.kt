/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 13 - BÀI 4: APP HOÀN CHỈNH VỚI NAVIGATION                ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Routes object để quản lý route
object Routes {
    const val HOME = "home"
    const val USER_LIST = "users"
    const val USER_DETAIL = "user/{userId}"
    
    fun userDetail(userId: Int) = "user/$userId"
}

// Fake data
data class User(val id: Int, val name: String, val email: String)
val users = listOf(
    User(1, "Nguyễn Văn An", "an@email.com"),
    User(2, "Trần Thị Bình", "binh@email.com"),
    User(3, "Lê Văn Cường", "cuong@email.com")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            NavHost(navController, startDestination = Routes.HOME) {
                composable(Routes.HOME) {
                    HomeScreen(
                        onUsersClick = { navController.navigate(Routes.USER_LIST) }
                    )
                }
                
                composable(Routes.USER_LIST) {
                    UserListScreen(
                        onUserClick = { userId -> 
                            navController.navigate(Routes.userDetail(userId))
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                
                composable(
                    route = Routes.USER_DETAIL,
                    arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { entry ->
                    val userId = entry.arguments?.getInt("userId") ?: 0
                    UserDetailScreen(
                        userId = userId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onUsersClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trang chủ") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🏠 Chào mừng!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(onClick = onUsersClick, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Person, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Xem danh sách Users")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(onUserClick: (Int) -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách Users") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                Card(onClick = { onUserClick(user.id) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).background(
                                MaterialTheme.colorScheme.primary, CircleShape
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(user.name.first().toString(), color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(user.name, fontWeight = FontWeight.Bold)
                            Text(user.email, color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(userId: Int, onBack: () -> Unit) {
    val user = users.find { it.id == userId }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user?.name ?: "User") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user != null) {
                Box(
                    modifier = Modifier.size(100.dp).background(
                        MaterialTheme.colorScheme.primary, CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(user.name.first().toString(), fontSize = 40.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(user.email, color = Color.Gray)
                Text("ID: ${user.id}")
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH APP HOÀN CHỈNH:                                   ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  1. ROUTES OBJECT:                                            ║
 * ║  object Routes {                                              ║
 * ║      const val HOME = "home"                                  ║
 * ║      fun userDetail(id: Int) = "user/$id"                     ║
 * ║  }                                                            ║
 * ║  → Quản lý route tập trung, tránh hardcode string             ║
 * ║                                                               ║
 * ║  2. TOPAPPBAR VỚI NAVIGATION ICON:                            ║
 * ║  navigationIcon = {                                           ║
 * ║      IconButton(onClick = onBack) {                           ║
 * ║          Icon(Icons.Default.ArrowBack, "Back")                ║
 * ║      }                                                        ║
 * ║  }                                                            ║
 * ║  → Nút back ở góc trên trái                                   ║
 * ║                                                               ║
 * ║  3. LUỒNG NAVIGATION:                                         ║
 * ║  Home → UserList → UserDetail                                 ║
 * ║  Mỗi màn hình có nút back riêng                               ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm màn hình Settings từ Home
 * 2. Thêm nút Edit trong UserDetail (navigate to EditScreen)
 * 3. Thêm BottomNavigationBar để chuyển giữa các tab
 */
