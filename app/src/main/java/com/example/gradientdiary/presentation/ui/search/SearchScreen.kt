package com.example.gradientdiary.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.ui.component.CustomSearchBar
import com.example.gradientdiary.presentation.viewModel.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    handleBackButtonClick: () -> Unit
) {

    SearchScreenContent(searchViewModel, handleBackButtonClick)
}

@Composable
fun SearchScreenContent(
    searchViewModel: SearchViewModel,
    handleBackButtonClick: () -> Unit
) {
    val searchText by searchViewModel.searchText.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()
    //val result by searchViewModel.result.collectAsState()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.dp20)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
            CustomSearchBar(hint = "키워드를 입력해주세요")

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchContent() {
    GradientDiaryTheme {
        //SearchScreenContent() {}
        CustomSearchBar(hint = "키워드를 입력해주세요")
    }
}