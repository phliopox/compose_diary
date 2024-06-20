package com.example.gradientdiary.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gradientdiary.data.database.entity.DiaryEntity
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.ui.component.ImageBlock
import com.example.gradientdiary.presentation.ui.component.TextBlock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import kotlin.properties.Delegates

class ContentBlockViewModel(
    diary: DiaryEntity?
) : ViewModel() {
    private val _contentBlocksSource = MutableStateFlow<List<ContentBlock<*>>>(emptyList())
    val contentBlocks: StateFlow<List<ContentBlock<*>>> = _contentBlocksSource

    private var contentBlockList: MutableList<ContentBlock<*>> = mutableListOf()
    private var toBeDeleteBlockList: MutableList<ContentBlock<*>> = mutableListOf()
    private var focusedIndex by Delegates.notNull<Int>()
    private val initialContentBlock = diary?.contents ?: emptyList()
    var title = diary?.title ?: "제목"

    init {
        Timber.e("initialContentBlock : ${initialContentBlock.isEmpty()}")
        if (initialContentBlock.isEmpty()) {
            insertTextBlock()
            focusedIndex = 0
        } else {
            contentBlockList =
                initialContentBlock.map { it.convertToContentBlockModel() }.toMutableList()
            _contentBlocksSource.value = contentBlockList
            focusedIndex = contentBlockList.size - 1
        }
    }

    fun insertTextBlock(s: String? = null) {
        contentBlockList.add(TextBlock(content = s ?: ""))
        focusedIndex = contentBlockList.size - 1
        _contentBlocksSource.value = contentBlockList.toList()
    }

    fun saveTextBlockContents(s: String) {
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
        Timber.e("delete 호출 $contentBlockList")
        Timber.e("seleted block : $block")
        if (contentBlockList.size <= 1) return

       contentBlockList.remove(block)
        _contentBlocksSource.value = contentBlockList.toList()
    }
    fun tobeDeletedBlockByUri(uri: Uri) {
        val errorBlock = contentBlockList.find { it.content == uri }
        //파일을 찾을 수 없는 이미지 블럭을 save 시 한번에 삭제할 수 있도록 viewModel 에서 관리
        if (errorBlock != null) {
            toBeDeleteBlockList.add(errorBlock)
        }
    }

    fun removeNotFoundBlocks(): List<ContentBlock<*>> {
        contentBlockList = contentBlockList.filterNot { block ->
            toBeDeleteBlockList.any { it.content == block.content }
        }.toMutableList()
        val result = contentBlockList.toList()
        toBeDeleteBlockList.clear()
        return result
    }

    fun focusedBlock(index: Int) {
        focusedIndex = index
    }

    fun insertBlock(block: ContentBlock<*>) {
        contentBlockList.add(block)
        _contentBlocksSource.value = contentBlockList.toList()
    }
}