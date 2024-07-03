package com.example.gradientdiary.data.repository

import android.icu.util.ULocale.Category
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow

interface DiaryAppRepository {
    //다이어리
    fun getAllDiary(): Flow<List<DiaryEntity>>?
    fun insertDiary(diaryEntity: DiaryEntity)
    fun deleteDiary(diaryEntity: DiaryEntity)
    fun deleteDiary(diaryId: Long)
    fun getDiaryByDate(categoryId: Long, date: String): Flow<DiaryEntity>

    fun getDiaryByCategory(categoryId: Long) : Flow<List<DiaryEntity>>?
    fun searchDiaryByKeyword(keyword: String ): Flow<List<DiaryEntity>>?
    fun searchDiaryByKeyword(keyword: String , categoryId: Long): Flow<List<DiaryEntity>>?
    fun getDiaryByDiaryId(diaryId : Long) : Flow<DiaryEntity>

    //카테고리
    fun getAllCategory(): Flow<List<CategoryEntity>>
    fun deleteCategory(categoryEntity: CategoryEntity)
    fun insertCategory(categoryEntity: CategoryEntity)
    fun updateCategory(id: Long, newName: String)

    fun getCategoryIdByName(name: String): Long

    fun getCategoryNameById(id : Long):String
}