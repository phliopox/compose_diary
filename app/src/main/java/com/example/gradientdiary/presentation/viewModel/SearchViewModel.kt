package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.gradientdiary.domain.SearchDiaryByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiaryByKeywordUseCase: SearchDiaryByKeywordUseCase
): ViewModel() {
    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun  onSearchTextChange (text: String ) {
        _searchText.value = text
    }

    fun  onToogleSearch () {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange( "" )
        }
    }
}