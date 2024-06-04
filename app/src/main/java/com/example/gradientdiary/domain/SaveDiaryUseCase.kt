package com.example.gradientdiary.domain

import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class SaveDiaryUseCase @Inject constructor(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke(diaryEntity: DiaryEntity) = appRepository.insertDiary(diaryEntity)
}