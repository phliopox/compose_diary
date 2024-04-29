package com.example.gradientdiary.presentation.ui.write



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.data.DiaryModel
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Grey70
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WriteScreen(
    date: String,
    content : DiaryEntity?=null,
    writeViewModel: WriteViewModel,
    contentBlockViewModel: ContentBlockViewModel
    ) {
    var contentBlockEntityList = mutableListOf<ContentBlockEntity>()
    val contentsState by remember { mutableStateOf(contentBlockViewModel.contentBlocks)}
    val memo = writeViewModel.diary.collectAsState()
    // var text by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val pref = SharedPrefsStorageProvider(context)
    val category = pref.get()  // 현재 선택된 category , 스토리지에 없을시 "일기" 로 반환된다.

    val inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault())
    val outputFormat = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
    val formatDate: LocalDate = LocalDate.parse(date, inputFormat)!!
    val outputDateString: String = outputFormat.format(formatDate)

    val handleSaveDiary = {
        val newMemoModel = content?.let {
            it.copy().convertToDiaryModel().apply {
                contents = contentsState.value
            }
        } ?: DiaryModel(contents = contentsState.value, category = category , updateDate = date)

        val contentsCount = contentsState.value.count {
            it.content.toString().isNotBlank() or it.content.toString().isNotEmpty()
        }
        if (contentsCount > 0) {
            writeViewModel.saveDiary(diaryModel = newMemoModel)
        }

    }
    val handleAddImage ={

    }
    WriteScreenContent(
        outputDateString,
        "category",
        handleSaveDiary,
        handleAddImage
    )
}

@Composable
private fun WriteScreenContent(
    outputDateString: String,
    text: String,
    handleSaveDiary:() -> Unit,
    handleAddImage:()-> Unit
) {
    var text1 = text
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(50.dp))
            Text(
                outputDateString,
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
            BasicTextField(
                value = text1,
                onValueChange = { newText ->
                    text1 = newText
                },
                textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = Paddings.extra,
                        vertical = Paddings.large
                    ),
            )
        }
        Divider(
            color = Grey70,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconModifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp)

            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                modifier = iconModifier.clickable {
                    handleAddImage()
                },
                contentDescription = null
            )
            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
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
}


@Preview(showBackground = true)
@Composable
fun PreviewWriteScreen() {
    GradientDiaryTheme {
        // WriteScreen(WriteViewModel())
    }
}