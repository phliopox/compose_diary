package com.example.gradientdiary.data.database

import androidx.room.TypeConverter
import com.example.gradientdiary.data.database.entity.ContentEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.presentation.dateToLocalDate
import com.google.gson.Gson
import java.time.LocalDate
import java.util.Date

class TypeConverters {
    @TypeConverter
    fun dateToLong(value: Date?) = value?.time?.toLong()
    @TypeConverter
    fun longToDate(value: Long?) = value?.let { Date(it) }

    fun contentTypeToString(value : ContentType?) = value?.name
    @TypeConverter
    fun stringToContentType(value : String?) = when(value){
        ContentType.Text.name -> ContentType.Text
        ContentType.Image.name -> ContentType.Image
        else -> ContentType.Unknown
    }

    @TypeConverter
    fun contentListToContentJson(value: List<ContentEntity>?) = value?.let { Gson().toJson(it) }
    @TypeConverter
    fun contentJsonToContentList(value: String?) = value?.let {
        Gson().fromJson(value, Array<ContentEntity>::class.java).toList()
    }

    @TypeConverter
    fun stringListToJson(value: List<String>?) = value?.let {
        Gson().toJson(it)
    }
    @TypeConverter
    fun jsonToStringList(value: String?) = value?.let {
        Gson().fromJson(it, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun dateToString(date : LocalDate) : String{
        return date.toString()
    }
    @TypeConverter
    fun stringToDate(dateString : String) : LocalDate{
        return dateToLocalDate(dateString)
    }
}