package com.example.gradientdiary.domain

import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class SaveCategoryUseCase @Inject constructor(private val appRepository: DiaryAppRepository) {
    operator fun invoke(categoryEntity: CategoryEntity) =
        appRepository.insertCategory(categoryEntity)


    //category name update
    operator fun invoke(id : Long , newName:String) = appRepository.updateCategory(id ,newName)

}