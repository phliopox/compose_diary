package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BasicButton(
    text: String,
    color: Color? = null,
    modifier: Modifier? = null,
    onclick: () -> Unit
) {
    val containerColor = color ?: Color.Black
    val btnModifier = modifier ?: Modifier.fillMaxWidth()
    Button(
        onClick = {
            onclick()
        },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ), modifier = btnModifier
    ) {
        Text(
            text, color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}