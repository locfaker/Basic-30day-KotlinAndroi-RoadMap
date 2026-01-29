/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 17: ROOM DATABASE SETUP - HƯỚNG DẪN HỌC                  ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * ⚠️ DEPENDENCIES cần thêm vào build.gradle.kts (app):
 * 
 * // Room
 * val room_version = "2.6.1"
 * implementation("androidx.room:room-runtime:$room_version")
 * implementation("androidx.room:room-ktx:$room_version")
 * ksp("androidx.room:room-compiler:$room_version")
 * 
 * // KSP Plugin - thêm vào plugins { }
 * id("com.google.devtools.ksp") version "1.9.21-1.0.15"
 * 
 * Có 3 bài học, học THEO THỨ TỰ:
 * 
 * 📁 day-17-room-setup/
 *    ├── BAI_01_ENTITY.kt     ← Định nghĩa bảng dữ liệu
 *    ├── BAI_02_DAO.kt        ← Data Access Object
 *    └── BAI_03_DATABASE.kt   ← Setup Database
 * 
 * ▶️ BẮT ĐẦU: Thêm dependencies, Sync, rồi mở BAI_01_ENTITY.kt
 */
