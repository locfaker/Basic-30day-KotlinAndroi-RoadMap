# Bài tập Day 14: Scaffold & Material Components

---

## 🏋️ BÀI TẬP THỰC HÀNH

### Bài 1: Basic Scaffold (Dễ)
Tạo Scaffold với:
- TopAppBar có title "My App"
- FAB với icon Add
- Content là Text "Hello World" căn giữa

---

### Bài 2: TopAppBar đầy đủ (Trung bình)
Tạo màn hình Detail với:
- TopAppBar có:
  - Navigation icon (Back arrow)
  - Title "Chi tiết"
  - Actions: Search, Share, MoreVert
- Content hiển thị thông tin

---

### Bài 3: Snackbar với Undo (Trung bình)
Tạo danh sách items:
- Mỗi item có nút Delete
- Khi xóa → hiện Snackbar "Đã xóa [tên item]"
- Snackbar có action "Hoàn tác"
- Click Hoàn tác → add item lại

---

### Bài 4: Dialog forms (Trung bình)
Tạo app quản lý liên lạc:
- FAB mở Dialog thêm contact
- Dialog có: TextField Tên, TextField SĐT
- Button Hủy/Thêm
- Danh sách contacts hiển thị bên dưới

---

### Bài 5: Bottom Sheet Actions (Khó)
Tạo danh sách sản phẩm:
- Long press sản phẩm → mở BottomSheet
- BottomSheet có options: Sửa, Xóa, Chia sẻ, Yêu thích
- Click option → thực hiện action + đóng sheet

---

### Bài 6: Complete Notes App (Nâng cao)
Tạo app ghi chú hoàn chỉnh:
```
Features:
├── TopAppBar: Title, Search, Settings
├── FAB: Add new note
├── Content: LazyColumn with note cards
├── Click note: Dialog xem chi tiết
├── Long press: BottomSheet options
├── Delete: Snackbar với Undo
└── Empty state: Hiển thị khi chưa có note
```

---

## ❓ CÂU HỎI PHẢN BIỆN

1. **Tại sao phải dùng paddingValues từ Scaffold?**
   > Gợi ý: Tránh content bị che bởi TopBar, BottomBar.

2. **Snackbar và Toast khác nhau như thế nào?**
   > Gợi ý: Material Design, action, dismissible.

3. **AlertDialog vs Dialog khác gì?**
   > Gợi ý: Preset structure vs custom.

4. **Khi nào dùng BottomSheet thay vì Dialog?**
   > Gợi ý: Mobile-friendly, actions list.

5. **FAB Position End vs Center khi nào dùng?**
   > Gợi ý: Primary action vs secondary.

---

## 💡 GỢI Ý GIẢI QUYẾT

**Bài 1:**
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicScaffoldBai1() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My App") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello World")
        }
    }
}
```

**Bài 3:**
```kotlin
@Composable
fun SnackbarUndoExample() {
    val items = remember { mutableStateListOf("Item 1", "Item 2", "Item 3") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items) { item ->
                Row {
                    Text(item, modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        val removedItem = item
                        items.remove(item)
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Đã xóa: $removedItem",
                                actionLabel = "Hoàn tác"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                items.add(removedItem)
                            }
                        }
                    }) {
                        Icon(Icons.Default.Delete, null)
                    }
                }
            }
        }
    }
}
```

**Bài 4:**
```kotlin
@Composable
fun ContactManagerApp() {
    var contacts by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(contacts) { (n, p) ->
                ListItem(
                    headlineContent = { Text(n) },
                    supportingContent = { Text(p) }
                )
            }
        }
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Thêm liên hệ") },
            text = {
                Column {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Tên") })
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("SĐT") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    contacts = contacts + (name to phone)
                    name = ""; phone = ""
                    showDialog = false
                }) { Text("Thêm") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Hủy") }
            }
        )
    }
}
```
