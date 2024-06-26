package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDiaryByDateUseCase @Inject constructor(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke(categoryId: Long, date: String) =
        appRepository.getDiaryByDate(categoryId, date)
}