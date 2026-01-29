# Day 24: Theming & Dark Mode

---

## 🎯 MỤC TIÊU
Tạo theme và hỗ trợ Dark Mode trong Jetpack Compose.

---

## PHẦN 1: COLOR SCHEME

```kotlin
// ui/theme/Color.kt
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
```

---

## PHẦN 2: THEME

```kotlin
// ui/theme/Theme.kt
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

---

## PHẦN 3: SỬ DỤNG THEME COLORS

```kotlin
@Composable
fun ThemedCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            "Hello",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
```

---

## PHẦN 4: TOGGLE DARK MODE

```kotlin
@Composable
fun ThemeToggle() {
    var isDark by rememberSaveable { mutableStateOf(false) }
    
    MyAppTheme(darkTheme = isDark) {
        Scaffold {
            Column {
                Switch(
                    checked = isDark,
                    onCheckedChange = { isDark = it }
                )
                // App content
            }
        }
    }
}
```

---

## PHẦN 5: CUSTOM COLORS

```kotlin
val LocalCustomColors = staticCompositionLocalOf { CustomColors() }

data class CustomColors(
    val success: Color = Color(0xFF4CAF50),
    val warning: Color = Color(0xFFFF9800),
    val info: Color = Color(0xFF2196F3)
)

@Composable
fun MyAppTheme(...) {
    CompositionLocalProvider(
        LocalCustomColors provides CustomColors()
    ) {
        MaterialTheme(...)
    }
}

// Usage
val successColor = LocalCustomColors.current.success
```
