/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 15 - BÀI 2: STATE TRONG VIEWMODEL                        ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Data class cho UI State
data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
    val isValid: Boolean = false
)

// ===== VIEWMODEL =====
class ProfileViewModel : ViewModel() {
    // UI State gom tất cả state vào 1 object
    var uiState by mutableStateOf(ProfileUiState())
        private set
    
    // Các hàm update state
    fun updateName(name: String) {
        uiState = uiState.copy(name = name)
        validate()
    }
    
    fun updateEmail(email: String) {
        uiState = uiState.copy(email = email)
        validate()
    }
    
    fun updateAge(age: Int) {
        uiState = uiState.copy(age = age)
        validate()
    }
    
    private fun validate() {
        val isValid = uiState.name.isNotBlank() && 
                      uiState.email.contains("@") && 
                      uiState.age > 0
        uiState = uiState.copy(isValid = isValid)
    }
    
    fun reset() {
        uiState = ProfileUiState()
    }
}

// ===== MAIN ACTIVITY =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ProfileViewModel = viewModel()
            ProfileScreen(viewModel)
        }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState = viewModel.uiState
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Profile Form",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Họ tên") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = if (uiState.age > 0) uiState.age.toString() else "",
            onValueChange = { 
                val age = it.toIntOrNull() ?: 0
                viewModel.updateAge(age) 
            },
            label = { Text("Tuổi") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Hiển thị trạng thái
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("UI State:", fontWeight = FontWeight.Bold)
                Text("Name: ${uiState.name}")
                Text("Email: ${uiState.email}")
                Text("Age: ${uiState.age}")
                Text("Valid: ${uiState.isValid}")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { /* Submit */ },
                enabled = uiState.isValid,
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit")
            }
            
            OutlinedButton(
                onClick = { viewModel.reset() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reset")
            }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH UI STATE:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  UI STATE là gì?                                              ║
 * ║  → Gom tất cả state liên quan vào 1 data class                ║
 * ║  → Dễ quản lý, dễ debug, dễ test                              ║
 * ║                                                               ║
 * ║  data class ProfileUiState(                                   ║
 * ║      val name: String = "",                                   ║
 * ║      val email: String = "",                                  ║
 * ║      val age: Int = 0,                                        ║
 * ║      val isValid: Boolean = false                             ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  CẬP NHẬT STATE với copy():                                   ║
 * ║  uiState = uiState.copy(name = newName)                       ║
 * ║  → Tạo object mới, chỉ thay đổi field cần thiết               ║
 * ║  → Immutable - an toàn hơn                                    ║
 * ║                                                               ║
 * ║  DERIVED STATE (isValid):                                     ║
 * ║  → Tính toán từ các state khác                                ║
 * ║  → Tự động cập nhật khi state gốc thay đổi                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Thêm field "phone" vào ProfileUiState
 * 2. Validate phone phải có ít nhất 10 số
 * 3. Thêm field "isLoading" để hiển thị loading khi submit
 */
