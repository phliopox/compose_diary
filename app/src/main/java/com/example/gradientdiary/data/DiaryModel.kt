package com.example.gradientdiary.data

import com.example.gradientdiary.presentation.ui.component.ContentBlock

data class DiaryModel(
    val id: Long? =null,
    val updateDate: String,
    var contents: List<ContentBlock<*>>,
    var title : String,
    val category: String
    )