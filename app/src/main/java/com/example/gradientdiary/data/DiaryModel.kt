package com.example.gradientdiary.data

import com.example.gradientdiary.data.database.entity.ContentEntity
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import java.time.LocalDate
import java.util.Date

data class DiaryModel (
    val id : Long? =null,
    val updateDate : LocalDate,
    var contents : List<ContentEntity>
    )