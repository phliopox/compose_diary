package com.example.gradientdiary.presentation.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.getCurrentLanguage
import com.example.gradientdiary.presentation.setLocale
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.viewModel.SettingViewModel
import timber.log.Timber

@Composable
fun LanguageSettingScreen(settingViewModel: SettingViewModel) {
    val textStyle = MaterialTheme.typography.titleLarge.copy(color = DefaultText, fontSize = 25.sp)
    val context = LocalContext.current
    var currentLanguage by remember { mutableStateOf(getCurrentLanguage(context)) }
    var isKo by remember { mutableStateOf(currentLanguage == "ko") }
    val handleClickRadioBtn = { it: String ->
        settingViewModel.updateLocaleLanguage(it)
        setLocale(context, it)
        currentLanguage = getCurrentLanguage(context)
        isKo = currentLanguage == "ko"
        Timber.e(" handle : $it , $currentLanguage")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.dp20)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.padding(horizontal = Dimens.dp20)
                .clickable(
                    MutableInteractionSource(),
                    null
                ) {
                    handleClickRadioBtn("ko")
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.language_ko),
                style = textStyle,
            )
            RadioButton(
                selected = isKo,
                onClick = { handleClickRadioBtn("ko") },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.padding(horizontal = Dimens.dp20)
                .clickable(
                    MutableInteractionSource(),
                    null
                ) {
                    handleClickRadioBtn("en")
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.language_en),
                style = textStyle,
            )
            RadioButton(
                selected = !isKo,
                onClick = { handleClickRadioBtn("en") },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
        }
    }

}