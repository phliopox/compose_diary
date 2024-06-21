package com.example.gradientdiary.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Grey70
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel


@Composable
fun EditCategoryDialog(categoryViewModel: CategoryViewModel,onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            EditCategoryDialogContent(categoryViewModel)
        }
    }
}

@Composable
private fun EditCategoryDialogContent(
    categoryViewModel: CategoryViewModel
) {
  //  val allCate = categoryViewModel.allCategoryStr
    val allCate by categoryViewModel.allCategory.collectAsState(initial = emptyList())

    LazyColumn(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Column {


                Text(
                    "카테고리",
                    style = MaterialTheme.typography.displayMedium.copy(color = DefaultText)
                )

                Divider(
                    color = Grey70,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                )
            }
        }
        items(allCate ?: emptyList())  {
            Text(it.categoryName)
        }
    }
}