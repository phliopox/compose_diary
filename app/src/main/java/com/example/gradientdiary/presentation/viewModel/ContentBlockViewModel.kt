package com.example.gradientdiary.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.ui.component.ImageBlock
import com.example.gradientdiary.presentation.ui.component.TextBlock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class ContentBlockViewModel(
    initialContentBlock: List<ContentBlockEntity>
) :ViewModel(){
    private val _contentBlocksSource = MutableStateFlow<List<ContentBlock<*>>>(emptyList())
    val contentBlocks: StateFlow<List<ContentBlock<*>>> = _contentBlocksSource

    private var contentBlockList: MutableList<ContentBlock<*>> = mutableListOf()
    private var focusedIndex: Int = 0

    var title = "제목"
    init {
        Timber.e("contentBlockViewModel init $initialContentBlock")
        if (initialContentBlock.isEmpty()) {
            insertTextBlock()
        } else {
            contentBlockList = initialContentBlock.map { it.convertToContentBlockModel() }.toMutableList()
            _contentBlocksSource.value = contentBlockList
        }
    }

    fun insertTextBlock(s: String? = null) {
        Timber.e("insertTextBlock !")
        contentBlockList.add(TextBlock(content = s ?: ""))
        focusedIndex = contentBlockList.size - 1
        _contentBlocksSource.value = contentBlockList.toList()
    }

    fun saveTextBlockContents(s : String){
        if (focusedIndex != -1 && focusedIndex < contentBlockList.size) {
            val block = contentBlockList[focusedIndex]
            if (block is TextBlock) {
                block.content = s
                _contentBlocksSource.value = contentBlockList.toList()
            }
        }
    }

    fun changeToImageBlock(uri: Uri) {
        contentBlockList.add(focusedIndex + 1, ImageBlock(content = uri))
        insertTextBlock()
    }

    fun deleteBlock(block: ContentBlock<*>) {
        if (contentBlockList.size <= 1) return

        contentBlockList.remove(block)
        _contentBlocksSource.value = contentBlockList.toList()
    }

    fun focusedBlock(index: Int) {
        focusedIndex = index
    }

    fun insertBlock(block: ContentBlock<*>) {
        contentBlockList.add(block)
        _contentBlocksSource.value = contentBlockList.toList()
    }
}