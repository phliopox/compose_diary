package com.example.gradientdiary.data.repository

import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow

interface DiaryAppRepository {
    fun getAllDiary(): Flow<List<DiaryEntity>>?
    fun insertDiary(diaryEntity: DiaryEntity)
    fun deleteDiary(diaryEntity: DiaryEntity)
    fun getDiaryByDate(categoryId: Long, date: String): Flow<DiaryEntity>
    fun searchDiaryByKeyword(keyword: String ): Flow<List<DiaryEntity>>?
    fun searchDiaryByKeyword(keyword: String , categoryId: Long): Flow<List<DiaryEntity>>?
    fun getAllCategory(): Flow<List<CategoryEntity>>
    fun deleteCategory(categoryEntity: CategoryEntity)
    fun insertCategory(categoryEntity: CategoryEntity)
    fun updateCategory(id: Long, newName: String)

    fun getCategoryIdByName(name: String): Long

    fun getCategoryNameById(id : Long):String
}