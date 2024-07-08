package com.example.gradientdiary.presentation.ui.component

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gradientdiary.R
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.presentation.PersistedPermissionsCheck
import com.example.gradientdiary.presentation.dateStringFormatter
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Job
import timber.log.Timber


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryCardView(
    diary: DiaryEntity,
    handleDelete: (Long) -> Job,
    handleCardClick: (Long) -> Unit
) {
    val (imageBlocks, textBlocks) = diary.contents.partition { it.content.startsWith("content://") }
    val textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText)
    val cornerShape = RoundedCornerShape(Dimens.dp8)
    var deleteIconShow by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Timber.e("diary textblocks : ${textBlocks.joinToString(",") { it.content }}")
//todo delete icon 로직 / wrtie screen navigation 연결

    Column(
        Modifier
            .padding(horizontal = Dimens.dp10, vertical = Dimens.dp16) //외부패딩
            .shadow(elevation = 1.dp, shape = cornerShape)
            .background(color = Color.White, shape = cornerShape)
            .padding(Dimens.dp10) //내부 패딩
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onLongClick = { // diary 삭제 아이콘 show
                    deleteIconShow = true
                },
                onClick = { // 다이어리 write screen 으로 이동
                    handleCardClick(diary.id!!)
                })
    ) {


        val outputDateString = dateStringFormatter(diary.updateDate)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = outputDateString, style = textStyle.copy(fontSize = 20.sp))
            if (deleteIconShow) {
                Icon(
                    painterResource(id = R.drawable.delete_2_svgrepo_com),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            handleDelete(diary.id!!)
                        },
                    contentDescription = "delete"
                )
            }
        }
        Text(diary.title, style = textStyle)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = Dimens.dp150),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.dp10),
            horizontalArrangement = Arrangement.spacedBy(Dimens.dp5),
            verticalArrangement = Arrangement.spacedBy(Dimens.dp5)
        ) {
            items(imageBlocks) { block ->
                val content = Uri.parse(block.content)
                PersistedPermissionsCheck(content,
                    havePermission = {
                        GlideImage(
                            imageModel = content,
                            modifier = Modifier
                                .size(Dimens.dp150)
                                .clip(cornerShape),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    },
                    notFound = {}
                )
            }
        }
        textBlocks.forEach {
            Text(it.content, style = textStyle)
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFEEEEEE
)
@Composable
fun PreviewDairyCardView() {
    val iconModifier = Modifier
        .size(40.dp)
        .padding(horizontal = 8.dp, vertical = 8.dp)

    GradientDiaryTheme {
        val textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText)
        val cornerShape = RoundedCornerShape(Dimens.dp8)

        Column(
            Modifier
                .padding(Dimens.dp10)
                .shadow(elevation = 2.dp, shape = cornerShape)
                .background(color = Color.White, shape = cornerShape)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "20200115", style = textStyle)
                Icon(
                    painterResource(id = R.drawable.delete_2_svgrepo_com),
                    modifier = iconModifier.clickable {

                    },
                    contentDescription = "delete"
                )
            }
            Text("제목자리", style = textStyle)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = Dimens.dp150),
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp5),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp5)
            ) {
                items(3) {
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimens.dp8)),
                        painter = painterResource(id = R.drawable.test_image),
                        contentDescription = ""
                    )
                }
            }
            /*    Row(
                Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 250.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 1..3) {
                    Image(
                        modifier =Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(Dimens.dp8)),
                        painter = painterResource(id = R.drawable.test_image),
                        contentDescription = ""
                    )
                }
            }*/
            for (i in 1..3) {
                Text("내용ㅇ내용ㅇ~~", style = textStyle)
            }

        }

    }
}


