package com.example.gradientdiary.presentation.ui.write

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import timber.log.Timber

@Composable
fun ColumnScope.ContentBlockScreen(
    contentBlockViewModel: ContentBlockViewModel,
    handleSaveDiary: () -> Unit,
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
    //Column(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f)) {
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
                handleSaveDiary = handleSaveDiary
            )
        }
    //}
}

@Composable
fun ContentBlocks(
    contents: List<ContentBlock<*>>,
    handleDeleteBlock: (ContentBlock<*>) -> Unit,
    handleFocusedIndex: (Int) -> Unit,
    contentBlockViewModel: ContentBlockViewModel
) {

    val scrollState = rememberScrollState()
    Timber.e("ContentBlocks : ${contents[0]}")
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
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

              content.DrawEditableContent(modifier = modifier, viewModel = contentBlockViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWriteScreen2() {
    //  GradientDiaryTheme {
    Column(modifier = Modifier.fillMaxSize()) {
        val sampleContents = emptyList<ContentBlock<*>>()
        Column(Modifier.weight(1f)) {
            ContentBlocks(
                contents = sampleContents,
                handleDeleteBlock = {},
                handleFocusedIndex = {},
                contentBlockViewModel = ContentBlockViewModel(emptyList()),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            WriteScreenBottomBar(
                handleAddImage = {},
                handleSaveDiary = {}
            )
        }
    }
    //}
}