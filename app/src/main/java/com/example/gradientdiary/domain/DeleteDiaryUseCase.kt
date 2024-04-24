package com.example.gradientdiary.domain

import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.data.repository.DiaryAppRepository

class DeleteDiaryUseCase(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke(diaryEntity: DiaryEntity) = appRepository.deleteDairy(diaryEntity)
}