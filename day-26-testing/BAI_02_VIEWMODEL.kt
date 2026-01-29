/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 26 - BÀI 2: TEST VIEWMODEL                               ║
 * ║                                                               ║
 * ║  File: app/src/test/java/.../ViewModelTest.kt                 ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

// ===== VIEWMODEL CẦN TEST =====
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    
    fun increment() { _count.value++ }
    fun decrement() { _count.value-- }
    fun reset() { _count.value = 0 }
}

// ===== TEST CLASS =====
@OptIn(ExperimentalCoroutinesApi::class)
class CounterViewModelTest {
    
    private lateinit var viewModel: CounterViewModel
    
    // Rule để test coroutines
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    @Before
    fun setup() {
        viewModel = CounterViewModel()
    }
    
    @Test
    fun `initial count should be zero`() = runTest {
        val count = viewModel.count.first()
        assertEquals(0, count)
    }
    
    @Test
    fun `increment should increase count by 1`() = runTest {
        viewModel.increment()
        assertEquals(1, viewModel.count.first())
        
        viewModel.increment()
        assertEquals(2, viewModel.count.first())
    }
    
    @Test
    fun `decrement should decrease count by 1`() = runTest {
        viewModel.increment()
        viewModel.increment()
        viewModel.decrement()
        assertEquals(1, viewModel.count.first())
    }
    
    @Test
    fun `reset should set count to zero`() = runTest {
        viewModel.increment()
        viewModel.increment()
        viewModel.reset()
        assertEquals(0, viewModel.count.first())
    }
}

// ===== MAIN DISPATCHER RULE =====
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }
    
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  TEST VIEWMODEL VỚI REPOSITORY:                               ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  // Fake Repository                                           ║
 * ║  class FakeUserRepository : UserRepository {                  ║
 * ║      private val users = mutableListOf<User>()                ║
 * ║                                                               ║
 * ║      override fun getUsers() = flowOf(users.toList())         ║
 * ║                                                               ║
 * ║      override suspend fun addUser(user: User) {               ║
 * ║          users.add(user)                                      ║
 * ║      }                                                        ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  // Test với Fake                                             ║
 * ║  class UserViewModelTest {                                    ║
 * ║      private lateinit var fakeRepo: FakeUserRepository        ║
 * ║      private lateinit var viewModel: UserViewModel            ║
 * ║                                                               ║
 * ║      @Before                                                  ║
 * ║      fun setup() {                                            ║
 * ║          fakeRepo = FakeUserRepository()                      ║
 * ║          viewModel = UserViewModel(fakeRepo)                  ║
 * ║      }                                                        ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * ⚠️ DEPENDENCIES cho test coroutines:
 * testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
 */
