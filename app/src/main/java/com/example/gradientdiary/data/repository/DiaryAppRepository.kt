package com.example.gradientdiary.data.repository

import android.icu.util.LocaleData
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface DiaryAppRepository {
    fun getAllDiary() : Flow<List<DiaryEntity>>?
    fun insertDiary(diaryEntity: DiaryEntity)
    fun deleteDiary(diaryEntity: DiaryEntity)
    fun getDiaryByDate(date : LocalDate) : Flow<DiaryEntity>
    fun getAllCategory() : Flow<List<CategoryEntity>>
    fun deleteCategory(categoryEntity: CategoryEntity)

    fun insertCategory(categoryEntity: CategoryEntity)
}