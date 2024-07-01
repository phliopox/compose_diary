package com.example.gradientdiary.presentation.ui.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import timber.log.Timber


//contentblock 으로 하지않고 직접 구현하는게 나을것같다 cardview에서는 내용 수정 불가 !

@Composable
fun DiaryCardView(diary: DiaryEntity) {
    val (imageBlocks, textBlocks) = diary.contents.partition { it.content.startsWith("content://") }
    val textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText)
    val cornerShape = RoundedCornerShape(Dimens.dp8)
    Timber.e("diary textblocks : ${textBlocks.joinToString(",") { it.content }}")
    Column(
        Modifier
            .padding(horizontal = Dimens.dp10, vertical = Dimens.dp16) //외부패딩
            .shadow(elevation = 2.dp, shape = cornerShape)
            .background(color = Color.White, shape = cornerShape)
            .padding(Dimens.dp10) //내부 패딩
    ) {


        val outputDateString = dateStringFormatter(diary.updateDate)

        Text(text = outputDateString, style = textStyle.copy(fontSize = 20.sp))
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
    GradientDiaryTheme {
        val textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText)
        val cornerShape = RoundedCornerShape(Dimens.dp8)

        Column(
            Modifier
                .padding(Dimens.dp10)
                .shadow(elevation = 2.dp, shape = cornerShape)
                .background(color = Color.White, shape = cornerShape)
        ) {


            Text(text = "20200115", style = textStyle)
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


