package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.domain.GetDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewViewModel @Inject constructor(private val getDiaryUseCase: GetDiaryUseCase) :
    ViewModel() {
    private val _listResult = MutableStateFlow<List<DiaryEntity>?>(null)
    val listResult: StateFlow<List<DiaryEntity>?> = _listResult
    private val filterCategory = MutableStateFlow(0L)

    init {
        viewModelScope.launch {
            refreshAction(null)
        }
    }
    suspend fun refreshAction(categoryFilter: Long?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.e("list view category")
                if (categoryFilter == null || categoryFilter == 0L) {
                    getDiaryUseCase.invoke()?.collectLatest { result ->
                        _listResult.value = result
                    }
                }else {
                    getDiaryUseCase.invoke(categoryFilter)?.collectLatest { result ->
                        _listResult.value = result
                    }
                }
            }
        }
    }
    }