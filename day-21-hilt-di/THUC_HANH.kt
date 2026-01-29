/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 21: HILT DEPENDENCY INJECTION - HƯỚNG DẪN HỌC            ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * ⚠️ SETUP PHỨC TẠP - Làm theo cẩn thận!
 * 
 * 1. build.gradle.kts (project):
 * plugins {
 *     id("com.google.dagger.hilt.android") version "2.48" apply false
 * }
 * 
 * 2. build.gradle.kts (app):
 * plugins {
 *     id("com.google.dagger.hilt.android")
 *     id("com.google.devtools.ksp")
 * }
 * 
 * dependencies {
 *     implementation("com.google.dagger:hilt-android:2.48")
 *     ksp("com.google.dagger:hilt-compiler:2.48")
 *     implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
 * }
 * 
 * Có 3 bài học:
 * 
 * 📁 day-21-hilt-di/
 *    ├── BAI_01_WHAT.kt       ← DI là gì, tại sao cần
 *    ├── BAI_02_SETUP.kt      ← Setup Hilt
 *    └── BAI_03_INJECT.kt     ← Inject dependencies
 * 
 * ▶️ BẮT ĐẦU: Mở file BAI_01_WHAT.kt
 */
