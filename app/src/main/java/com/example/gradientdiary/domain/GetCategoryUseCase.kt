package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val appRepository: DiaryAppRepository) {
    operator fun invoke(name: String) = appRepository.getCategoryIdByName(name)

    operator fun invoke(id: Long): String {
        return appRepository.getCategoryNameById(id)
    }

    operator fun invoke() = appRepository.getAllCategory()
}