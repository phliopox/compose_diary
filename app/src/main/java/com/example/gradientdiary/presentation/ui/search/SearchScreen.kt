package com.example.gradientdiary.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.ui.component.CustomSearchBar
import com.example.gradientdiary.presentation.ui.component.DiaryCardView
import com.example.gradientdiary.presentation.viewModel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    handleBackButtonClick: () -> Unit
) {
    val handleClickSearch = {
        CoroutineScope(Dispatchers.IO).launch {
            searchViewModel.searchAction()
        }
    }
    SearchScreenContent(searchViewModel, handleClickSearch, handleBackButtonClick)

}

@Composable
fun SearchScreenContent(
    searchViewModel: SearchViewModel,
    handleClickSearch: () -> Job,
    handleBackButtonClick: () -> Unit
) {
    val searchText by searchViewModel.searchText.collectAsState()
    val result by searchViewModel.searchResult.collectAsState()
    var searchClicked by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.dp20)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.dp16), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(width = Dimens.dp30, height = Dimens.dp40)
                    .padding(Dimens.dp8)
                    .clickable {
                        handleBackButtonClick()
                    },
                painter = painterResource(id = R.drawable.ic_back_left),
                contentDescription = ""
            )
            Spacer(Modifier.padding(Dimens.dp5))
            CustomSearchBar(
                hint = stringResource(id = R.string.search_hint),
                onSearchClicked = {
                    //search 전달
                    Timber.e("search Term : $searchText")
                    searchClicked = true
                    handleClickSearch()
                },
                onTextChange = searchViewModel::onSearchTextChange
            )
        }

        result?.forEach {
            DiaryCardView(it)
        } ?: if (searchClicked) {
            ResultNotFoundedScreen()
        } else {
            //빈화면
        }

    }
}

@Composable
fun ResultNotFoundedScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.result_not_founded), style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchContent() {
    GradientDiaryTheme {
        //SearchScreenContent() {}
    }
}