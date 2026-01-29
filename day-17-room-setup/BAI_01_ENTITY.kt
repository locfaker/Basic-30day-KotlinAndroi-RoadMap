/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 17 - BÀI 1: ENTITY (BẢNG DỮ LIỆU)                        ║
 * ║                                                               ║
 * ║  File này chỉ để đọc hiểu, không copy vào MainActivity        ║
 * ║  Tạo file riêng: data/Note.kt                                 ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity = Đánh dấu đây là 1 bảng trong database
 * tableName = Tên bảng (mặc định = tên class)
 */
@Entity(tableName = "notes")
data class Note(
    /**
     * @PrimaryKey = Khóa chính, mỗi row có giá trị duy nhất
     * autoGenerate = true: Room tự động tạo ID tăng dần
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH ENTITY:                                           ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  ENTITY là gì?                                                ║
 * ║  → Data class đại diện cho 1 BẢNG trong SQLite database       ║
 * ║  → Mỗi property = 1 CỘT trong bảng                            ║
 * ║  → Mỗi instance = 1 HÀNG (row) trong bảng                     ║
 * ║                                                               ║
 * ║  VÍ DỤ: Bảng notes                                            ║
 * ║  ┌────┬───────────┬──────────────┬───────────┬───────────┐    ║
 * ║  │ id │   title   │   content    │ createdAt │isCompleted│    ║
 * ║  ├────┼───────────┼──────────────┼───────────┼───────────┤    ║
 * ║  │  1 │ "Note 1"  │ "Content 1"  │ 17123...  │   false   │    ║
 * ║  │  2 │ "Note 2"  │ "Content 2"  │ 17124...  │   true    │    ║
 * ║  └────┴───────────┴──────────────┴───────────┴───────────┘    ║
 * ║                                                               ║
 * ║  CÁC ANNOTATIONS:                                             ║
 * ║  @Entity(tableName = "...")  → Tên bảng                       ║
 * ║  @PrimaryKey                 → Khóa chính                     ║
 * ║  @ColumnInfo(name = "...")   → Đổi tên cột (optional)         ║
 * ║  @Ignore                     → Không lưu vào database         ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 * 
 * 🎯 BÀI TẬP:
 * 1. Tạo Entity User(id, name, email, age)
 * 2. Tạo Entity Product(id, name, price, category)
 * 3. Thêm annotation @ColumnInfo(name = "note_title") cho title
 */
