/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 25 - BÀI 1: ANIMATION CƠ BẢN                             ║
 * ║                                                               ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationBasicDemo()
        }
    }
}

@Composable
fun AnimationBasicDemo() {
    var visible by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Animation Cơ Bản", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // AnimatedVisibility - Hiển thị/ẩn với animation
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFF6200EE), RoundedCornerShape(16.dp))
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Ẩn box" else "Hiện box")
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // AnimatedContent - Animate content change
        var count by remember { mutableStateOf(0) }
        
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { -it } + fadeIn() togetherWith 
                    slideOutVertically { it } + fadeOut()
                } else {
                    slideInVertically { it } + fadeIn() togetherWith 
                    slideOutVertically { -it } + fadeOut()
                }
            },
            label = "count"
        ) { targetCount ->
            Text(
                text = "$targetCount",
                style = MaterialTheme.typography.displayLarge
            )
        }
        
        Row {
            Button(onClick = { count-- }) { Text("-") }
            Spacer(Modifier.width(16.dp))
            Button(onClick = { count++ }) { Text("+") }
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH ANIMATION CƠ BẢN:                                 ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  AnimatedVisibility:                                          ║
 * ║  → Animate khi hiển thị/ẩn content                            ║
 * ║  → enter = animation khi xuất hiện                            ║
 * ║  → exit = animation khi biến mất                              ║
 * ║                                                               ║
 * ║  ENTER ANIMATIONS:                                            ║
 * ║  fadeIn()         → Fade từ trong suốt                        ║
 * ║  slideInVertically → Slide từ trên/dưới                       ║
 * ║  slideInHorizontally → Slide từ trái/phải                     ║
 * ║  expandIn()       → Mở rộng từ nhỏ                            ║
 * ║  scaleIn()        → Scale từ nhỏ                              ║
 * ║                                                               ║
 * ║  EXIT ANIMATIONS:                                             ║
 * ║  fadeOut(), slideOutVertically, shrinkOut, scaleOut...        ║
 * ║                                                               ║
 * ║  KẾT HỢP:                                                     ║
 * ║  fadeIn() + slideInVertically()                               ║
 * ║  → Kết hợp 2 animations                                       ║
 * ║                                                               ║
 * ║  AnimatedContent:                                             ║
 * ║  → Animate khi content thay đổi                               ║
 * ║  → transitionSpec định nghĩa animation                        ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
