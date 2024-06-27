package com.example.gradientdiary.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.ui.component.EditableText
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun EditCategoryDialog(categoryViewModel: CategoryViewModel, onDismiss: () -> Unit) {
    val initialCategory by categoryViewModel.savedCategory.collectAsState()
    val localCategory by remember { derivedStateOf {  categoryViewModel.allCategory}}
    val iconModifier = Modifier
        .size(35.dp)
        .padding(8.dp)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val halfScreenWidth = screenWidth / 2

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyColumn(
                Modifier
                    .background(Color.White)
                    .width(halfScreenWidth)
                    .defaultMinSize(minWidth = 250.dp, minHeight = 140.dp)
                    .padding(vertical = Dimens.dp16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.dp10, bottom = Dimens.dp16),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "카테고리",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 25.sp,
                                color = DefaultText
                            )
                        )
                    }
                }
                items(localCategory) {category ->
                    var readOnly by remember { mutableStateOf(category.categoryName.isNotEmpty()) }
                    val focusRequester = remember { FocusRequester() }

                    Row(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .clickable {
                                readOnly = !readOnly
                                if (!readOnly) {
                                    focusRequester.requestFocus()
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        EditableText(
                            readOnly = readOnly,
                            category.categoryName,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.focusRequester(focusRequester)
                        ) {
                            //카테고리 이름 편집
                            category.categoryName =  it
                        }
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.edit_pen_2_line_icon),
                            contentDescription = null,
                            tint = DefaultText
                        )
                    }

                    LaunchedEffect(readOnly) {
                        if (!readOnly) {
                            focusRequester.requestFocus()
                        }
                    }
                }
                item {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.padding(top =Dimens.dp16)
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = iconModifier.clickable {
                                    //새로운 카테고리 추가
                                    categoryViewModel.addAllCateStrList()
                                },
                                painter = painterResource(id = R.drawable.plus_svgrepo_com),
                                contentDescription = null
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(Dimens.dp16)
                                .clickable {
                                    // 카테고리 변경사항 저장
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val initialNames =
                                            initialCategory?.map { it.categoryName } ?: emptyList()
                                        val currentNames = localCategory.map { it.categoryName }

                                        val changed = initialNames.size != currentNames.size ||
                                                initialNames
                                                    .zip(currentNames)
                                                    .any { (x, y) -> x != y }
                                        /*Timber.e("initList ${initialCategory?.joinToString(",") { it.toString() }}")
                                        Timber.e("currentNames ${currentNames.joinToString(separator = ", ") { it.toString() }} ")
                                        Timber.e("changed : $changed")*/
                                        if (changed) {
                                            categoryViewModel.updateCategory(localCategory)
                                        }
                                    }
                                    onDismiss()
                                },
                            text = "저장",
                            style = MaterialTheme.typography.titleLarge
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogContent() {
    GradientDiaryTheme {


        val allCate = listOf("일기", "메모")
        Card(
            modifier = Modifier
                //    .fillMaxWidth(0.5f)
                .wrapContentHeight()
                .padding(horizontal = Dimens.dp16, vertical = 24.dp),
            shape = RoundedCornerShape(Dimens.dp16)
        ) {
            val iconModifier = Modifier
                .size(35.dp)
                .padding(8.dp)
            LazyColumn(
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(vertical =Dimens.dp16)
                    .defaultMinSize(minHeight = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = Dimens.dp16),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "카테고리",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 25.sp,
                                color = DefaultText
                            )
                        )
                    }
                }
                items(allCate ?: emptyList()) {
                    Row(
                        Modifier.fillMaxWidth(0.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            it,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        //Spacer(modifier = Modifier.size(10.dp))
                        Icon(
                            modifier = Modifier.size(Dimens.dp18),
                            painter = painterResource(id = R.drawable.edit_pen_2_line_icon),
                            contentDescription = null,
                            tint = DefaultText
                        )
                    }
                }
                item {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.padding(top = Dimens.dp16)
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = iconModifier,
                                painter = painterResource(id = R.drawable.plus_svgrepo_com),
                                contentDescription = null
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(end =Dimens.dp16)
                                .clickable {
                                },
                            text = "저장",
                            style = MaterialTheme.typography.titleLarge
                        )

                    }
                }
            }

        }
    }
}