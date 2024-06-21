package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.domain.DeleteCategoryUseCase
import com.example.gradientdiary.domain.GetAllCategoryUseCase
import com.example.gradientdiary.domain.SaveCategoryUseCase
import com.example.gradientdiary.domain.UpdateCategoryNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val updateCategoryNameUseCase: UpdateCategoryNameUseCase,
    private val storage: SharedPrefsStorageProvider
) : ViewModel() {

    private var _allCategory = MutableStateFlow<List<CategoryEntity>?>(emptyList())
    val allCategory: StateFlow<List<CategoryEntity>?> = _allCategory

    var allCategoryStr: MutableList<String> = mutableListOf()

    var selectedCategory = storage.getCurrentCategory()

    init {
        viewModelScope.launch {
            refreshAllCategory()
        }
    }

    fun selectedCategoryChange(category: String) {
        storage.saveSelectedCategory(category)
        selectedCategory = category
    }

    private suspend fun refreshAllCategory() {
        _allCategory.value = getAllCategoryUseCase.invoke().firstOrNull()
        _allCategory.value?.let { categories ->
            val categoryNames = categories.map { it.categoryName }
            allCategoryStr = categoryNames.toMutableList()
        }
        Timber.e("allCategory : $allCategoryStr")
    }

    fun deleteCategory(categoryName: String) {
        viewModelScope.launch {
            _allCategory.value?.let { categories ->
                val selectedCategory = categories.filter { it.categoryName == categoryName }
                if (selectedCategory.isEmpty()) {
                    deleteCategoryUseCase.invoke(selectedCategory.first())
                }
                refreshAllCategory()
            }
        }
    }

    fun addNewCategory(categoryName: String) {
        viewModelScope.launch {
            if (allCategoryStr.contains(categoryName).not()) {
                viewModelScope.launch {
                    saveCategoryUseCase.invoke(CategoryEntity(categoryName = categoryName))
                    refreshAllCategory() // Refresh
                }
            }
        }
    }

    fun updateCategoryName(categoryName: String, newName: String) {
        viewModelScope.launch {
            _allCategory.value?.let { categories ->
                val selected = categories.first { it.categoryName == categoryName }
                updateCategoryNameUseCase.invoke(selected.id!!, newName)
                refreshAllCategory()
            }
        }
    }
}