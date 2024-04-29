package com.example.gradientdiary.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.viewModel.WriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryApp(
    writeViewModel: WriteViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    GradientDiaryTheme {
        Scaffold() {
            DiaryAppNavHost(
                writeViewModel = writeViewModel,
                navController = navController
            )
        }
    }
}