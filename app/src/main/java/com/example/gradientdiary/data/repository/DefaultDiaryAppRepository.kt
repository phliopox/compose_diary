package com.example.gradientdiary.data.repository

import com.example.gradientdiary.data.database.CategoryDao
import com.example.gradientdiary.data.database.DiaryDao
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultDiaryAppRepository @Inject constructor(
    private val diaryDao: DiaryDao,
    private val categoryDao: CategoryDao
) : DiaryAppRepository {
    override fun getAllDiary(): Flow<List<DiaryEntity>> = diaryDao.getAllDairy()
    override fun insertDiary(diaryEntity: DiaryEntity) = diaryDao.insertDairyEntity(diaryEntity)
    override fun deleteDiary(diaryEntity: DiaryEntity) = diaryDao.deleteDairy(diaryEntity)
    override fun deleteDiary(diaryId: Long) =diaryDao.deleteDairy(diaryId)

    override fun getDiaryByDate(categoryId: Long, date: String) =
        diaryDao.getDairyByDate(categoryId, date)

    override fun getDiaryByCategory(categoryId: Long): Flow<List<DiaryEntity>>? = diaryDao.getDiaryByCategory(categoryId)

    override fun searchDiaryByKeyword(keyword: String) = diaryDao.searchDiaryByKeyword(keyword)
    override fun searchDiaryByKeyword(keyword: String, categoryId: Long) =
        diaryDao.searchDiaryByKeyword(keyword, categoryId)

    override fun getDiaryByDiaryId(diaryId: Long): Flow<DiaryEntity>  = diaryDao.getDiaryByDiaryId(diaryId)

    override fun getAllCategory(): Flow<List<CategoryEntity>> = categoryDao.getAllCategory()
    override fun deleteCategory(categoryEntity: CategoryEntity) =
        categoryDao.deleteCategory(categoryEntity)

    override fun insertCategory(categoryEntity: CategoryEntity) =
        categoryDao.insertCategoryEntity(categoryEntity)

    override fun updateCategory(id: Long, newName: String) {
        categoryDao.updateCategoryName(id, newName)
    }

    override fun getCategoryIdByName(name: String): Long = categoryDao.getCategoryIdByName(name)

    override fun getCategoryNameById(id: Long): String = categoryDao.getCategoryNameById(id)

}