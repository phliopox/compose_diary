package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.gradientdiary.presentation.theme.DefaultText

@Composable
fun EditableText(
    readOnly: Boolean? = null,
    value: String,
    hint: String? = null,
    modifier: Modifier? = Modifier,
    style: TextStyle? = null,
    onChange: (String) -> Unit
) {
    var textValue by rememberSaveable { mutableStateOf(value) }
    val textStyle = style ?: MaterialTheme.typography.titleMedium.copy(color = DefaultText)
    val fieldModifier = modifier ?: Modifier
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        readOnly = readOnly == true,
        value = textValue,
        onValueChange = { new ->
            textValue = new
            onChange(new)
        },
        textStyle = textStyle,
        modifier = fieldModifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                if (isFocused) {
                    if (textValue == hint) textValue = ""
                } else {
                    if (textValue.isEmpty()) textValue = value
                }
            },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}