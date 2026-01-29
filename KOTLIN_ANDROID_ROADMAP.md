# 📱 KOTLIN & ANDROID ROADMAP - 30 NGÀY

## ✅ TIẾN ĐỘ: HOÀN THÀNH 100%

---

## 📖 CÁCH HỌC

1. **Mỗi ngày** mở thư mục `day-XX-xxx/`
2. **Đọc** file `THUC_HANH.kt` để hiểu bài học
3. **Copy code** từ các file `BAI_XX_YYY.kt` vào Android Studio
4. **Run** để xem kết quả
5. **Làm bài tập** ở cuối mỗi file

---

## 📅 TUẦN 1: KOTLIN BASICS (Day 01-07)

| Ngày | Thư mục | Nội dung |
|------|---------|----------|
| 01 | `day-01-hello-kotlin/` | Hello World, Print, Comments |
| 02 | `day-02-variables/` | Variables (val, var), Data types |
| 03 | `day-03-operators/` | Operators (+, -, *, /, %) |
| 04 | `day-04-control-flow/` | If/else, When |
| 05 | `day-05-loops/` | For, While, Repeat |
| 06 | `day-06-functions/` | Functions, Lambda |
| 07 | `day-07-collections/` | List, Map, Set |

---

## 📅 TUẦN 2: JETPACK COMPOSE UI (Day 08-14)

| Ngày | Thư mục | Nội dung |
|------|---------|----------|
| 08 | `day-08-compose-intro/` | Text, Column, Row, Modifier |
| 09 | `day-09-button-textfield-events/` | Button, TextField, Events |
| 10 | `day-10-image-icon-card/` | Image, Icon, Card, Surface |
| 11 | `day-11-lazy-layouts/` | LazyColumn, LazyRow |
| 12 | `day-12-state-management/` | Remember, State Hoisting |
| 13 | `day-13-navigation/` | Navigation, Arguments |
| 14 | `day-14-scaffold-material/` | Scaffold, TopBar, BottomBar |

---

## 📅 TUẦN 3: ARCHITECTURE & DATA (Day 15-21)

| Ngày | Thư mục | Nội dung |
|------|---------|----------|
| 15 | `day-15-viewmodel/` | ViewModel, UI State |
| 16 | `day-16-stateflow-coroutines/` | StateFlow, Coroutines |
| 17 | `day-17-room-setup/` | Room: Entity, DAO, Database |
| 18 | `day-18-room-crud/` | Room CRUD Operations |
| 19 | `day-19-retrofit/` | Retrofit API Calls |
| 20 | `day-20-repository/` | Repository Pattern, Offline-first |
| 21 | `day-21-hilt-di/` | Hilt Dependency Injection |

---

## 📅 TUẦN 4: ADVANCED & PROJECT (Day 22-30)

| Ngày | Thư mục | Nội dung |
|------|---------|----------|
| 22 | `day-22-mvvm-complete/` | MVVM Architecture Complete |
| 23 | `day-23-error-handling/` | Error Handling, Result Pattern |
| 24 | `day-24-theming/` | Material 3 Theming |
| 25 | `day-25-animation/` | Compose Animations |
| 26 | `day-26-testing/` | Unit Test, UI Test |
| 27-30 | `day-27-30-final-project/` | 📱 Todo App Project |

---

## 🎯 FINAL PROJECT: TODO APP

Xây dựng ứng dụng Todo hoàn chỉnh với:
- ✅ CRUD Operations
- ✅ Room Database
- ✅ MVVM Architecture
- ✅ Hilt DI
- ✅ Navigation
- ✅ Material 3 Theming
- ✅ Animations
- ✅ Search & Filter

---

## 📦 DEPENDENCIES CHUẨN

```kotlin
// build.gradle.kts (app)
dependencies {
    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    
    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
```

---

## 📁 CẤU TRÚC THƯ MỤC

```
D:\kotlin\
├── day-01-hello-kotlin/
├── day-02-variables/
├── day-03-operators/
├── day-04-control-flow/
├── day-05-loops/
├── day-06-functions/
├── day-07-collections/
├── day-08-compose-intro/
├── day-09-button-textfield-events/
├── day-10-image-icon-card/
├── day-11-lazy-layouts/
├── day-12-state-management/
├── day-13-navigation/
├── day-14-scaffold-material/
├── day-15-viewmodel/
├── day-16-stateflow-coroutines/
├── day-17-room-setup/
├── day-18-room-crud/
├── day-19-retrofit/
├── day-20-repository/
├── day-21-hilt-di/
├── day-22-mvvm-complete/
├── day-23-error-handling/
├── day-24-theming/
├── day-25-animation/
├── day-26-testing/
├── day-27-30-final-project/
└── KOTLIN_ANDROID_ROADMAP.md  ← Bạn đang đọc file này
```

---

## 🚀 NEXT STEPS SAU 30 NGÀY

1. **Build your own apps**: Áp dụng kiến thức để tạo app riêng
2. **Learn more topics**: WorkManager, DataStore, Paging 3
3. **Backend integration**: Firebase, REST APIs
4. **Publish**: Đăng ký Google Play Developer, publish app lên Store

---

**Chúc bạn học tốt! 🎉**
