package com.example.gradientdiary.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["categoryName"], unique = true)])
data class CategoryEntity (
    @PrimaryKey(autoGenerate = true) val id :Long? =null,
    val categoryName : String
)