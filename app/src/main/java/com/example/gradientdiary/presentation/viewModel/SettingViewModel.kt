package com.example.gradientdiary.presentation.viewModel


import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradientdiary.R
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.data.storage.getFontResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val storage: SharedPrefsStorageProvider
) : ViewModel() {
    private val _selectedFont = MutableStateFlow("restart")
    val selectedFont: StateFlow<String> = _selectedFont.asStateFlow()


    private val _previewTextStyle =
        MutableStateFlow(TextStyle(fontFamily = FontFamily(Font(R.font.restart))))
    val previewTextStyle: StateFlow<TextStyle> = _previewTextStyle.asStateFlow()

    init {
        loadSelectedFont()
    }

    fun updateFontSelection(font: String) {
        viewModelScope.launch {
            _selectedFont.value = font
            _previewTextStyle.value =
                TextStyle(fontFamily = FontFamily(Font(getFontResource(font))))
            storage.saveFontSelection(font)
        }
    }

    fun applyFontSelection() {
        viewModelScope.launch {
            storage.saveFontSelection(_selectedFont.value)
        }
    }

    private fun loadSelectedFont() {
        viewModelScope.launch {
            val font = storage.getSavedFontSelection()
            _selectedFont.value = font
            _previewTextStyle.value =
                TextStyle(fontFamily = FontFamily(Font(getFontResource(font))))
        }
    }
}