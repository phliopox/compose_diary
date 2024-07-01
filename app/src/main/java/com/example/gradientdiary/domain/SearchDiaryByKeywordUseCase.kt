package com.example.gradientdiary.domain

import com.example.gradientdiary.data.repository.DiaryAppRepository
import javax.inject.Inject

class SearchDiaryByKeywordUseCase @Inject constructor(
    private val appRepository: DiaryAppRepository
) {
    operator fun invoke(keyword: String, categoryId: Long) =
        appRepository.searchDiaryByKeyword(keyword, categoryId)

    operator fun invoke(keyword: String) =
        appRepository.searchDiaryByKeyword(keyword)

}