package com.example.gradientdiary.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel

abstract class ContentBlock<T> {
    open val seq : Long by lazy { seq }
    open val title : String by lazy { title }
    open val content : T by lazy { content }

/*
    @Composable
    abstract fun drawOnlyReadContent(modifier: Modifier)
*/

    abstract fun isEmpty(): Boolean

    abstract fun addNextBlock(viewModel: ContentBlockViewModel)

    @Composable
    abstract fun DrawEditableContent(modifier: Modifier, viewModel: ContentBlockViewModel)

    abstract fun convertToContentBlockEntity(): ContentBlockEntity


}