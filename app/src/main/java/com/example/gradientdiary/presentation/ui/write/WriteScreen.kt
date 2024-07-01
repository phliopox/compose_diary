package com.example.gradientdiary.presentation.ui.write


import android.annotation.SuppressLint
import android.view.ViewTreeObserver
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.gradientdiary.R
import com.example.gradientdiary.data.DiaryModel
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.presentation.dateStringFormatter
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.ui.component.EditableText
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import com.example.gradientdiary.presentation.viewModel.WriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WriteScreen(
    date: String,
    content: DiaryEntity? = null,
    writeViewModel: WriteViewModel,
    categoryViewModel: CategoryViewModel,
    contentBlockViewModel: ContentBlockViewModel,
    handleBackButtonClick: () -> Unit
) {
    val contentsState by remember { mutableStateOf(contentBlockViewModel.contentBlocks) }
    val outputDateString = dateStringFormatter(date)
    val contentValue = contentsState.collectAsState()

    val isKeyboardOpen by keyboardAsState()
    val focusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle
    var previousKeyboardState by remember { mutableStateOf(false) }

    var deleteCheckShow by remember { mutableStateOf(false) }
    var isDeleteInProgress by remember { mutableStateOf(false) }



    val handleSaveDiary = {
        CoroutineScope(Dispatchers.IO).launch {
            val categoryId = categoryViewModel.getCategoryId()
            val finalBlockList =
                contentBlockViewModel.removeNotFoundBlocks() // 파일을 찾을 수 없는 이미지 블럭 삭제한 list
            val newMemoModel = content?.let {
                it.copy().convertToDiaryModel().apply {
                    title = contentBlockViewModel.title
                    contents = finalBlockList
                }
            } ?: DiaryModel(
                contents = finalBlockList,
                categoryId = categoryId,
                title = contentBlockViewModel.title,
                updateDate = date
            )

            val contentsCount = finalBlockList.count {
                it.content.toString().isNotBlank() or it.content.toString().isNotEmpty()
            }

            if (contentsCount > 0) {
                Timber.e("save 예정$newMemoModel")
                writeViewModel.saveDiary(diaryModel = newMemoModel)
            }
        }
    }

    // 앱 백그라운드 전환시에도 save (이미지 첨부를 위한 백그라운드 전환시 데이터 사라짐 방지)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    if (!isDeleteInProgress) {
                        handleSaveDiary()
                    }
                }

                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    val handleBackClickSave = {
        handleSaveDiary()
        handleBackButtonClick()
    }

    BackHandler {
        // back 버튼 클릭시 save
        if (!isDeleteInProgress) {
            handleBackClickSave()
        }
    }

    val handleDeleteDiary = {
        //재차 다이어리 삭제를 확인하는 다이얼로그를 띄운다
        deleteCheckShow = !deleteCheckShow
    }

    //사용자가 키보드를 직접 내릴경우 ,
    // focus clear 를 해줘야 정상적인 backHandler 가 동작하기 때문에 clear 해준다.
    LaunchedEffect(isKeyboardOpen) {
        if (previousKeyboardState && !isKeyboardOpen) {
            focusManager.clearFocus()
        }
        previousKeyboardState = isKeyboardOpen
    }

    Box(contentAlignment = Alignment.Center) {
        WriteScreenContent(
            outputDateString,
            contentValue.value,
            contentBlockViewModel,
            handleDeleteDiary
        )
        //삭제 아이콘 클릭시 여기까지 타고올라와서 handling
        // dialog 띄우고 재차 확인하는 과정
        if (deleteCheckShow) {
            DeleteCheckDialog({
                deleteCheckShow = false
            }) {//삭제 버튼 클릭
                isDeleteInProgress = true // backHandler 를 통한 save 방지
                CoroutineScope(Dispatchers.IO).launch {
                    writeViewModel.deleteDiary()
                }
                deleteCheckShow = false // dialog 숨기기
                handleBackButtonClick() // navigation.Up
            }
        }
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val view = LocalView.current
    var isImeVisible by remember { mutableStateOf(false) }

    DisposableEffect(LocalWindowInfo.current) {
        val listener = ViewTreeObserver.OnPreDrawListener {
            isImeVisible = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) == true
            true
        }
        view.viewTreeObserver.addOnPreDrawListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnPreDrawListener(listener)
        }
    }
    return rememberUpdatedState(isImeVisible)
}
@Composable
private fun WriteScreenContent(
    outputDateString: String,
    contents: List<ContentBlock<*>>,
    contentBlockViewModel: ContentBlockViewModel,
    handleDeleteDiary: () -> Unit
) {
    val hint = stringResource(R.string.title_hint)
    var diaryTitle by rememberSaveable { mutableStateOf(contentBlockViewModel.title) }


    LaunchedEffect(key1 = diaryTitle) {
        contentBlockViewModel.title = diaryTitle
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
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

            EditableText(
                value = diaryTitle,
                hint = hint,
                style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
            ) {
                diaryTitle = it
            }
        }
        ContentBlockScreen(
            handleDeleteDiary,
            contentBlockViewModel = contentBlockViewModel,
            contents = contents
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWriteScreen() {
    GradientDiaryTheme {
        val date = "20220202"

        /*   WriteScreen(
               date = date,
               contentBlockViewModel = ContentBlockViewModel(emptyList()),
               content = null ,
               writeViewModel = null
           ){}*/
    }
}