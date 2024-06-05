package com.example.gradientdiary.di

import android.content.Context
import androidx.room.Room
import com.example.gradientdiary.data.database.DiaryAppDataBase
import com.example.gradientdiary.data.database.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DBModule {
    @Provides
    @Singleton
    fun provideMemoAppDatabase(@ApplicationContext context: Context): DiaryAppDataBase =
        Room.databaseBuilder(context, DiaryAppDataBase::class.java, "diary_app.db")
            .fallbackToDestructiveMigration().build()
    //addMigrations(
    //            MIGRATION_1_2)

    @Provides
    fun providesDiaryDao(appDataBase: DiaryAppDataBase) = appDataBase.diaryDao()

    @Provides
    fun providesCategoryDao(appDataBase: DiaryAppDataBase) = appDataBase.categoryDao()
}