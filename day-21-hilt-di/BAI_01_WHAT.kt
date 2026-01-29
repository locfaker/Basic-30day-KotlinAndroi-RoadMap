/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 21 - BÀI 1: DEPENDENCY INJECTION LÀ GÌ                   ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  VẤN ĐỀ KHÔNG CÓ DI:                                          ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  // ViewModel tự tạo dependencies                             ║
 * ║  class UserViewModel : ViewModel() {                          ║
 * ║      // Phải tự tạo ApiService                                ║
 * ║      private val apiService = Retrofit.Builder()              ║
 * ║          .baseUrl("...")                                      ║
 * ║          .build()                                             ║
 * ║          .create(ApiService::class.java)                      ║
 * ║                                                               ║
 * ║      // Phải có context để tạo database                       ║
 * ║      // KHÔNG CÓ context trong ViewModel thông thường!        ║
 * ║      private val userDao = AppDatabase                        ║
 * ║          .getDatabase(???)  // Lỗi!                           ║
 * ║          .userDao()                                           ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  VẤN ĐỀ:                                                      ║
 * ║  → Tightly coupled (phụ thuộc chặt)                           ║
 * ║  → Khó test (không mock được)                                 ║
 * ║  → Khó reuse                                                  ║
 * ║  → Khó thay đổi implementation                                ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI PHÁP: DEPENDENCY INJECTION                              ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  // Dependencies được "inject" (tiêm) từ bên ngoài            ║
 * ║  class UserViewModel @Inject constructor(                     ║
 * ║      private val repository: UserRepository  // Được inject   ║
 * ║  ) : ViewModel() {                                            ║
 * ║      // Không cần biết cách tạo repository                    ║
 * ║  }                                                            ║
 * ║                                                               ║
 * ║  LỢI ÍCH:                                                     ║
 * ║  → Loosely coupled (ít phụ thuộc)                             ║
 * ║  → Dễ test (mock repository)                                  ║
 * ║  → Dễ reuse                                                   ║
 * ║  → Dễ thay đổi implementation                                 ║
 * ║                                                               ║
 * ║  HILT:                                                        ║
 * ║  → DI framework của Google cho Android                        ║
 * ║  → Tự động generate code để inject dependencies               ║
 * ║  → Quản lý lifecycle (singleton, per-activity, per-fragment)  ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

// KHÔNG DI:
class UserViewModelBad : ViewModel() {
    // ❌ Tự tạo mọi thứ = khó test, khó thay đổi
    private val api = RetrofitClient.apiService
    private val dao = AppDatabase.getDatabase(/* context??? */).userDao()
    private val repository = UserRepositoryImpl(dao, api)
}

// CÓ DI:
class UserViewModelGood @Inject constructor(
    private val repository: UserRepository  // ✅ Được inject
) : ViewModel() {
    // Không quan tâm repository tạo như thế nào
}

/**
 * 🎯 BÀI TẬP:
 * 1. Xác định các dependencies trong project của bạn
 * 2. Vẽ dependency graph (class nào phụ thuộc class nào)
 * 3. Đọc tiếp BAI_02_SETUP.kt để biết cách setup Hilt
 */
