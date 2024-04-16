package com.example.gradientdiary.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryApp() {
    val navController = rememberNavController()
    GradientDiaryTheme {
        Scaffold() {
            DiaryAppNavHost(navController = navController)
        }
    }
}