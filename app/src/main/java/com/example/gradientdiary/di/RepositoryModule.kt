package com.example.gradientdiary.di

import com.example.gradientdiary.data.repository.DefaultDiaryAppRepository
import com.example.gradientdiary.data.repository.DiaryAppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsDefaultAppRepository(defaultAppRepository: DefaultDiaryAppRepository): DiaryAppRepository
}