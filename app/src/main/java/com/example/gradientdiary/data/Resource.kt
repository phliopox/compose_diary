package com.example.gradientdiary.data


sealed class Resource<out T>() {
    object Loading : Resource<Nothing>()
    class Success<out T>(val value : T) : Resource<T>()
    class Error<T>(val message : String) : Resource<Nothing>()

}