package com.example.gradientdiary.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.presentation.dateToLocalDate
import com.example.gradientdiary.presentation.ui.Key.DIARY_ARGS_KEY
import com.example.gradientdiary.presentation.ui.home.DiaryScreen
import com.example.gradientdiary.presentation.ui.write.WriteScreen
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun DiaryAppNavHost(
    writeViewModel: WriteViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = DiaryAppScreen.Home.name
    ) {
        val handleClickCalendarColumn = { date: String ->
            navController.navigate("${DiaryAppScreen.Write.name}/${date}") {
                popUpTo(DiaryAppScreen.Home.name)
            }
        }
        val handleClickAddDiaryButton = {
            navController.navigate(DiaryAppScreen.Write.name) {
                popUpTo(DiaryAppScreen.Home.name)
            }
        }
        val handleBackButtonClick = {
            navController.navigateUp()
        }

        composable(DiaryAppScreen.Home.name) {
            DiaryScreen(
                // memoViewModel = memoViewModel,
                handleClickAddDiaryButton = handleClickAddDiaryButton,
                handleClickCalendarColumn = handleClickCalendarColumn,
            )
        }
        composable(
            route = "${DiaryAppScreen.Write.name}/{${DIARY_ARGS_KEY}}",
            arguments = listOf(navArgument(DIARY_ARGS_KEY) {
                type = NavType.StringType
            })
        ) { entry ->
            val date = entry.arguments?.getString(DIARY_ARGS_KEY) ?: ""
            Timber.e("host getDiaryByDate $date")
            writeViewModel.getDiaryByDate(dateToLocalDate(date))
            val content = writeViewModel.diary.collectAsState().value
            val contentBlockViewModel = remember {
                mutableStateOf(ContentBlockViewModel(content?.let { it.contents } ?: emptyList()))
            }

            WriteScreen(
                date,
                content,
                writeViewModel,
                contentBlockViewModel.value,
                handleBackButtonClick = { handleBackButtonClick() }
            )
        }
    }
}

object Key {
    const val DIARY_ARGS_KEY = "date"
}
