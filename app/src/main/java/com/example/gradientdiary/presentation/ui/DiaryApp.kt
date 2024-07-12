package com.example.gradientdiary.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel
import com.example.gradientdiary.presentation.viewModel.ListViewViewModel
import com.example.gradientdiary.presentation.viewModel.SearchViewModel
import com.example.gradientdiary.presentation.viewModel.SettingViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel

val localSnackBarManager = compositionLocalOf<SnackBarManager> {
    error("No SnackbarManager provided")
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryApp(
    writeViewModel: WriteViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    listViewViewModel: ListViewViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val snackBarManager = remember { SnackBarManager() }

    CompositionLocalProvider(localSnackBarManager provides snackBarManager) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues()),
            topBar = {
                SnackbarHost(hostState = snackBarManager.snackbarHostState)
            }
        ) {
            DiaryAppNavHost(
                writeViewModel = writeViewModel,
                categoryViewModel = categoryViewModel,
                searchViewModel = searchViewModel,
                listViewViewModel = listViewViewModel,
                settingViewModel = settingViewModel,
                navController = navController
            )
        }
    }
}