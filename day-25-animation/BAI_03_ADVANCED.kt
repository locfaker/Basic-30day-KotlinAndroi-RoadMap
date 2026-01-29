/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 25 - BÀI 3: ANIMATION NÂNG CAO                           ║
 * ║                                                               ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Animation Nâng Cao", style = MaterialTheme.typography.headlineMedium)
                
                Spacer(Modifier.height(32.dp))
                
                // 1. Infinite Animation
                InfiniteRotation()
                
                Spacer(Modifier.height(32.dp))
                
                // 2. Pulsating Heart
                PulsatingHeart()
                
                Spacer(Modifier.height(32.dp))
                
                // 3. Loading Dots
                LoadingDots()
            }
        }
    }
}

// 1. Infinite Rotation
@Composable
fun InfiniteRotation() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Box(
        modifier = Modifier
            .size(60.dp)
            .rotate(rotation)
            .background(Color(0xFF9C27B0), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .offset(y = (-15).dp)
                .background(Color.White, CircleShape)
        )
    }
}

// 2. Pulsating Heart
@Composable
fun PulsatingHeart() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        modifier = Modifier
            .size(80.dp)
            .scale(scale),
        tint = Color.Red
    )
}

// 3. Loading Dots
@Composable
fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(3) { index ->
            val offset by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(300, delayMillis = index * 100),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot$index"
            )
            
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(y = offset.dp)
                    .background(Color(0xFF2196F3), CircleShape)
            )
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH ANIMATION NÂNG CAO:                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  rememberInfiniteTransition:                                  ║
 * ║  → Animation chạy mãi mãi                                     ║
 * ║  → Dùng cho loading, indicator, effects                       ║
 * ║                                                               ║
 * ║  infiniteRepeatable():                                        ║
 * ║  → Lặp vô hạn                                                 ║
 * ║  → repeatMode: Restart hoặc Reverse                           ║
 * ║                                                               ║
 * ║  RepeatMode.Restart:                                          ║
 * ║  → Bắt đầu lại từ đầu                                         ║
 * ║  → 0 → 360 → 0 → 360 ...                                      ║
 * ║                                                               ║
 * ║  RepeatMode.Reverse:                                          ║
 * ║  → Đảo ngược hướng                                            ║
 * ║  → 0 → 360 → 0 → 360 ...                                      ║
 * ║                                                               ║
 * ║  delayMillis:                                                 ║
 * ║  → Delay trước khi bắt đầu                                    ║
 * ║  → Tạo hiệu ứng staggered (như 3 dots)                        ║
 * ║                                                               ║
 * ║  EASING:                                                      ║
 * ║  LinearEasing        → Đều                                    ║
 * ║  FastOutSlowInEasing → Nhanh đầu, chậm cuối                   ║
 * ║  FastOutLinearInEasing                                        ║
 * ║  LinearOutSlowInEasing                                        ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo loading spinner với gradient
 * 2. Tạo button với ripple effect tự làm
 * 3. Tạo skeleton loading animation
 */
