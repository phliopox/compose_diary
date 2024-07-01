package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.domain.GetCategoryUseCase
import com.example.gradientdiary.domain.SearchDiaryByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiaryByKeywordUseCase: SearchDiaryByKeywordUseCase
) : ViewModel() {


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchResult = MutableStateFlow<List<DiaryEntity>?>(null)
    val searchResult: StateFlow<List<DiaryEntity>?> = _searchResult

    private val filterCategory = MutableStateFlow("")
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    suspend fun searchAction(categoryFilter: Long?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val keyword = _searchText.value
                if (categoryFilter == null || categoryFilter == 0L) {
                    searchDiaryByKeywordUseCase.invoke(keyword)?.collectLatest { result ->
                        _searchResult.value = result
                    }
                } else {
                    searchDiaryByKeywordUseCase.invoke(keyword, categoryFilter)
                        ?.collectLatest { result ->
                            _searchResult.value = result
                        }
                }
            }
        }
    }

}