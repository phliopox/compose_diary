package com.example.gradientdiary.data

import com.example.gradientdiary.presentation.ui.component.ContentBlock
import java.util.Date

data class DiaryModel (
    val id : Long? =null,
    val updateDate : Date = Date(System.currentTimeMillis()),
    var contents : List<ContentBlock<*>>
    )