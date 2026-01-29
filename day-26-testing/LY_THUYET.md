# Day 26: Testing Basics

---

## 🎯 MỤC TIÊU
Unit Testing và UI Testing cơ bản.

---

## PHẦN 1: DEPENDENCIES

```kotlin
testImplementation("junit:junit:4.13.2")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
testImplementation("io.mockk:mockk:1.13.8")

androidTestImplementation("androidx.test.ext:junit:1.1.5")
androidTestImplementation("androidx.compose.ui:ui-test-junit4")
```

---

## PHẦN 2: UNIT TEST VIEWMODEL

```kotlin
class NotesViewModelTest {
    private lateinit var viewModel: NotesViewModel
    private lateinit var repository: FakeNoteRepository
    
    @Before
    fun setup() {
        repository = FakeNoteRepository()
        viewModel = NotesViewModel(repository)
    }
    
    @Test
    fun `load notes should update state with notes`() = runTest {
        // Given
        repository.setNotes(listOf(Note(1, "Test", "Content")))
        
        // When
        viewModel.loadNotes()
        
        // Then
        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success).data.size)
    }
}
```

---

## PHẦN 3: FAKE REPOSITORY

```kotlin
class FakeNoteRepository : NoteRepository {
    private val notes = mutableListOf<Note>()
    
    fun setNotes(list: List<Note>) {
        notes.clear()
        notes.addAll(list)
    }
    
    override fun getNotes(): Flow<List<Note>> = flow { emit(notes) }
    override suspend fun getNoteById(id: Int) = notes.find { it.id == id }
    override suspend fun createNote(title: String, content: String) {
        notes.add(Note(notes.size + 1, title, content))
    }
    override suspend fun deleteNote(id: Int) {
        notes.removeIf { it.id == id }
    }
}
```

---

## PHẦN 4: COMPOSE UI TEST

```kotlin
class NotesScreenTest {
    @get:Rule
    val composeRule = createComposeRule()
    
    @Test
    fun notesScreen_displaysNotes() {
        // Given
        val notes = listOf(Note(1, "Test Note", "Content"))
        
        // When
        composeRule.setContent {
            NotesScreen(notes = notes)
        }
        
        // Then
        composeRule.onNodeWithText("Test Note").assertIsDisplayed()
    }
    
    @Test
    fun addButton_click_showsDialog() {
        composeRule.setContent { NotesScreen() }
        
        composeRule.onNodeWithContentDescription("Add").performClick()
        composeRule.onNodeWithText("New Note").assertIsDisplayed()
    }
}
```
