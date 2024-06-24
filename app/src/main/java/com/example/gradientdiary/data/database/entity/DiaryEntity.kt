package com.example.gradientdiary.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gradientdiary.data.DiaryModel

@Entity
data class DiaryEntity(
    //@PrimaryKey(autoGenerate = true) val id :Long? = null,
    @PrimaryKey val updateDate : String,
    val contents : List<ContentBlockEntity>,
    val title : String,
    val category : String
){
    fun convertToDiaryModel() = DiaryModel(
        //id = id,
        updateDate = updateDate,
        contents = contents.map{it.convertToContentBlockModel()},
        title = title,
        category = category
    )
}