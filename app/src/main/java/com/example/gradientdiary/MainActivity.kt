package com.example.gradientdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.data.storage.getFontResource
import com.example.gradientdiary.presentation.setLocale
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.getTypography
import com.example.gradientdiary.presentation.ui.DiaryApp
import com.example.gradientdiary.presentation.ui.component.LoadingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val storage = SharedPrefsStorageProvider(this)
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
        val pref = SharedPrefsStorageProvider(this)
        pref.clearCurrentYearAndMonth()
    }
}