package com.example.gradientdiary.di

import android.content.Context
import com.example.gradientdiary.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DefaultCategoryModule {

    @Provides
    @Named("defaultCategory")
    fun provideDefaultCategory(@ApplicationContext context: Context): String {
        return context.getString(R.string.category_default)
    }
}