package com.example.gradientdiary.data.repository

import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow

interface DiaryAppRepository {
    fun getAllDiary() : Flow<List<DiaryEntity>>?
    fun insertDiary(diaryEntity: DiaryEntity)
    fun deleteDiary(diaryEntity: DiaryEntity)
    fun getDiaryByDate(date : String) : Flow<DiaryEntity>
    fun getAllCategory() : Flow<List<CategoryEntity>>
    fun deleteCategory(categoryEntity: CategoryEntity)
    fun insertCategory(categoryEntity: CategoryEntity)
    fun updateCategory(id : Long , newName : String)
}