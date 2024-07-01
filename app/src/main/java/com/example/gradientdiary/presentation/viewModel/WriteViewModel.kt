package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.gradientdiary.data.DiaryModel
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.domain.DeleteDiaryUseCase
import com.example.gradientdiary.domain.GetDiaryUseCase
import com.example.gradientdiary.domain.SaveDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val saveDiaryUseCase: SaveDiaryUseCase,
    private val getDiaryUseCase: GetDiaryUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
) : ViewModel() {

    private val _diary = MutableStateFlow<DiaryEntity?>(null)

    suspend fun getDiaryByDateAndCategory(categoryId: Long, date: String): DiaryEntity? {
        return try {
            val result = getDiaryUseCase.invoke(categoryId, date)
                .firstOrNull()
            _diary.value = result
            result
        } catch (e: Exception) {
            Timber.e("데이터 로딩 중 오류 발생 : $e")
            null
        }
    }


    fun saveDiary(diaryModel: DiaryModel) {
        val converted = diaryModel.contents
            .asSequence()
            .map { it.convertToContentBlockEntity() }
            .mapIndexed { index, contentBlockEntity ->
                contentBlockEntity.seq = index + 1L
                contentBlockEntity
            }.toList()

        val diaryEntity = DiaryEntity(
            // id = diaryModel.id,
            categoryId = diaryModel.categoryId,
            contents = converted,
            title = diaryModel.title,
            updateDate = diaryModel.updateDate
        )

        saveDiaryUseCase.invoke(diaryEntity)
    }

    fun deleteDiary() {
        Timber.e("deleteDiary 호출 : ${_diary.value}")
        _diary.value?.let {
            deleteDiaryUseCase.invoke(it)
        }
    }
}