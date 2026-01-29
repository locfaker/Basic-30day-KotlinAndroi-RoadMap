/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 26 - BÀI 3: UI TEST                                      ║
 * ║                                                               ║
 * ║  File: app/src/androidTest/java/.../ComposeTest.kt            ║
 * ║  (Thư mục androidTest - chạy trên emulator/device)            ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class CounterScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun counterScreen_displayInitialValue() {
        composeTestRule.setContent {
            CounterScreen()
        }
        
        // Tìm text "0"
        composeTestRule
            .onNodeWithText("0")
            .assertIsDisplayed()
    }
    
    @Test
    fun counterScreen_incrementButtonWorks() {
        composeTestRule.setContent {
            CounterScreen()
        }
        
        // Click button "+"
        composeTestRule
            .onNodeWithText("+")
            .performClick()
        
        // Kiểm tra text "1" hiển thị
        composeTestRule
            .onNodeWithText("1")
            .assertIsDisplayed()
    }
    
    @Test
    fun counterScreen_decrementButtonWorks() {
        composeTestRule.setContent {
            CounterScreen()
        }
        
        // Increment trước
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        
        // Rồi decrement
        composeTestRule.onNodeWithText("-").performClick()
        
        composeTestRule
            .onNodeWithText("1")
            .assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_showsErrorOnEmptyEmail() {
        composeTestRule.setContent {
            LoginScreen()
        }
        
        // Click login mà không nhập gì
        composeTestRule
            .onNodeWithText("Login")
            .performClick()
        
        // Kiểm tra có hiện lỗi
        composeTestRule
            .onNodeWithText("Email is required")
            .assertIsDisplayed()
    }
    
    @Test
    fun loginScreen_enterCredentials() {
        composeTestRule.setContent {
            LoginScreen()
        }
        
        // Nhập email
        composeTestRule
            .onNodeWithTag("email_input")
            .performTextInput("test@example.com")
        
        // Nhập password
        composeTestRule
            .onNodeWithTag("password_input")
            .performTextInput("password123")
        
        // Click login
        composeTestRule
            .onNodeWithText("Login")
            .performClick()
    }
}

// Thêm testTag trong Composable:
// TextField(
//     modifier = Modifier.testTag("email_input"),
//     ...
// )

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  COMPOSE UI TEST API:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  FINDERS:                                                     ║
 * ║  onNodeWithText("text")      → Tìm theo text                  ║
 * ║  onNodeWithTag("tag")        → Tìm theo testTag               ║
 * ║  onNodeWithContentDescription("desc")                         ║
 * ║  onAllNodesWithText("text")  → Tìm tất cả                     ║
 * ║                                                               ║
 * ║  ASSERTIONS:                                                  ║
 * ║  assertIsDisplayed()         → Đang hiển thị                  ║
 * ║  assertIsEnabled()           → Đang enabled                   ║
 * ║  assertIsSelected()          → Đang selected                  ║
 * ║  assertTextEquals("text")    → Text bằng                      ║
 * ║  assertDoesNotExist()        → Không tồn tại                  ║
 * ║                                                               ║
 * ║  ACTIONS:                                                     ║
 * ║  performClick()              → Click                          ║
 * ║  performTextInput("text")    → Nhập text                      ║
 * ║  performScrollTo()           → Scroll đến                     ║
 * ║  performGesture { swipeUp() }                                 ║
 * ║                                                               ║
 * ║  CHẠY TEST:                                                   ║
 * ║  → ./gradlew connectedAndroidTest                             ║
 * ║  → Right-click → Run (cần emulator/device)                    ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Viết test cho TodoScreen (add, toggle, delete)
 * 2. Viết test cho Navigation
 * 3. Viết test cho form validation
 */
