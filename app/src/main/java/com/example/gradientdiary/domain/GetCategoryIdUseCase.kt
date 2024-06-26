package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class GetCategoryIdUseCase @Inject constructor(private val appRepository: DiaryAppRepository) {
    operator fun invoke(name: String) = appRepository.getCategoryIdByName(name)
}