package com.example.gradientdiary.presentation

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.ui.localSnackBarManager
import timber.log.Timber
import java.text.DateFormatSymbols
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


fun getFirstDayOfWeek(year: Int, month: Int): Int {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    return firstDayOfMonth.dayOfWeek.value
}

fun getDaysInMonth(year: Int, month: Int): Int {
    // 특정 연도와 월의 일 갯수 구하기
    val yearMonth = YearMonth.of(year, month)
    return yearMonth.lengthOfMonth()
}

fun getNow(): LocalDate = LocalDate.now()
fun getMonth(): Int = getNow().monthValue
fun getYear(): Int = getNow().year
fun getDay(): Int = getNow().dayOfMonth

fun dateStringFormatter(date : String) : String{
    val inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault())
    val outputFormat = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
    val formatDate: LocalDate = LocalDate.parse(date, inputFormat)!!
    return outputFormat.format(formatDate)
}

fun dateToLocalDate(dateString: String): LocalDate {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return LocalDate.parse(dateString, dateFormatter)
}


@Composable
fun PersistedPermissionsCheck(
    content: Uri?,
    havePermission: @Composable () -> Unit,
    notFound: (Uri) -> Unit
) {
    var hasShownSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val persistedPermissions = context.contentResolver.persistedUriPermissions
    val snackBarManager = localSnackBarManager.current
    if (persistedPermissions.any { it.uri == content }) {
        havePermission()
    } else {
        if (!hasShownSnackbar) {
            snackBarManager.showMessage("일부 파일을 앨범에서 찾을 수 없어요.") // 파일을 삭제하여 uri 접근이 불가한 경우
            content?.let {
                notFound(it)
            }
            Timber.e("Permission Denial: No persistable permission grants found for URI !! $content")
            hasShownSnackbar = true
        }
    }
}

@Composable
fun getLocalizedMonthName(month: Int): String {
  //  val locale = Locale.getDefault()
    return when (month) {
        1 -> stringResource(id = R.string.january)
        2 -> stringResource(id = R.string.february)
        3 -> stringResource(id = R.string.march)
        4 -> stringResource(id = R.string.april)
        5 -> stringResource(id = R.string.may)
        6 -> stringResource(id = R.string.june)
        7 -> stringResource(id = R.string.july)
        8 -> stringResource(id = R.string.august)
        9 -> stringResource(id = R.string.september)
        10 -> stringResource(id = R.string.october)
        11 -> stringResource(id = R.string.november)
        12 -> stringResource(id = R.string.december)
        else -> throw IllegalArgumentException("Invalid month: $month")
    }
}

fun getDayNames(locale: Locale): List<String> {
    val dateFormatSymbols = DateFormatSymbols(locale)
    val dayNames = dateFormatSymbols.shortWeekdays
    // 요일 배열의 첫 번째 요소는 빈 문자열이므로 제외하고 반환
    return dayNames.filter { it.isNotEmpty() }
}

fun setLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

fun getCurrentLanguage(context: Context): String {
    val locale = context.resources.configuration.locales[0]
    return locale.language
}
/*fun getDaysInCurrentMonth() : Int{
    // 현재 월의 일 갯수 구하기
    val currentYearMonth = YearMonth.now()
    val daysInCurrentMonth = currentYearMonth.lengthOfMonth()
    Timber.e("현재 월의 일 갯수: $daysInCurrentMonth")

    return daysInCurrentMonth
}

fun getFirstDayNameOfMonth():String {
    val firstDayOfMonth = LocalDate.of(getYear(), getMonth(), 1)
    val dayOfWeek = firstDayOfMonth.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)

    Timber.e("현재 월의 1일은 $dayOfWeek 입니다.")
    return dayOfWeek
}
fun getBlankCountOfMonth():Int {
    val firstDayOfMonth = LocalDate.of(getYear(), getMonth(), 1)
    val blankCount = firstDayOfMonth.dayOfWeek.value

    Timber.e("달력 블럭을 비워야할 blank day는 $blankCount 개 입니다")
    return blankCount

    fun getBlankCountOfMonth(year: Int, month: Int): Int {
    // 특정 연도와 월의 첫 날의 요일로부터 빈 칸의 수 구하기
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val blankCount = firstDayOfMonth.dayOfWeek.value
    Timber.e("$year 년 $month 월의 빈 칸 개수: $blankCount")
    return blankCount
}
}*/