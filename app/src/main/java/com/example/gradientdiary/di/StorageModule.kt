package com.example.gradientdiary.di

import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Provides
    fun provideSharePrefStorageProvider(provider: SharedPrefsStorageProvider) = provider
}