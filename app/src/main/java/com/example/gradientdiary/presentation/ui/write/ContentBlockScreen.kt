package com.example.gradientdiary.presentation.ui.write

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    var isFocused by remember { mutableStateOf(false) }

    // 텍스트 필드 포커스 상태에 따라 focus in/out
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
            ) // 클릭 효과 제거
            {
                isFocused = !isFocused
            }
    ) {

        val focusManager = LocalFocusManager.current

        for (i in contents.indices) {

            val content = contents[i]
            val modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        handleFocusedIndex(i)
                    }
                }
                .onPreviewKeyEvent {
                    if (it.key.nativeKeyCode == Key.Backspace.nativeKeyCode) {
                        Timber.e("click backspace")

                        if (content.isEmpty()) {
                            handleDeleteBlock(content)
                            focusManager.moveFocus(FocusDirection.Up)
                        }
                    }
                    true
                }
                .focusRequester(focusRequester)

            content.DrawEditableContent(modifier = modifier, viewModel = contentBlockViewModel)
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
                ContentBlockViewModel(emptyList()),
                sampleContents
            )
        }
    }
}