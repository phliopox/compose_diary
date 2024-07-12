package com.example.gradientdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.gradientdiary.data.storage.UserDataStoreProvider
import com.example.gradientdiary.data.storage.getFontResource
import com.example.gradientdiary.presentation.setLocale
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.getTypography
import com.example.gradientdiary.presentation.ui.DiaryApp
import com.example.gradientdiary.presentation.ui.component.LoadingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val storage = UserDataStoreProvider(this)
            val savedFont by produceState<String?>(initialValue = null) {
                value = withContext(Dispatchers.IO) {
                    storage.getSavedFontSelection()
                }
            }
            val language by produceState<String?>(initialValue = null) {
                value = withContext(Dispatchers.IO) {
                    storage.language.firstOrNull()
                }
            }
            if (savedFont != null && language != null) {
                setLocale(this, language!!)
                val typography = getTypography(FontFamily(Font(getFontResource(savedFont!!))))
                GradientDiaryTheme(typography = typography) {
                    DiaryApp()
                }
            } else {
                LoadingScreen()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        val pref = UserDataStoreProvider(this)
        pref.clearCurrentYearAndMonth()
    }
}