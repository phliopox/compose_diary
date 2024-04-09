package com.example.gradientdiary.presentation

import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

fun getDaysInCurrentMonth() : Int{
    // 현재 월의 일 갯수 구하기
    val currentYearMonth = YearMonth.now()
    val daysInCurrentMonth = currentYearMonth.lengthOfMonth()
    Timber.e("현재 월의 일 갯수: $daysInCurrentMonth")

    return daysInCurrentMonth
}

fun getFirstDayNameOfMonth():String {
    val today = LocalDate.now()
    val firstDayOfMonth = LocalDate.of(today.year, today.month, 1)
    val dayOfWeek = firstDayOfMonth.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)

    Timber.e("현재 월의 1일은 $dayOfWeek 입니다.")
    return dayOfWeek
}
fun getBlankCountOfMonth():Int {
    val today = LocalDate.now()
    val firstDayOfMonth = LocalDate.of(today.year, today.month, 1)
    val blankCount = firstDayOfMonth.dayOfWeek.value

    Timber.e("달력 블럭을 비워야할 blank day는 $blankCount 개 입니다")
    return blankCount
}

fun getNow(): LocalDate =  LocalDate.now()

fun getMonth(): Int {
    return getNow().monthValue
}

fun getYear() : Int{
    return getNow().year
}

fun getDay() : Int{
    return getNow().dayOfMonth
}

