package com.example.gradientdiary.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.gradientdiary.data.DiaryModel

@Entity(
    indices = [Index(value = ["updateDate", "categoryId"], unique = true)]
)
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id :Long? = null,
    val updateDate : String,
    val contents : List<ContentBlockEntity>,
    val title : String,
    val categoryId : Long
){
    fun convertToDiaryModel() = DiaryModel(
        id = id,
        updateDate = updateDate,
        contents = contents.map{it.convertToContentBlockModel()},
        title = title,
        categoryId = categoryId
    )
}