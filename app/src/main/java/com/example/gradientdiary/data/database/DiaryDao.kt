package com.example.gradientdiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.StateFlow

@Dao
abstract class DiaryDao {
    @Query("select * from DiaryEntity")
    abstract fun getAllDairy(): StateFlow<List<DiaryEntity>>

    @Insert(onConflict = REPLACE)
    abstract fun insertDairyEntity(diaryEntity: DiaryEntity)

    @Delete
    abstract fun deleteDairy(diaryEntity: DiaryEntity)
}