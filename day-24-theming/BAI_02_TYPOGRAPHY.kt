/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 24 - BÀI 2: TYPOGRAPHY                                   ║
 * ║                                                               ║
 * ║  File: ui/theme/Type.kt                                       ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Custom Font Family (nếu có)
// Download font từ Google Fonts, đặt vào res/font/
// val Poppins = FontFamily(
//     Font(R.font.poppins_regular, FontWeight.Normal),
//     Font(R.font.poppins_medium, FontWeight.Medium),
//     Font(R.font.poppins_bold, FontWeight.Bold)
// )

// Typography
val AppTypography = Typography(
    // Display - Rất lớn, dùng cho số, hero text
    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    
    // Headline - Tiêu đề chính
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    
    // Title - Tiêu đề nhỏ hơn
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    
    // Body - Nội dung chính
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    
    // Label - Buttons, chips, labels
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  CÁCH DÙNG TYPOGRAPHY:                                        ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  Text(                                                        ║
 * ║      text = "Tiêu đề",                                        ║
 * ║      style = MaterialTheme.typography.headlineMedium          ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  Text(                                                        ║
 * ║      text = "Nội dung",                                       ║
 * ║      style = MaterialTheme.typography.bodyLarge               ║
 * ║  )                                                            ║
 * ║                                                               ║
 * ║  QUYƯỚNG SỬ DỤNG:                                             ║
 * ║  displayXxx  → Hero text, số lớn                              ║
 * ║  headlineXxx → Tiêu đề trang, section                         ║
 * ║  titleXxx    → Tiêu đề card, dialog                           ║
 * ║  bodyXxx     → Nội dung chính                                 ║
 * ║  labelXxx    → Button, chip, tab                              ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
