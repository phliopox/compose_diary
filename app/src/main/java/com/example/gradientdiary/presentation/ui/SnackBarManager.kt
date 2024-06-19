package com.example.gradientdiary.presentation.ui

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SnackBarManager {
    val snackbarHostState = SnackbarHostState()
    val scope = CoroutineScope(Dispatchers.Main)

    fun showMessage(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}