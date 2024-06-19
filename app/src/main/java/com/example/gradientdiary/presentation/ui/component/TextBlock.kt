package com.example.gradientdiary.presentation.ui.component

import android.os.Parcelable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
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
        BasicTextField(
            value = textInputState.value,
            onValueChange = { new ->
                textInputState.value = new
                content = new
            },
            textStyle = MaterialTheme.typography.titleMedium.copy(color = DefaultText),
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    addNextBlock(viewModel = viewModel)
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
    }


    override fun convertToContentBlockEntity() = ContentBlockEntity(
        type = ContentType.Text,
        seq = seq,
        content = textInputState.value
    )


}