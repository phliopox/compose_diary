package com.example.gradientdiary.presentation

import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth

fun getDaysInCurrentMonth() : Int{
    // 현재 월의 일 갯수 구하기
    val currentYearMonth = YearMonth.now()
    val daysInCurrentMonth = currentYearMonth.lengthOfMonth()
    Timber.e("현재 월의 일 갯수: $daysInCurrentMonth")

    return daysInCurrentMonth
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

