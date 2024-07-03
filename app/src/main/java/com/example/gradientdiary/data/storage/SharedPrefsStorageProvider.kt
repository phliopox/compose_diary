package com.example.gradientdiary.data.storage

import android.content.Context
import androidx.compose.ui.text.style.TextAlign
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.getYear
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class SharedPrefsStorageProvider @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val PREF_CATEGORY = stringPreferencesKey("category")
        private const val PREF_CURRENT_MONTH = "currentMonth"
        private const val PREF_CURRENT_YEAR = "currentYear"
        const val default = "일기"
        private val PREF_TEXT_ALIGN = stringPreferencesKey("textAlign")

    }

    /*  private val category = context.getSharedPreferences(
          PREF_CATEGORY, Context.MODE_PRIVATE
      )*/
    private val currentMonth = context.getSharedPreferences(
        PREF_CURRENT_MONTH, Context.MODE_PRIVATE
    )

    private val currentYear = context.getSharedPreferences(
        PREF_CURRENT_YEAR, Context.MODE_PRIVATE
    )

    val category: Flow<String>
        get() = context.userDataStore.data.map { preferences ->
            preferences[PREF_CATEGORY]?:default
        }
    val textAlign: Flow<String>
        get() = context.userDataStore.data.map { preferences ->
            preferences[PREF_TEXT_ALIGN]?:"start"
        }

    suspend fun saveTextAlignStatus(value: String) {
        context.userDataStore.edit { pref -> pref[PREF_TEXT_ALIGN] = value }
    }
     /*suspend fun getTextAlignStatus() : TextAlign{

        *//*        val align: Deferred<String?> =
                    CoroutineScope(Dispatchers.IO).async {
                        textAlign.first()
                    }*//*
        return when(textAlign.firstOrNull()){
            "end" -> TextAlign.End
            "center" -> TextAlign.Center
            else -> TextAlign.Start
        }
    }*/

    suspend fun saveSelectedCategory(value: String) {
        context.userDataStore.edit { pref -> pref[PREF_CATEGORY] = value }
        //category.edit().putString(PREF_CATEGORY, value).commit()
    }


    suspend fun getCurrentCategory(): String {
        val category: Deferred<String?> =
            CoroutineScope(Dispatchers.IO).async {
                category.first()
            }
        val result = category.await()
        return result ?: default
        //category.getString(PREF_CATEGORY, default) ?: default
    }


    suspend fun removeCurrentCategory() {
        context.userDataStore.edit { pref ->
            pref.remove(PREF_CATEGORY)
        }
        //category.edit().remove(PREF_CATEGORY).apply()
    }

    fun getCurrentMonth(): Int {
        return currentMonth.getInt(PREF_CURRENT_MONTH, getMonth())
    }

    fun getCurrentYear(): Int {
        return currentYear.getInt(PREF_CURRENT_YEAR, getYear())
    }

    fun saveCurrentMonth(value: Int): Boolean {
        return currentMonth.edit().putInt(PREF_CURRENT_MONTH, value).commit()
    }

    fun saveCurrentYear(value: Int): Boolean {
        return currentYear.edit().putInt(PREF_CURRENT_YEAR, value).commit()
    }

    fun clearCurrentYearAndMonth() {
        currentMonth.edit().clear().apply()
        currentYear.edit().clear().apply()
    }

    suspend fun clearAll() {
        context.userDataStore.edit { pref ->
            pref.clear()
        }
        /* category.edit().clear().apply()
         currentMonth.edit().clear().apply()*/
    }
}
