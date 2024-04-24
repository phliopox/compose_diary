package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val appRepository: DiaryAppRepository) {
    operator fun invoke() = appRepository.getAllCategory()
}