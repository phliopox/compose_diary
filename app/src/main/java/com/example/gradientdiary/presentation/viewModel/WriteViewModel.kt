package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.data.DiaryModel
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.domain.DeleteDiaryUseCase
import com.example.gradientdiary.domain.GetDiaryByDateUseCase
import com.example.gradientdiary.domain.GetDiaryUseCase
import com.example.gradientdiary.domain.SaveDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val saveDiaryUseCase: SaveDiaryUseCase,
    private val getDiaryUseCase: GetDiaryUseCase,
    private val getDiaryByDateUseCase: GetDiaryByDateUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase,
    private val storage : SharedPrefsStorageProvider
) : ViewModel() {

    private val _diary = MutableStateFlow<DiaryEntity?>(null)
    var diary: StateFlow<DiaryEntity?> = _diary

    fun getCategory() :String {
        // 현재 선택된 category , 스토리지에 없을시 "일기" 로 반환된다.
        return storage.get()
    }

    fun getDiaryByDate(date: LocalDate) {
        Timber.e("viewModel getDiaryByDate 호출")
        viewModelScope.launch {
            getDiaryByDateUseCase.invoke(date).collectLatest {
                _diary.value = it
                Timber.e("getDiaryByDate 호출 : $it")
            }
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
            id = diaryModel.id,
            category =  diaryModel.category,
            contents = converted,
            updateDate = diaryModel.updateDate
        )

        saveDiaryUseCase.invoke(diaryEntity)
    }


}