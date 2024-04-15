package com.example.gradientdiary.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun DiaryAppNavHost(
    modifier : Modifier = Modifier,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = DiaryAppScreen.Home.name
    ){
        val handleClickCalendarColumn = {
            navController.navigate(DiaryAppScreen.Write.name) {
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
    }
}