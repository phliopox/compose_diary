package com.example.gradientdiary.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gradientdiary.data.DiaryModel

@Entity
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id :Long? = null,
    val updateDate : String,
    val contents : List<ContentBlockEntity>,
    val category : String
){
    fun convertToDiaryModel() = DiaryModel(
        id = id,
        updateDate = updateDate,
        contents = contents.map{it.convertToContentBlockModel()},
        category = category
    )
}