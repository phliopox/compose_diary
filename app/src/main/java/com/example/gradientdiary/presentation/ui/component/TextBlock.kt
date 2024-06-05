package com.example.gradientdiary.presentation.ui.component

import android.os.Parcelable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import timber.log.Timber

@Parcelize
data class TextBlock(
    override var seq: Long = 0,
    override var content: String
) : ContentBlock<String>(), Parcelable {
    @IgnoredOnParcel
    var textInputState: MutableState<String> = mutableStateOf(content)

    override fun isEmpty(): Boolean {
        Timber.e("TextBlock isEmpty() :${textInputState.value}")
        return textInputState.value.isBlank()
    }

    override fun addNextBlock(viewModel: ContentBlockViewModel) {
        viewModel.insertBlock(TextBlock(content = ""))
    }

    @Composable
    override fun DrawEditableContent(modifier: Modifier, viewModel: ContentBlockViewModel) {
        val focusManager = LocalFocusManager.current
        EditableText(
            value = textInputState.value,
            modifier = modifier
                .fillMaxSize()
                .padding(
                    horizontal = Paddings.extra,
                    vertical = Paddings.large
                )
        ) { new ->
            textInputState.value = new
            content = new
           // viewModel.saveTextBlockContents(new)

        }
    }


    override fun convertToContentBlockEntity() = ContentBlockEntity(
        type = ContentType.Text,
        seq = seq,
        content = textInputState.value
    )


}