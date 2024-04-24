package com.example.gradientdiary.data.repository

import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.StateFlow

interface DiaryAppRepository {
    fun getAllDairy() : StateFlow<List<DiaryEntity>>
    fun insertDairy(diaryEntity: DiaryEntity)
    fun deleteDairy(diaryEntity: DiaryEntity)
    fun getAllCategory() : StateFlow<List<CategoryEntity>>

    fun deleteCategory(categoryEntity: CategoryEntity)

    fun insertCategory(categoryEntity: CategoryEntity)
}