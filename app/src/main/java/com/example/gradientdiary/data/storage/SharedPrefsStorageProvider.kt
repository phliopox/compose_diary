package com.example.gradientdiary.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class SharedPrefsStorageProvider @Inject constructor(@ApplicationContext context: Context){

    companion object {
        private const val PREF_CATEGORY = "category"
    }

    private val prefs = context.getSharedPreferences(
        PREF_CATEGORY, Context.MODE_PRIVATE
    )


    fun save(value: String): Boolean {
        return prefs.edit().putString(PREF_CATEGORY, value).commit()
    }


    fun get(): String {
        val default = "일기"
        return  prefs.getString(PREF_CATEGORY, default)?:default
    }


    fun remove(): Boolean {
        prefs.edit().remove(PREF_CATEGORY).apply()
        return true
    }


    fun clear() {
        prefs.edit().clear().apply()
    }
}
