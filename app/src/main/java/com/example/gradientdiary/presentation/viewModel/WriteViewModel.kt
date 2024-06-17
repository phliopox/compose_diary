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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
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

    //private val _diary = MutableStateFlow<DiaryEntity?>(null)
    //var diary: StateFlow<DiaryEntity?> = _diary

    fun getCategory() :String {
        // 현재 선택된 category , 스토리지에 없을시 "일기" 로 반환된다.
        return storage.getCategory()
    }

    suspend fun getDiaryByDate(date: String): DiaryEntity? {
        return try {
            getDiaryByDateUseCase.invoke(date)
                .firstOrNull()
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
            id = diaryModel.id,
            category =  diaryModel.category,
            contents = converted,
            title = diaryModel.title,
            updateDate = diaryModel.updateDate
        )

        saveDiaryUseCase.invoke(diaryEntity)
    }


}