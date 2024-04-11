package com.example.gradientdiary.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings

@Composable
fun WriteScreen() {

    var text by remember { mutableStateOf("") }

    Column(
        //modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {

        BasicTextField(
            value = text ?: "",
            onValueChange = { newText ->
                text = newText
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.labelSmall,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(top = Paddings.small)
                        /*.border(
                            width = 1.dp,
                            color = Grey100,
                            shape = RoundedCornerShape(size = 5.dp)
                        )*/
                        .background(Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(
                            horizontal = Paddings.xlarge,
                            vertical = Paddings.medium
                        ), // inner padding
                ) {
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewWriteScreen() {
    GradientDiaryTheme {
        WriteScreen()
    }
}