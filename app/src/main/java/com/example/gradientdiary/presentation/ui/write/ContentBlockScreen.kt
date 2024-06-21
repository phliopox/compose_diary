package com.example.gradientdiary.presentation.ui.write

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import timber.log.Timber

@Composable
fun ColumnScope.ContentBlockScreen(
    contentBlockViewModel: ContentBlockViewModel,
    contents: List<ContentBlock<*>>,
) {
    val handleAddImageBlock = { uri: Uri? ->
        uri?.let {
            contentBlockViewModel.changeToImageBlock(it)
        }
        Unit
    }
    val handleAddTextBlock = {
        contentBlockViewModel.insertTextBlock()
    }
    val handleDeleteBlock = { block: ContentBlock<*> ->
        contentBlockViewModel.deleteBlock(block)
    }

    val handleFocusedIndex = { index: Int ->
        contentBlockViewModel.focusedBlock(index = index)
    }

    Column(Modifier.weight(0.1f)) {
        ContentBlocks(
            contents = contents,
            handleDeleteBlock = handleDeleteBlock,
            handleFocusedIndex = handleFocusedIndex,
            contentBlockViewModel = contentBlockViewModel,
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        WriteScreenBottomBar(
            handleAddImage = handleAddImageBlock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun ContentBlocks(
    contents: List<ContentBlock<*>>,
    handleDeleteBlock: (ContentBlock<*>) -> Unit,
    handleFocusedIndex: (Int) -> Unit,
    contentBlockViewModel: ContentBlockViewModel
) {

    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(true) }

    // 컬럼 클릭시 focus in/out
    LaunchedEffect(isFocused) {
        if (isFocused) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) // 기본 클릭 효과 제거
            {
                isFocused = !isFocused
            }
    ) {
        var imageFocused by remember { mutableStateOf(false) }
        contents.forEachIndexed { index, content ->
            val isImage = content.content
                .toString()
                .startsWith("content://")

            val modifier = Modifier
                .focusTarget()
                .onFocusChanged {
                    if (it.isFocused) {
                        //Timber.e("Focused block content: ${content.toString()}")
                        handleFocusedIndex(index)
                    } else {
                        // Timber.e("Focus lost from block content: ${content.toString()}")
                    }

                    if (isImage) {
                        imageFocused = it.isFocused
                    }
                }
                .onPreviewKeyEvent {
                    if (it.key.nativeKeyCode == Key.Backspace.nativeKeyCode) {
                        Timber.e("click backspace ${content.content}")

                        if (content.isEmpty() || isImage) {
                            handleDeleteBlock(content)
                            focusManager.moveFocus(FocusDirection.Up)
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                }
                .focusRequester(focusRequester)

            LaunchedEffect(isFocused, index) { // 최초 로딩시 , 마지막 block 에 포커스 주기
                if (isFocused && index == contents.lastIndex) {
                    focusRequester.requestFocus()
                }
            }
            content.DrawEditableContent(modifier = modifier, viewModel = contentBlockViewModel)
            if (isImage && index == contents.lastIndex) { // 이미지 삭제를 위해서 마지막 item이 image일 경우 빈 text line 추가
                Timber.e("image empty textblock add")
                content.addNextBlock(contentBlockViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWriteScreen2() {
    GradientDiaryTheme {
        Column {
            val sampleContents = emptyList<ContentBlock<*>>()
            ContentBlockScreen(
                ContentBlockViewModel(null),
                sampleContents
            )
        }
    }
}