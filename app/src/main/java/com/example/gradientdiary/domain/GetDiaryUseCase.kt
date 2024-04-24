package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository

class GetDiaryUseCase(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke() = appRepository.getAllDairy()
}