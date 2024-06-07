package com.example.gradientdiary.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.getYear
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class SharedPrefsStorageProvider @Inject constructor(@ApplicationContext context: Context){

    companion object {
        private const val PREF_CATEGORY = "category"
        private const val PREF_CURRENT_MONTH = "currentMonth"
        private const val PREF_CURRENT_YEAR = "currentYear"
    }

    private val category = context.getSharedPreferences(
        PREF_CATEGORY, Context.MODE_PRIVATE
    )
    private val currentMonth = context.getSharedPreferences(
        PREF_CURRENT_MONTH,Context.MODE_PRIVATE
    )

    private val currentYear = context.getSharedPreferences(
        PREF_CURRENT_YEAR,Context.MODE_PRIVATE
    )

    fun saveCategory(value: String): Boolean {
        return category.edit().putString(PREF_CATEGORY, value).commit()
    }


    fun getCategory(): String {
        val default = "일기"
        return  category.getString(PREF_CATEGORY, default)?:default
    }


    fun removeCategory(): Boolean {
        category.edit().remove(PREF_CATEGORY).apply()
        return true
    }

    fun getCurrentMonth(): Int {
        return  currentMonth.getInt(PREF_CURRENT_MONTH, getMonth())
    }
    fun getCurrentYear(): Int {
        return  currentYear.getInt(PREF_CURRENT_YEAR, getYear())
    }

    fun saveCurrentMonth(value: Int): Boolean {
        return currentMonth.edit().putInt(PREF_CURRENT_MONTH, value).commit()
    }
    fun saveCurrentYear(value: Int): Boolean {
        return currentYear.edit().putInt(PREF_CURRENT_YEAR, value).commit()
    }
    fun clearCurrentYearAndMonth(){
        currentMonth.edit().clear().apply()
        currentYear.edit().clear().apply()
    }

    fun clearAll() {
        category.edit().clear().apply()
        currentMonth.edit().clear().apply()
    }
}
