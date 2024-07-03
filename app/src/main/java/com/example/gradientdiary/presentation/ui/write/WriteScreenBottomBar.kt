package com.example.gradientdiary.presentation.ui.write

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Grey70
import com.example.gradientdiary.presentation.ui.component.BasicButton
import kotlinx.coroutines.Job


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WriteScreenBottomBar(
    handleAddImage: (Uri?) -> Unit,
    handleDeleteDiary: () -> Unit,
    handleTextAlignment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var alignmentStatus by remember { mutableStateOf("start") }
    var alignmentIconId by remember { mutableIntStateOf(R.drawable.baseline_format_align_center_24) }
    LaunchedEffect(key1 = alignmentStatus) {
        alignmentIconId = when (alignmentStatus) {
            "start" -> R.drawable.baseline_format_align_center_24
            "center" -> R.drawable.baseline_format_align_right_24
            else -> R.drawable.baseline_format_align_left_24
        }
    }
    Divider(
        color = Grey70,
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconModifier = Modifier
            .size(40.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)

        AddImageButton(
            iconModifier = iconModifier,
            handleAddImage = handleAddImage,
        )
        IconButton(onClick = {
            alignmentStatus = when (alignmentStatus) {
                "start" -> "center"
                "center" -> "end"
                else -> "start"
            }
            handleTextAlignment(alignmentStatus)
        }) {
            Icon(
                painterResource(id = alignmentIconId),
                contentDescription = "align"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {
            handleDeleteDiary()
        }) {
            Icon(
                modifier= iconModifier,
                painter = painterResource(id = R.drawable.delete_2_svgrepo_com),
                contentDescription = "delete"
            )
        }

      /*  IconButton(onClick = {
            handleSaveDiary()
        }) {
            Icon(
                painterResource(id = R.drawable.baseline_check_24),
                modifier = iconModifier.clickable {

                },
                contentDescription = "save"
            )
        }*/
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    GradientDiaryTheme {
        WriteScreenBottomBar({}, {})
    }

}
*/




