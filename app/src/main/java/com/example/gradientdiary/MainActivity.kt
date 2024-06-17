package com.example.gradientdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.presentation.ui.DiaryApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //LifeCycleChecker().onCreate()
        setContent {
            DiaryApp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = SharedPrefsStorageProvider(this)
        pref.clearCurrentYearAndMonth()
    }
}