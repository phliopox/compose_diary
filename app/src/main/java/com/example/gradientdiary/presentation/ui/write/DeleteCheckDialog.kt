package com.example.gradientdiary.presentation.ui.write

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.ui.component.BasicButton

@Composable
fun DeleteCheckDialog(
    onDismiss: () -> Unit, handleDeleteDiary: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                Modifier
                    .defaultMinSize(minWidth = 100.dp, minHeight = 140.dp)
                    .background(Color.White)
                    .fillMaxWidth(0.5f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "다이어리를 삭제하시겠습니까?",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 25.sp,
                        color = DefaultText
                    )
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    BasicButton("삭제", modifier = Modifier.weight(1f)) {
                        handleDeleteDiary()
                        onDismiss()
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                    BasicButton("취소", modifier = Modifier.weight(1f)) {
                        onDismiss()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDeleteDialog() {
    GradientDiaryTheme {
        DeleteCheckDialog({}, {})
    }

}