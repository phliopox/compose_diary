package com.example.gradientdiary.domain

import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke(categoryEntity: CategoryEntity) = appRepository.deleteCategory(categoryEntity)
}