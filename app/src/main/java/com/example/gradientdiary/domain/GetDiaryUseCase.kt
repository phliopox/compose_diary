package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class GetDiaryUseCase @Inject constructor(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke() = appRepository.getAllDiary()

    operator fun invoke(categoryId: Long, date: String) =
        appRepository.getDiaryByDate(categoryId, date)

    operator fun invoke(categoryId: Long) =
        appRepository.getDiaryByCategory(categoryId)
}