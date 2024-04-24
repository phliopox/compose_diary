package com.example.gradientdiary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity

@Database(
    entities = [
        DiaryEntity::class,
        CategoryEntity::class
    ],
    version =1,
    exportSchema = false
)
@TypeConverters(com.example.gradientdiary.data.database.TypeConverters::class)
abstract class DiaryAppDataBase :RoomDatabase(){
    abstract fun diaryDao() : DiaryDao
    abstract fun categoryDao() : CategoryDao
}