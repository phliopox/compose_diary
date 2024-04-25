package com.example.gradientdiary.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gradientdiary.presentation.dateToLocalDate
import com.example.gradientdiary.presentation.ui.Key.DIARY_ARGS_KEY
import com.example.gradientdiary.presentation.ui.home.DiaryScreen
import com.example.gradientdiary.presentation.ui.write.WriteScreen
import com.example.gradientdiary.presentation.viewModel.ContentViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel
import timber.log.Timber
import java.time.LocalDate

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

        composable(DiaryAppScreen.Home.name) {
            DiaryScreen(
                // memoViewModel = memoViewModel,
                handleClickAddDiaryButton = handleClickAddDiaryButton,
                handleClickCalendarColumn = handleClickCalendarColumn,
                //tagViewModel = tagViewModel
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

            WriteScreen(
                writeViewModel
            )
        }
    }
}

object Key {
    const val DIARY_ARGS_KEY = "date"
}
