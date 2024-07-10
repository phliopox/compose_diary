package com.example.gradientdiary.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.getYear
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class SharedPrefsStorageProvider @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val PREF_CATEGORY = stringPreferencesKey("category")
        private const val PREF_CURRENT_MONTH = "currentMonth"
        private const val PREF_CURRENT_YEAR = "currentYear"
        private val PREF_TEXT_ALIGN = stringPreferencesKey("textAlign")
        private val PREF_TEXT_STYLE = stringPreferencesKey("font_prefs")
        private val PREF_LANGUAGE = stringPreferencesKey("language")
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
            preferences[PREF_CATEGORY]?:""
        }
    val textAlign: Flow<String>
        get() = context.userDataStore.data.map { preferences ->
            preferences[PREF_TEXT_ALIGN]?:"start"
        }

    val currentFont : Flow<String>
        get() = context.userDataStore.data.map { preferences ->
            preferences[PREF_TEXT_STYLE] ?:"restart"
        }

    val language : Flow<String>
        get() = context.userDataStore.data.map { preferences ->
            preferences[PREF_LANGUAGE] ?:"ko"
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
    }


    suspend fun getCurrentCategory(): String {
        val category: Deferred<String?> =
            CoroutineScope(Dispatchers.IO).async {
                category.first()
            }
        val result = category.await()
        return result ?: ""
    }


    suspend fun removeCurrentCategory() {
        context.userDataStore.edit { pref ->
            pref.remove(PREF_CATEGORY)
        }
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

    suspend fun saveFontSelection(font: String) {
        context.userDataStore.edit{pref -> pref[PREF_TEXT_STYLE] = font}
    }

    suspend fun getSavedFontSelection(): String {
        val font: Deferred<String?> =
            CoroutineScope(Dispatchers.IO).async {
                currentFont.first()
            }
        val result = font.await()
        return result ?: "restart"
    }

    suspend fun saveLanguageSetting(str :String){
        context.userDataStore.edit { pref -> pref[PREF_LANGUAGE] = str }
    }

    suspend fun clearAll() {
        context.userDataStore.edit { pref ->
            pref.clear()
        }
    }
}

fun getFontResource(fontName: String): Int {
    return when (fontName) {
        "restart" -> R.font.restart
        "saeenum" -> R.font.saeeum
        "ongeul_julison" -> R.font.ongeul_julison
        "ongeul_iplyuttung" -> R.font.ongeul_iplyuttung
        "leeseoyun" -> R.font.leeseoyun
        "adultkid" -> R.font.adultkid
        else -> R.font.kopub_worlddotum_medium
    }
}