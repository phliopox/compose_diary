package com.example.gradientdiary.di

import com.example.gradientdiary.data.repository.DiaryAppRepository
import com.example.gradientdiary.domain.DeleteCategoryUseCase
import com.example.gradientdiary.domain.DeleteDiaryUseCase
import com.example.gradientdiary.domain.GetCategoryUseCase
import com.example.gradientdiary.domain.GetDiaryUseCase
import com.example.gradientdiary.domain.SaveCategoryUseCase
import com.example.gradientdiary.domain.SaveDiaryUseCase
import com.example.gradientdiary.domain.SearchDiaryByKeywordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun providesGetDiaryUseCase(appRepository: DiaryAppRepository) = GetDiaryUseCase(appRepository)


    @Provides
    fun providesSaveDiaryUseCase(appRepository: DiaryAppRepository) =
        SaveDiaryUseCase(appRepository)

    @Provides
    fun providesSaveCategoryUseCase(appRepository: DiaryAppRepository) =
        SaveCategoryUseCase(appRepository)


    @Provides
    fun providesDeleteCategoryUseCase(appRepository: DiaryAppRepository) =
        DeleteCategoryUseCase(appRepository)

    @Provides
    fun providesDeleteDiaryUserCase(appRepository: DiaryAppRepository) =
        DeleteDiaryUseCase(appRepository)

    @Provides
    fun providesGetCategoryIdByName(appRepository: DiaryAppRepository) =
        GetCategoryUseCase(appRepository)

    @Provides
    fun providesSearchDiaryByKeyword(appRepository: DiaryAppRepository) =
        SearchDiaryByKeywordUseCase(appRepository)
}