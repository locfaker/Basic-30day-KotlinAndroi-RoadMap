# Day 14: Scaffold & Material Components - Hoàn thiện Tuần 2

---

## 🎯 MỤC TIÊU
Sau bài này, bạn sẽ:
1. Thành thạo **Scaffold** - Layout chính của Material Design
2. Sử dụng **TopAppBar** và **BottomAppBar**
3. Tạo **FloatingActionButton** (FAB)
4. Hiển thị **Snackbar**, **Dialog**, **BottomSheet**
5. Kết hợp tất cả để tạo App hoàn chỉnh

---

## PHẦN 1: SCAFFOLD - KHUNG NỀN CỦA APP

### 1.1 Scaffold là gì?

Scaffold là layout chuẩn của Material Design, cung cấp các slot cho:
- TopBar (thanh trên)
- BottomBar (thanh dưới)
- FloatingActionButton (nút nổi)
- Drawer (menu trượt)
- Snackbar
- Content (nội dung chính)

### 1.2 Scaffold cơ bản

```kotlin
@Composable
fun BasicScaffold() {
    Scaffold(
        topBar = { /* TopAppBar */ },
        bottomBar = { /* BottomNavigation */ },
        floatingActionButton = { /* FAB */ },
        snackbarHost = { /* Snackbar */ }
    ) { paddingValues ->
        // Content - QUAN TRỌNG: Phải dùng paddingValues
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("Nội dung chính")
        }
    }
}
```

---

## PHẦN 2: TOP APP BAR

### 2.1 TopAppBar cơ bản

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My App") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Text("Content")
        }
    }
}
```

### 2.2 TopAppBar với Navigation và Actions

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text("Chi tiết sản phẩm") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
        },
        actions = {
            IconButton(onClick = { /* Search */ }) {
                Icon(Icons.Default.Search, "Search")
            }
            IconButton(onClick = { /* Share */ }) {
                Icon(Icons.Default.Share, "Share")
            }
            IconButton(onClick = { /* More */ }) {
                Icon(Icons.Default.MoreVert, "More")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
```

### 2.3 CenterAlignedTopAppBar

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text("Centered Title") },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Menu, "Menu")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Settings, "Settings")
            }
        }
    )
}
```

---

## PHẦN 3: FLOATING ACTION BUTTON (FAB)

### 3.1 FAB cơ bản

```kotlin
FloatingActionButton(
    onClick = { /* Add action */ }
) {
    Icon(Icons.Default.Add, contentDescription = "Add")
}
```

### 3.2 Extended FAB (có text)

```kotlin
ExtendedFloatingActionButton(
    onClick = { },
    icon = { Icon(Icons.Default.Add, null) },
    text = { Text("Thêm mới") }
)
```

### 3.3 FAB với các kiểu khác

```kotlin
// Small FAB
SmallFloatingActionButton(onClick = { }) {
    Icon(Icons.Default.Add, null)
}

// Large FAB
LargeFloatingActionButton(onClick = { }) {
    Icon(Icons.Default.Add, null, modifier = Modifier.size(36.dp))
}
```

### 3.4 FAB Position trong Scaffold

```kotlin
Scaffold(
    floatingActionButton = {
        FloatingActionButton(onClick = { }) {
            Icon(Icons.Default.Add, null)
        }
    },
    floatingActionButtonPosition = FabPosition.End  // hoặc Center
) { /* content */ }
```

---

## PHẦN 4: SNACKBAR

### 4.1 Snackbar cơ bản

```kotlin
@Composable
fun SnackbarExample() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Hello Snackbar!")
                }
            },
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("Show Snackbar")
        }
    }
}
```

### 4.2 Snackbar với Action

```kotlin
scope.launch {
    val result = snackbarHostState.showSnackbar(
        message = "Item đã được xóa",
        actionLabel = "Hoàn tác",
        duration = SnackbarDuration.Short
    )
    
    when (result) {
        SnackbarResult.ActionPerformed -> {
            // User clicked "Hoàn tác"
        }
        SnackbarResult.Dismissed -> {
            // Snackbar tự động ẩn
        }
    }
}
```

---

## PHẦN 5: DIALOG

### 5.1 AlertDialog cơ bản

```kotlin
@Composable
fun AlertDialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    
    Button(onClick = { showDialog = true }) {
        Text("Show Dialog")
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Xác nhận") },
            text = { Text("Bạn có chắc chắn muốn xóa?") },
            confirmButton = {
                TextButton(onClick = {
                    // Xử lý xác nhận
                    showDialog = false
                }) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}
```

### 5.2 Custom Dialog

```kotlin
@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Nhập tên", fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Tên") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Hủy")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onConfirm(text) }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}
```

---

## PHẦN 6: BOTTOM SHEET

### 6.1 ModalBottomSheet

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    
    Button(onClick = { showBottomSheet = true }) {
        Text("Show Bottom Sheet")
    }
    
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bottom Sheet Content", fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ListItem(
                    headlineContent = { Text("Share") },
                    leadingContent = { Icon(Icons.Default.Share, null) },
                    modifier = Modifier.clickable { }
                )
                ListItem(
                    headlineContent = { Text("Delete") },
                    leadingContent = { Icon(Icons.Default.Delete, null) },
                    modifier = Modifier.clickable { }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
```

---

## PHẦN 7: VÍ DỤ HOÀN CHỈNH - TODO APP

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppComplete() {
    var todos by remember { mutableStateOf(listOf("Học Kotlin", "Làm bài tập")) }
    var showAddDialog by remember { mutableStateOf(false) }
    var newTodo by remember { mutableStateOf("") }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Todos") },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(todo, modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            todos = todos.filter { it != todo }
                            scope.launch {
                                snackbarHostState.showSnackbar("Đã xóa: $todo")
                            }
                        }) {
                            Icon(Icons.Default.Delete, "Delete")
                        }
                    }
                }
            }
        }
    }
    
    // Add Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false; newTodo = "" },
            title = { Text("Thêm Todo") },
            text = {
                OutlinedTextField(
                    value = newTodo,
                    onValueChange = { newTodo = it },
                    label = { Text("Công việc") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (newTodo.isNotBlank()) {
                        todos = todos + newTodo
                        newTodo = ""
                        showAddDialog = false
                    }
                }) {
                    Text("Thêm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false; newTodo = "" }) {
                    Text("Hủy")
                }
            }
        )
    }
}
```

---

## 📝 TÓM TẮT TUẦN 2

| Ngày | Chủ đề | Nội dung chính |
|------|--------|----------------|
| Day 08 | Compose Intro | @Composable, Column, Row, Box, Modifier |
| Day 09 | Inputs | Button, TextField, State cơ bản |
| Day 10 | Media | Image, Icon, Card |
| Day 11 | Lists | LazyColumn, LazyRow, items, key |
| Day 12 | State | remember, mutableStateOf, State Hoisting |
| Day 13 | Navigation | NavController, NavHost, Arguments |
| Day 14 | Scaffold | TopBar, FAB, Snackbar, Dialog |

---

## ➡️ TUẦN 3 SẮP TỚI
**Kiến trúc MVVM và Data Layer**
- Day 15-16: ViewModel và LiveData/StateFlow
- Day 17-18: Room Database
- Day 19-20: Retrofit và API calls
- Day 21: Repository Pattern
