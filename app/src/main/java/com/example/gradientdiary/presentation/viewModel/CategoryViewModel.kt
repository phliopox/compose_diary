package com.example.gradientdiary.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.domain.DeleteCategoryUseCase
import com.example.gradientdiary.domain.GetAllCategoryUseCase
import com.example.gradientdiary.domain.SaveCategoryUseCase
import com.example.gradientdiary.domain.UpdateCategoryNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var _savedCategory = MutableStateFlow<List<CategoryEntity>?>(emptyList())
    val savedCategory: StateFlow<List<CategoryEntity>?> = _savedCategory

    private val _allCategory = mutableStateListOf<CategoryEntity>()
    val allCategory: List<CategoryEntity> get() = _allCategory

    var selectedCategory = ""

    init {
        viewModelScope.launch {
            selectedCategory = storage.getCurrentCategory()
            refreshAllCategory()
        }
    }

    fun addAllCateStrList() {
        _allCategory.add(CategoryEntity(null, ""))
        //Timber.e("addAllcate allcategory : ${allCategory.joinToString(",") { it.toString() }}")
    }

    suspend fun selectedCategoryChange(category: String) {
        storage.saveSelectedCategory(category)
        selectedCategory = category
    }

    private suspend fun refreshAllCategory() {
        val categories = getAllCategoryUseCase.invoke().firstOrNull() ?: emptyList()
        _savedCategory.value = categories.map { it.copy() } // Deep copy
        _allCategory.clear()
        _allCategory.addAll(categories.map { it.copy() }) // Deep copy

        //Timber.e("allCategory ${_allCategory.joinToString(separator = ", ") { it.toString() }}")
    }

    fun deleteCategory(categoryName: String) {
        viewModelScope.launch {
            val categories = _savedCategory.value ?: return@launch
            val selectedCategory = categories.filter { it.categoryName == categoryName }
            if (selectedCategory.isEmpty()) {
                deleteCategoryUseCase.invoke(selectedCategory.first())
            }
            refreshAllCategory()
        }
    }

    fun updateCategory(categories: List<CategoryEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categories.forEach {
                   // Timber.e("UPDATE CATEGORY ${categories.joinToString(separator = ", ") { it.toString() }}")
                    if (it.id != null) {
                        updateCategoryNameUseCase.invoke(it.id, it.categoryName)
                    } else {
                        saveCategoryUseCase.invoke(CategoryEntity(categoryName = it.categoryName))
                    }
                }
                storage.saveSelectedCategory(categories[0].categoryName)
                refreshAllCategory() // Refresh
            }
        }
    }
}