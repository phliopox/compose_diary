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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel


@Composable
fun EditCategoryDialog(categoryViewModel: CategoryViewModel, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            EditCategoryDialogContent(categoryViewModel, onDismiss)
        }
    }
}

@Composable
private fun EditCategoryDialogContent(
    categoryViewModel: CategoryViewModel,
    onDismiss: () -> Unit
) {
    val allCate by categoryViewModel.allCategory.collectAsState(initial = emptyList())
    val iconModifier = Modifier
        .size(35.dp)
        .padding(8.dp)

    LazyColumn(
        Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(vertical = 15.dp)
            .defaultMinSize(minHeight = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 16.dp),
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
                    it.categoryName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.edit_pen_2_line_icon),
                    contentDescription = null,
                    tint = DefaultText
                )
            }
        }
        item {
            Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.padding(top = 16.dp)) {
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
                //todo if changed
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            //todo save
                            onDismiss()
                        },
                    text = "저장",
                    style = MaterialTheme.typography.titleLarge
                )

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
                .padding(horizontal = 16.dp, vertical = 24.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            val iconModifier = Modifier
                .size(35.dp)
                .padding(8.dp)
            LazyColumn(
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
                    .defaultMinSize(minHeight = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 16.dp),
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
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.edit_pen_2_line_icon),
                            contentDescription = null,
                            tint = DefaultText
                        )
                    }
                }
                item {
                    Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.padding(top = 16.dp)) {
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
                                .padding(end = 16.dp)
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