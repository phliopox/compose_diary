package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import java.time.LocalDate

class GetDiaryByDateUseCase(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke(date : LocalDate) = appRepository.getDiaryByDate(date)
}