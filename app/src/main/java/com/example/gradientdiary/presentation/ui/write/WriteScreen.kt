package com.example.gradientdiary.presentation.ui.write

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen() {

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
           // .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(50.dp))
            Text(
                "2024년 4월 11일",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "제목",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(
            Modifier
                .weight(1f)
                .padding(top = 10.dp)
        ) {

            /* TextField(
                 value = text,
                 modifier = Modifier.fillMaxSize(),
                 textStyle = MaterialTheme.typography.titleMedium.copy(DefaultText),
                 onValueChange = { newText -> text = newText })*/
               BasicTextField(
                   value = text,
                   onValueChange = { newText ->
                       text = newText
                   },
                   textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText),
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(
                           horizontal = Paddings.extra,
                           vertical = Paddings.large
                       ),
                  /* decorationBox = { innerTextField ->
                       Box(
                           modifier = Modifier
                               .background(Color.White)
                       ) {
                           innerTextField()
                       }
                   }*/
               )
        }

        Row(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWriteScreen() {
    GradientDiaryTheme {
        WriteScreen()
    }
}