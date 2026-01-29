/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 22 - BÀI 2: CẤU TRÚC PROJECT                             ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  CẤU TRÚC THƯ MỤC CHUẨN:                                      ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  app/src/main/java/com/example/myapp/                         ║
 * ║  │                                                            ║
 * ║  ├── MyApplication.kt          @HiltAndroidApp                ║
 * ║  │                                                            ║
 * ║  ├── data/                     DATA LAYER                     ║
 * ║  │   ├── local/                                               ║
 * ║  │   │   ├── AppDatabase.kt    Room Database                  ║
 * ║  │   │   ├── entity/           Entities                       ║
 * ║  │   │   │   └── UserEntity.kt                                ║
 * ║  │   │   └── dao/              DAOs                           ║
 * ║  │   │       └── UserDao.kt                                   ║
 * ║  │   │                                                        ║
 * ║  │   ├── remote/                                              ║
 * ║  │   │   ├── ApiService.kt     Retrofit interface             ║
 * ║  │   │   └── dto/              Data Transfer Objects          ║
 * ║  │   │       └── UserDto.kt                                   ║
 * ║  │   │                                                        ║
 * ║  │   ├── repository/                                          ║
 * ║  │   │   ├── UserRepository.kt Interface                      ║
 * ║  │   │   └── UserRepositoryImpl.kt                            ║
 * ║  │   │                                                        ║
 * ║  │   └── mapper/               Entity ↔ Domain ↔ DTO          ║
 * ║  │       └── UserMapper.kt                                    ║
 * ║  │                                                            ║
 * ║  ├── domain/                   DOMAIN LAYER (optional)        ║
 * ║  │   ├── model/                Domain models                  ║
 * ║  │   │   └── User.kt                                          ║
 * ║  │   └── usecase/              Use cases                      ║
 * ║  │       └── GetUsersUseCase.kt                               ║
 * ║  │                                                            ║
 * ║  ├── ui/                       UI LAYER                       ║
 * ║  │   ├── theme/                                               ║
 * ║  │   │   ├── Color.kt                                         ║
 * ║  │   │   ├── Theme.kt                                         ║
 * ║  │   │   └── Type.kt                                          ║
 * ║  │   │                                                        ║
 * ║  │   ├── components/           Reusable composables           ║
 * ║  │   │   ├── LoadingScreen.kt                                 ║
 * ║  │   │   └── ErrorScreen.kt                                   ║
 * ║  │   │                                                        ║
 * ║  │   ├── screens/              Feature screens                ║
 * ║  │   │   ├── home/                                            ║
 * ║  │   │   │   ├── HomeScreen.kt                                ║
 * ║  │   │   │   └── HomeViewModel.kt                             ║
 * ║  │   │   └── detail/                                          ║
 * ║  │   │       ├── DetailScreen.kt                              ║
 * ║  │   │       └── DetailViewModel.kt                           ║
 * ║  │   │                                                        ║
 * ║  │   └── navigation/                                          ║
 * ║  │       └── NavGraph.kt                                      ║
 * ║  │                                                            ║
 * ║  ├── di/                       DEPENDENCY INJECTION           ║
 * ║  │   ├── AppModule.kt          Provides singletons            ║
 * ║  │   ├── DatabaseModule.kt                                    ║
 * ║  │   ├── NetworkModule.kt                                     ║
 * ║  │   └── RepositoryModule.kt                                  ║
 * ║  │                                                            ║
 * ║  └── util/                     UTILITIES                      ║
 * ║      ├── Resource.kt           Wrapper class                  ║
 * ║      └── Constants.kt                                         ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GỌN HƠN CHO APP NHỎ:                                         ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  app/src/main/java/com/example/myapp/                         ║
 * ║  ├── data/                                                    ║
 * ║  │   ├── User.kt              Entity                          ║
 * ║  │   ├── UserDao.kt           DAO                             ║
 * ║  │   ├── AppDatabase.kt       Database                        ║
 * ║  │   ├── ApiService.kt        Retrofit                        ║
 * ║  │   └── UserRepository.kt    Repository                      ║
 * ║  │                                                            ║
 * ║  ├── ui/                                                      ║
 * ║  │   ├── HomeScreen.kt                                        ║
 * ║  │   ├── HomeViewModel.kt                                     ║
 * ║  │   └── theme/                                               ║
 * ║  │                                                            ║
 * ║  ├── di/                                                      ║
 * ║  │   └── AppModule.kt                                         ║
 * ║  │                                                            ║
 * ║  └── MainActivity.kt                                          ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
