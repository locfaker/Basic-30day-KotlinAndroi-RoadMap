/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 24 - BÀI 3: THEME HOÀN CHỈNH                             ║
 * ║                                                               ║
 * ║  File: ui/theme/Theme.kt                                      ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple80,
    onPrimaryContainer = Color(0xFF21005D),
    
    secondary = PurpleGrey40,
    onSecondary = Color.White,
    secondaryContainer = PurpleGrey80,
    onSecondaryContainer = Color(0xFF1D192B),
    
    tertiary = Pink40,
    onTertiary = Color.White,
    tertiaryContainer = Pink80,
    onTertiaryContainer = Color(0xFF31111D),
    
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0)
)

// Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Purple80,
    
    secondary = PurpleGrey80,
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = PurpleGrey80,
    
    tertiary = Pink80,
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Pink80,
    
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F)
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,  // Android 12+ Material You
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic Color (Material You) - Android 12+
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    // Status bar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

// CÁCH DÙNG TRONG APP
@Composable
fun App() {
    MyAppTheme {
        // Tất cả composables bên trong sẽ dùng theme này
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // UI của bạn
        }
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  SỬ DỤNG THEME TRONG COMPOSABLES:                             ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  // Màu                                                       ║
 * ║  MaterialTheme.colorScheme.primary                            ║
 * ║  MaterialTheme.colorScheme.onPrimary                          ║
 * ║  MaterialTheme.colorScheme.background                         ║
 * ║                                                               ║
 * ║  // Typography                                                ║
 * ║  MaterialTheme.typography.headlineMedium                      ║
 * ║  MaterialTheme.typography.bodyLarge                           ║
 * ║                                                               ║
 * ║  // Shapes                                                    ║
 * ║  MaterialTheme.shapes.small                                   ║
 * ║  MaterialTheme.shapes.medium                                  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo color scheme riêng cho app của bạn
 * 2. Thêm custom font từ Google Fonts
 * 3. Tạo theme toggle (Light/Dark)
 */
