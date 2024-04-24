package com.example.gradientdiary.data.repository

import com.example.gradientdiary.data.database.CategoryDao
import com.example.gradientdiary.data.database.DiaryDao
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DefaultDiaryAppRepository @Inject constructor(
    private val diaryDao: DiaryDao,
    private val categoryDao: CategoryDao
) : DiaryAppRepository {
    override fun getAllDairy(): StateFlow<List<DiaryEntity>> = diaryDao.getAllDairy()
    override fun insertDairy(diaryEntity: DiaryEntity) = diaryDao.insertDairyEntity(diaryEntity)
    override fun deleteDairy(diaryEntity: DiaryEntity) = diaryDao.deleteDairy(diaryEntity)
    override fun getAllCategory(): StateFlow<List<CategoryEntity>> = categoryDao.getAllCategory()
    override fun deleteCategory(categoryEntity: CategoryEntity) = categoryDao.deleteCategory(categoryEntity)
    override fun insertCategory(categoryEntity: CategoryEntity) = categoryDao.insertCategoryEntity(categoryEntity)


}