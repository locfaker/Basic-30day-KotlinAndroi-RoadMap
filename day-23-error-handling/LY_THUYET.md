# Day 23: Error Handling & UX States

---

## 🎯 MỤC TIÊU
Xử lý lỗi và các trạng thái UX (Loading, Empty, Error, Success).

---

## PHẦN 1: UX STATES

```kotlin
sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    object Empty : UiState<Nothing>
}
```

---

## PHẦN 2: RESULT WRAPPER

```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    
    fun getOrNull(): T? = (this as? Success)?.data
    fun exceptionOrNull(): Exception? = (this as? Error)?.exception
}

suspend fun <T> safeCall(call: suspend () -> T): Result<T> {
    return try {
        Result.Success(call())
    } catch (e: Exception) {
        Result.Error(e)
    }
}
```

---

## PHẦN 3: ERROR HANDLING IN VIEWMODEL

```kotlin
class PostsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()
    
    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            when (val result = repository.getPosts()) {
                is Result.Success -> {
                    _uiState.value = if (result.data.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(result.data)
                    }
                }
                is Result.Error -> {
                    _uiState.value = UiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}
```

---

## PHẦN 4: UI COMPONENTS

```kotlin
@Composable
fun <T> UiStateContent(
    state: UiState<T>,
    onRetry: () -> Unit,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is UiState.Loading -> LoadingContent()
        is UiState.Empty -> EmptyContent()
        is UiState.Error -> ErrorContent(state.message, onRetry)
        is UiState.Success -> content(state.data)
    }
}

@Composable
fun LoadingContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Inbox, null, Modifier.size(64.dp))
            Text("No data")
        }
    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Error, null, tint = MaterialTheme.colorScheme.error)
            Text(message)
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}
```
