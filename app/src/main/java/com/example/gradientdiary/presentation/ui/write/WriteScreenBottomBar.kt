package com.example.gradientdiary.presentation.ui.write

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Grey70


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WriteScreenBottomBar(
    handleAddImage: (Uri?) -> Unit,
    handleSaveDiary: () -> Unit,
    modifier: Modifier = Modifier
) {
    Divider(
        color = Grey70,
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconModifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)

        AddImageButton(
            iconModifier = iconModifier,
            handleAddImage = handleAddImage,
        )

        Icon(
            painterResource(id = R.drawable.baseline_format_align_center_24),

            modifier = iconModifier.clickable {
            },
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painterResource(id = R.drawable.baseline_check_24),
            modifier = iconModifier.clickable {
                handleSaveDiary()
            },
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBar(){
    GradientDiaryTheme {
        WriteScreenBottomBar({},{})
    }
}