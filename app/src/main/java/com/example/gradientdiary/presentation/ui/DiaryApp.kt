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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.data.storage.getFontResource
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.getTypography
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

    /*//font
    val context = LocalContext.current
    val storage = SharedPrefsStorageProvider(context)
    val savedFont by storage.currentFont.collectAsState(initial = "restart")
    val typography = getTypography(FontFamily(Font(getFontResource(savedFont))))*/

   // GradientDiaryTheme(typography = typography) {
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
    //}
}