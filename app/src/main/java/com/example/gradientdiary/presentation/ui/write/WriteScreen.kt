package com.example.gradientdiary.presentation.ui.write



import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.data.DiaryModel
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WriteScreen(
    date: String,
    content : DiaryEntity?=null,
    writeViewModel: WriteViewModel,
    contentBlockViewModel: ContentBlockViewModel
    ) {
    //var contentBlockEntityList = mutableListOf<ContentBlockEntity>()
    val contentsState by remember { mutableStateOf(contentBlockViewModel.contentBlocks)}
    val memo = writeViewModel.diary.collectAsState()
    // var text by rememberSaveable { mutableStateOf("") }
    Timber.e("WriteScreen contentBlocks: ${contentsState.value} ")


    val inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault())
    val outputFormat = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
    val formatDate: LocalDate = LocalDate.parse(date, inputFormat)!!
    val outputDateString: String = outputFormat.format(formatDate)
    val handleSaveDiary = {
        val newMemoModel = content?.let {
            it.copy().convertToDiaryModel().apply {
                contents = contentsState.value
            }
        } ?: DiaryModel(contents = contentsState.value, category = writeViewModel.getCategory() , updateDate = date)

        val contentsCount = contentsState.value.count {
            it.content.toString().isNotBlank() or it.content.toString().isNotEmpty()
        }
        if (contentsCount > 0) {
            writeViewModel.saveDiary(diaryModel = newMemoModel)
        }

    }
    val handleAddImage ={

    }

    val contentValue = contentsState.collectAsState()
    WriteScreenContent(
        outputDateString,
        contentValue.value,
        contentBlockViewModel,
        handleSaveDiary,
        //handleAddImage
    )
}

@Composable
private fun WriteScreenContent(
    outputDateString: String,
    contents: List<ContentBlock<*>>,
    contentBlockViewModel: ContentBlockViewModel,
    //handleDeleteMemo: () -> Unit,
    handleSaveDiary:() -> Unit,
) {

    //top 에 삭제버튼 추가 필요
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
           ContentBlockScreen(
               contentBlockViewModel = contentBlockViewModel,
               handleSaveDiary,
               contents = contents
           )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWriteScreen() {
    GradientDiaryTheme {
         //WriteScreen(WriteViewModel())
    }
}