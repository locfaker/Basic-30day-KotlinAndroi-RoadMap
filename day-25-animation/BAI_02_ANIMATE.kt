/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 25 - BÀI 2: animateXxxAsState                            ║
 * ║                                                               ║
 * ║  Copy code này vào MainActivity.kt và Run                     ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateAsStateDemo()
        }
    }
}

@Composable
fun AnimateAsStateDemo() {
    var isExpanded by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }
    
    // Animate size
    val boxSize: Dp by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "size"
    )
    
    // Animate color
    val boxColor: Color by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF4CAF50) else Color(0xFF2196F3),
        animationSpec = tween(durationMillis = 500),
        label = "color"
    )
    
    // Animate corner radius
    val cornerRadius: Dp by animateDpAsState(
        targetValue = if (isExpanded) 32.dp else 8.dp,
        label = "corner"
    )
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("animateXxxAsState", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Animated Box
        Box(
            modifier = Modifier
                .size(boxSize)
                .background(boxColor, RoundedCornerShape(cornerRadius))
                .clickable { isSelected = !isSelected },
            contentAlignment = Alignment.Center
        ) {
            Text("Click me!", color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(onClick = { isExpanded = !isExpanded }) {
            Text(if (isExpanded) "Thu nhỏ" else "Phóng to")
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Scale Animation
        ScaleAnimationDemo()
    }
}

@Composable
fun ScaleAnimationDemo() {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "scale"
    )
    
    Button(
        onClick = { },
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        isPressed = event.changes.any { it.pressed }
                    }
                }
            }
    ) {
        Text("Nhấn và giữ")
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  animateXxxAsState FUNCTIONS:                                 ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  animateDpAsState     → Animate Dp (size, padding...)         ║
 * ║  animateColorAsState  → Animate Color                         ║
 * ║  animateFloatAsState  → Animate Float (scale, alpha...)       ║
 * ║  animateIntAsState    → Animate Int                           ║
 * ║  animateOffsetAsState → Animate Offset (position)             ║
 * ║  animateSizeAsState   → Animate Size                          ║
 * ║                                                               ║
 * ║  ANIMATION SPECS:                                             ║
 * ║                                                               ║
 * ║  spring():                                                    ║
 * ║  → Animation vật lý, tự nhiên                                 ║
 * ║  → dampingRatio: độ nảy                                       ║
 * ║  → stiffness: độ cứng                                         ║
 * ║                                                               ║
 * ║  tween():                                                     ║
 * ║  → Duration-based                                             ║
 * ║  → durationMillis: thời gian                                  ║
 * ║  → easing: đường cong (FastOutSlowInEasing...)                ║
 * ║                                                               ║
 * ║  keyframes():                                                 ║
 * ║  → Điều khiển từng keyframe                                   ║
 * ║                                                               ║
 * ║  repeatable():                                                ║
 * ║  → Lặp lại animation                                          ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
