package com.example.gradientdiary.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gradientdiary.data.DiaryModel
import java.time.LocalDate
import java.util.Date

@Entity
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id :Long? = null ,
    val updateDate : LocalDate,
    val contents : List<ContentEntity>,
    val category : String
){
    fun convertToDiaryModel() = DiaryModel(
        id = id,
        updateDate = updateDate,
        contents = contents
    )
}