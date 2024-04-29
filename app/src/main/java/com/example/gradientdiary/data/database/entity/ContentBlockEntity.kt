package com.example.gradientdiary.data.database.entity

import android.net.Uri
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import com.example.gradientdiary.presentation.ui.component.ImageBlock
import com.example.gradientdiary.presentation.ui.component.TextBlock
data class ContentBlockEntity(
    val type: ContentType,
    var seq: Long,
    val content: String,
) {
    fun convertToContentBlockModel(): ContentBlock<*> = when (type) {
        ContentType.Text -> TextBlock(seq = seq, content = content)
        ContentType.Image -> ImageBlock(seq = seq, content = Uri.parse(content))
        else -> throw Exception("Not Content Type")
    }
}
