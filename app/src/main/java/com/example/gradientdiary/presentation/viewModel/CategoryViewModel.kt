package com.example.gradientdiary.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.domain.DeleteCategoryUseCase
import com.example.gradientdiary.domain.GetCategoryUseCase
import com.example.gradientdiary.domain.SaveCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getCategoryIdUseCase: GetCategoryUseCase,
    private val storage: SharedPrefsStorageProvider
) : ViewModel() {

    private var _savedCategory = MutableStateFlow<List<CategoryEntity>?>(emptyList())
    val savedCategory: StateFlow<List<CategoryEntity>?> = _savedCategory

    private val _allCategory = mutableStateListOf<CategoryEntity>()
    val allCategory: List<CategoryEntity> get() = _allCategory

    var selectedCategory = MutableStateFlow("일기")

    init {
        viewModelScope.launch {
            refreshAllCategory()
            val currentStorageCategory = storage.getCurrentCategory()

            //pref에 담긴 카테고리가 db에 없는 카테고리일 시 pref seletedCategory 업데이트
            val exist =
                _savedCategory.value?.any { it.categoryName == currentStorageCategory } ?: false
            selectedCategory.value = if (exist) {
                storage.getCurrentCategory()
            } else {
                val refreshValue = _savedCategory.value?.get(0)?.categoryName ?: "일기"
                storage.saveSelectedCategory(refreshValue)
                refreshValue
            }
        }
    }
     fun getCategoryName(id : Long) : String{
       return getCategoryIdUseCase.invoke(id)
    }
     fun getCategoryId(name: String? = null): Long {
        val categoryName = name ?: selectedCategory.value
        val id = getCategoryIdUseCase.invoke(categoryName)
        //Timber.e("CATEGORY ID / NAME ${categoryName} , ${id}")
        return id
    }

    fun addAllCateStrList() {
        _allCategory.add(CategoryEntity(null, ""))
        //Timber.e("addAllcate allcategory : ${allCategory.joinToString(",") { it.toString() }}")
    }

    suspend fun selectedCategoryChange(category: String) {
        storage.saveSelectedCategory(category)
        selectedCategory.value = category
    }

    private suspend fun refreshAllCategory() {
        val categories = getCategoryIdUseCase.invoke().firstOrNull() ?: emptyList()
        _savedCategory.value = categories.map { it.copy() } // Deep copy
        _allCategory.clear()
        _allCategory.addAll(categories.map { it.copy() }) // Deep copy
       // selectedCategory.value = _savedCategory.value?.get(0)?.categoryName ?: "일기" //CalendarScreen 에서 참조할 category 업데이트
        //Timber.e("allCategory ${_allCategory.joinToString(separator = ", ") { it.toString() }}")
        val categoryName = if (!_savedCategory.value.isNullOrEmpty()) {
            _savedCategory.value?.get(0)?.categoryName ?: "일기"
        } else {
            "일기"
        }
        selectedCategory.value = categoryName
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
                        saveCategoryUseCase.invoke(it.id, it.categoryName)
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