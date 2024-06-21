package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class UpdateCategoryNameUseCase  @Inject constructor(private val appRepository: DiaryAppRepository){
    operator fun invoke(id : Long , newName:String) = appRepository.updateCategory(id ,newName)

}