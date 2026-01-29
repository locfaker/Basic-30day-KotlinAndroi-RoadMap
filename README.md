# 📱 30-Day Kotlin & Android Roadmap

> Lộ trình học Kotlin và Android từ cơ bản đến nâng cao trong 30 ngày

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 🎯 Giới thiệu

Đây là roadmap học **Kotlin** và **Android** với **Jetpack Compose** trong 30 ngày. Mỗi ngày có các bài học chi tiết với code ví dụ, giải thích rõ ràng và bài tập thực hành.

### ✨ Đặc điểm

- 📚 **30 ngày học tập** có cấu trúc rõ ràng
- 💻 **140+ file code** ví dụ sẵn sàng chạy
- 🎨 **Jetpack Compose** - UI hiện đại
- 🏗️ **MVVM Architecture** - Kiến trúc chuẩn
- 🔧 **Thực hành ngay** - Copy code vào Android Studio và run

---

## 📅 Lộ trình 4 tuần

### 🟢 Tuần 1: Kotlin Basics (Day 01-07)

| Ngày | Nội dung | Thư mục |
|:----:|----------|---------|
| 01 | Hello Kotlin, Android Studio | `day-01-android-studio-kotlin-basics/` |
| 02 | Variables, Data Types | `day-02-data-types-operators/` |
| 03 | If/Else, When Expression | `day-03-if-else-when/` |
| 04 | Loops (For, While) | `day-04-loops-for-while/` |
| 05 | Collections (List, Set, Map) | `day-05-collections-list-set-map/` |
| 06 | Functions, Lambda | `day-06-functions/` |
| 07 | Null Safety | `day-07-null-safety/` |

### 🔵 Tuần 2: Jetpack Compose UI (Day 08-14)

| Ngày | Nội dung | Thư mục |
|:----:|----------|---------|
| 08 | Text, Column, Row, Modifier | `day-08-compose-intro/` |
| 09 | Button, TextField, Events | `day-09-button-textfield-events/` |
| 10 | Image, Icon, Card | `day-10-image-icon-card/` |
| 11 | LazyColumn, LazyRow | `day-11-lazy-layouts/` |
| 12 | State Management | `day-12-state-management/` |
| 13 | Navigation | `day-13-navigation/` |
| 14 | Scaffold, Material Design | `day-14-scaffold-material/` |

### 🟣 Tuần 3: Architecture & Data (Day 15-21)

| Ngày | Nội dung | Thư mục |
|:----:|----------|---------|
| 15 | ViewModel | `day-15-viewmodel/` |
| 16 | StateFlow, Coroutines | `day-16-stateflow-coroutines/` |
| 17 | Room Database Setup | `day-17-room-setup/` |
| 18 | Room CRUD Operations | `day-18-room-crud/` |
| 19 | Retrofit API | `day-19-retrofit/` |
| 20 | Repository Pattern | `day-20-repository/` |
| 21 | Hilt Dependency Injection | `day-21-hilt-di/` |

### 🟠 Tuần 4: Advanced & Project (Day 22-30)

| Ngày | Nội dung | Thư mục |
|:----:|----------|---------|
| 22 | MVVM Complete | `day-22-mvvm-complete/` |
| 23 | Error Handling | `day-23-error-handling/` |
| 24 | Theming (Material 3) | `day-24-theming/` |
| 25 | Animations | `day-25-animation/` |
| 26 | Testing | `day-26-testing/` |
| 27-30 | **Final Project: Todo App** | `day-27-30-final-project/` |

---

## 🚀 Cách sử dụng

### 1. Clone repository

```bash
git clone https://github.com/locfaker/Basic-30day-KotlinAndroi-RoadMap.git
```

### 2. Mở Android Studio

- File → Open → Chọn project mới của bạn

### 3. Học theo thứ tự

```
1. Mở thư mục day-XX-xxx/
2. Đọc file THUC_HANH.kt để hiểu bài học
3. Copy code từ BAI_XX_YYY.kt vào MainActivity.kt
4. Run app để xem kết quả
5. Làm bài tập ở cuối mỗi file
```

---

## 📁 Cấu trúc mỗi ngày

```
day-XX-topic/
├── THUC_HANH.kt      # Hướng dẫn học
├── LY_THUYET.md      # Lý thuyết chi tiết
├── BAI_01_XXX.kt     # Bài học 1
├── BAI_02_XXX.kt     # Bài học 2
├── BAI_03_XXX.kt     # Bài học 3
└── BAI_TAP.md        # Bài tập thực hành
```

---

## 📱 Final Project: Todo App

Trong 4 ngày cuối (Day 27-30), bạn sẽ xây dựng một ứng dụng Todo hoàn chỉnh với:

- ✅ CRUD Operations
- ✅ Room Database (Offline-first)
- ✅ MVVM Architecture
- ✅ Hilt Dependency Injection
- ✅ Navigation Compose
- ✅ Material 3 Theming
- ✅ Dark/Light Mode
- ✅ Animations
- ✅ Search & Filter

---

## 🛠️ Tech Stack

| Công nghệ | Phiên bản |
|-----------|-----------|
| Kotlin | 1.9.x |
| Jetpack Compose | BOM 2024.02.00 |
| Material 3 | Latest |
| Room | 2.6.1 |
| Retrofit | 2.9.0 |
| Hilt | 2.48 |
| Navigation Compose | 2.7.6 |
| Coroutines | 1.7.3 |

---

## 📦 Dependencies

```kotlin
// build.gradle.kts (app)
dependencies {
    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
}
```

---

## 🎓 Kiến thức sẽ học được

### Kotlin Fundamentals
- Variables, Data Types, Operators
- Control Flow (if/else, when)
- Loops (for, while)
- Functions, Lambda, Higher-order functions
- Null Safety
- Collections (List, Set, Map)

### Jetpack Compose
- Composable functions
- Layout: Column, Row, Box
- Material Components
- State management
- Navigation
- Theming
- Animations

### Architecture
- MVVM Pattern
- ViewModel
- StateFlow/Flow
- Repository Pattern
- Dependency Injection (Hilt)

### Data
- Room Database
- Retrofit (REST API)
- Coroutines
- Offline-first approach

---

## 🤝 Đóng góp

Contributions are welcome! Nếu bạn muốn đóng góp:

1. Fork repository
2. Tạo branch mới (`git checkout -b feature/improvement`)
3. Commit changes (`git commit -m 'Add some improvement'`)
4. Push to branch (`git push origin feature/improvement`)
5. Tạo Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🌟 Ủng hộ

Nếu bạn thấy roadmap này hữu ích, hãy:

- ⭐ Star repository này
- 🔀 Fork và chia sẻ cho bạn bè
- 📢 Share trên social media

---

## 📧 Liên hệ

- GitHub: [@locfaker](https://github.com/locfaker)

---

**Happy Coding! 🚀**
