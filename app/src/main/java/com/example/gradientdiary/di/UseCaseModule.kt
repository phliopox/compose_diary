package com.example.gradientdiary.di

import com.example.gradientdiary.data.repository.DiaryAppRepository
import com.example.gradientdiary.domain.GetDiaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun providesGetDiaryUseCase(appRepository : DiaryAppRepository) = GetDiaryUseCase(appRepository)
}