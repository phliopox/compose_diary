package com.example.gradientdiary.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

abstract class ContentBlock<T> {
    open val seq : Long by lazy { seq }
    open val content : T by lazy { content }

    @Composable
    abstract fun drawOnlyReadContent(modifier: Modifier)

    abstract fun isEmpty(): Boolean

    abstract fun addNextBlock()

    @Composable
    abstract fun drawEditableContent()

}