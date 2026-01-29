# Day 25: Animation Basics

---

## 🎯 MỤC TIÊU
Animation cơ bản trong Jetpack Compose.

---

## PHẦN 1: ANIMATESTATE

```kotlin
// Animate color
val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) Color.Blue else Color.Gray,
    animationSpec = tween(durationMillis = 300)
)

// Animate size
val size by animateDpAsState(
    targetValue = if (expanded) 200.dp else 100.dp,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
)

// Animate alpha
val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f
)
```

---

## PHẦN 2: ANIMATEDVISIBILITY

```kotlin
AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn() + slideInVertically(),
    exit = fadeOut() + slideOutVertically()
) {
    Text("Hello")
}
```

---

## PHẦN 3: ANIMATEDCONTENT

```kotlin
AnimatedContent(
    targetState = count,
    transitionSpec = {
        if (targetState > initialState) {
            slideInVertically { -it } + fadeIn() with
                slideOutVertically { it } + fadeOut()
        } else {
            slideInVertically { it } + fadeIn() with
                slideOutVertically { -it } + fadeOut()
        }
    }
) { targetCount ->
    Text("$targetCount")
}
```

---

## PHẦN 4: CROSSFADE

```kotlin
Crossfade(targetState = currentScreen) { screen ->
    when (screen) {
        Screen.Home -> HomeScreen()
        Screen.Profile -> ProfileScreen()
    }
}
```

---

## PHẦN 5: GESTURE ANIMATION

```kotlin
val offsetX by animateDpAsState(
    targetValue = if (swiped) 300.dp else 0.dp,
    animationSpec = spring()
)

Box(
    modifier = Modifier
        .offset(x = offsetX)
        .draggable(
            state = rememberDraggableState { delta ->
                // Handle drag
            },
            orientation = Orientation.Horizontal
        )
)
```
