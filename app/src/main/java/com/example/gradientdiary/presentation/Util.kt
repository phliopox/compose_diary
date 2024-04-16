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
}

fun getDaysInMonth(year: Int, month: Int): Int {
    // 특정 연도와 월의 일 갯수 구하기
    val yearMonth = YearMonth.of(year, month)
    val daysInMonth = yearMonth.lengthOfMonth()
    Timber.e("$year 년 $month 월의 일 갯수: $daysInMonth")
    return daysInMonth
}

fun getBlankCountOfMonth(year: Int, month: Int): Int {
    // 특정 연도와 월의 첫 날의 요일로부터 빈 칸의 수 구하기
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val blankCount = firstDayOfMonth.dayOfWeek.value
    Timber.e("$year 년 $month 월의 빈 칸 개수: $blankCount")
    return blankCount
}

fun getNow(): LocalDate =  LocalDate.now()
fun getMonth(): Int  = getNow().monthValue
fun getYear() : Int = getNow().year
fun getDay() : Int = getNow().dayOfMonth


