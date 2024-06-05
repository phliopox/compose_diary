package com.example.gradientdiary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity

@Database(
    entities = [
        DiaryEntity::class,
        CategoryEntity::class
    ],
    version =2,
    exportSchema = false
)
@TypeConverters(com.example.gradientdiary.data.database.TypeConverters::class)
abstract class DiaryAppDataBase :RoomDatabase(){
    abstract fun diaryDao() : DiaryDao
    abstract fun categoryDao() : CategoryDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 예: "diary_table"에 "new_column" 추가
        database.execSQL("ALTER TABLE DiaryEntity ADD COLUMN title TEXT")
    }
}