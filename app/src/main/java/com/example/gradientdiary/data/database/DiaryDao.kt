package com.example.gradientdiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@Dao
abstract class DiaryDao {
    @Query("select * from DiaryEntity")
    abstract fun getAllDairy(): Flow<List<DiaryEntity>>

    @Insert(onConflict = REPLACE)
    abstract fun insertDairyEntity(diaryEntity: DiaryEntity)

    @Delete
    abstract fun deleteDairy(diaryEntity: DiaryEntity)

    @Query("DELETE FROM DiaryEntity WHERE id = :diaryId")
    abstract fun deleteDairy(diaryId: Long)

    @Query("select * from DiaryEntity where id = :diaryId")
    abstract fun getDiaryByDiaryId(diaryId:Long) : Flow<DiaryEntity>
    @Query("select * from DiaryEntity where updateDate = :updateDate AND categoryId = :categoryId")
    abstract fun getDairyByDate(categoryId: Long, updateDate: String): Flow<DiaryEntity>

    @Query("select * from DiaryEntity where categoryId = :categoryId")
    abstract fun getDiaryByCategory(categoryId: Long) : Flow<List<DiaryEntity>>?

    @Query("SELECT * FROM DiaryEntity where title LIKE '%' || :keyword || '%' OR contents LIKE '%' || :keyword || '%'")
    abstract fun searchDiaryByKeyword(keyword : String) : Flow<List<DiaryEntity>>

    @Query("SELECT * FROM DiaryEntity where (title LIKE '%' || :keyword || '%' OR contents LIKE '%' || :keyword || '%') AND categoryId = :categoryId ")
    abstract fun searchDiaryByKeyword(keyword : String,categoryId: Long?) : Flow<List<DiaryEntity>>
}