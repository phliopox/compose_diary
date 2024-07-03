package com.example.gradientdiary.presentation.ui.listview

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.theme.Grey100
import com.example.gradientdiary.presentation.ui.component.CategorySpinner
import com.example.gradientdiary.presentation.ui.component.DiaryCardView
import com.example.gradientdiary.presentation.ui.search.ResultNotFoundedScreen
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel
import com.example.gradientdiary.presentation.viewModel.ListViewViewModel
import com.example.gradientdiary.presentation.viewModel.SearchViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ListViewScreen(
    listViewViewModel: ListViewViewModel,
    categoryViewModel: CategoryViewModel,
    handleDiaryCardClick: (Long) -> Unit,
    handleBackButtonClick: () -> Unit
) {

    val handleDeleteDiary = { id: Long ->

    }
    var categorySpinnerExpanded by remember { mutableStateOf(false) }
    val allCategory = stringResource(id = R.string.category)
    var category by remember { mutableStateOf(allCategory) } // 카테고리 필터 선택값
    val result by listViewViewModel.listResult.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(category) {
        CoroutineScope(Dispatchers.IO).launch {
            val categoryId = categoryViewModel.getCategoryId(category)
            listViewViewModel.refreshAction(categoryId)
        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.dp20)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.dp16), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .size(width = Dimens.dp30, height = Dimens.dp40)
                    .padding(Dimens.dp8)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        handleBackButtonClick()
                    },
                painter = painterResource(id = R.drawable.ic_back_left),
                contentDescription = ""
            )
            Box() {
                Row(
                    modifier = Modifier
                        .width(130.dp)
                        .height(Dimens.dp40)
                        .padding(start = Dimens.dp10)
                        .clip(RoundedCornerShape(Dimens.dp5))
                        .border(Dimens.dp1, Grey100, RoundedCornerShape(Dimens.dp5))
                        .clickable {
                            categorySpinnerExpanded = !categorySpinnerExpanded
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        category,
                        style = MaterialTheme.typography.titleLarge.copy(color = DefaultText),
                        modifier = Modifier.padding(horizontal = Dimens.dp10)
                    )
                    Icon(
                        modifier = Modifier
                            .size(Dimens.dp30)
                            .padding(Dimens.dp8),
                        painter = painterResource(id = R.drawable.chevron_down_svgrepo_com),
                        contentDescription = null
                    )
                }
                // 카테고리 드롭다운
                CategorySpinner(
                    categorySpinnerExpanded = categorySpinnerExpanded,
                    categoryViewModel = categoryViewModel,
                    settingMenu = false,
                    dismiss = { it ->
                        categorySpinnerExpanded = false
                        category = if (it) {
                            allCategory
                        } else {
                            categoryViewModel.selectedCategory.value
                        }
                    }
                )
            }
        }

        result?.forEach {
            DiaryCardView(
                it,
                handleCardClick = handleDiaryCardClick,
                handleDelete = handleDeleteDiary
            )
        }
    }
}