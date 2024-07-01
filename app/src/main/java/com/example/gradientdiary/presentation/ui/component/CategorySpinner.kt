package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun CategorySpinner(
    categorySpinnerExpanded: Boolean,
    categoryViewModel: CategoryViewModel,
    settingMenu: Boolean,
    dismiss: (Boolean) -> Unit
) {
    val allCate by categoryViewModel.savedCategory.collectAsState()
    val textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText)

    DropdownMenu(
        modifier = Modifier.background(Color.White),
        expanded = categorySpinnerExpanded,
        onDismissRequest = { dismiss(false) }
    ) {

        if (!settingMenu) {
            DropdownMenuItem( // 전체선택이 필요한 경우 (검색 페이지)
                text = {
                    Text(
                        stringResource(id = R.string.category),
                        style = textStyle,
                        textAlign = TextAlign.Center
                    )
                },
                onClick = { dismiss(true) })
        }
        allCate?.let {
            it.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            category.categoryName,
                            style = textStyle,
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            categoryViewModel.selectedCategoryChange(category.categoryName)
                            dismiss(false)
                        }
                    })
            }
        }
        if (settingMenu) { // 설정 메뉴가 필요한 경우 (캘린더 페이지)
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(id = R.string.setting),
                        style = textStyle,
                        textAlign = TextAlign.Center
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.size(Dimens.dp20),
                        painter = painterResource(
                            id = R.drawable.settings_svgrepo_com
                        ), contentDescription = "category_setting"
                    )
                },
                onClick = { dismiss(true) })
        }
    }
}
