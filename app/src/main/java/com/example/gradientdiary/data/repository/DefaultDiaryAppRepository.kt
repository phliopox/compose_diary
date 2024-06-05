package com.example.gradientdiary.data.repository

import com.example.gradientdiary.data.database.CategoryDao
import com.example.gradientdiary.data.database.DiaryDao
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

class DefaultDiaryAppRepository @Inject constructor(
    private val diaryDao: DiaryDao,
    private val categoryDao: CategoryDao
) : DiaryAppRepository {
    override fun getAllDiary(): Flow<List<DiaryEntity>> = diaryDao.getAllDairy()
    override fun insertDiary(diaryEntity: DiaryEntity) = diaryDao.insertDairyEntity(diaryEntity)
    override fun deleteDiary(diaryEntity: DiaryEntity) = diaryDao.deleteDairy(diaryEntity)
    override fun getDiaryByDate(date: LocalDate) = diaryDao.getDairyByDate(date)
    override fun getAllCategory(): Flow<List<CategoryEntity>> = categoryDao.getAllCategory()
    override fun deleteCategory(categoryEntity: CategoryEntity) =
        categoryDao.deleteCategory(categoryEntity)

    override fun insertCategory(categoryEntity: CategoryEntity) =
        categoryDao.insertCategoryEntity(categoryEntity)


}