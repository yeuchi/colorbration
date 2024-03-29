package com.ctyeung.colorbration.di

import android.content.Context
import com.ctyeung.colorbration.data.PrefStoreRepository
import com.ctyeung.colorbration.data.SourceRepository
import com.ctyeung.colorbration.viewmodels.SourceViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object IlluminantModule {
    @Provides
    fun provideSourceViewModel(
        @ApplicationContext context: Context,
        sourceRepository: SourceRepository
    ): SourceViewModel = SourceViewModel(context, sourceRepository)

    @Provides
    fun provideSourceRepository(
        @ApplicationContext context: Context,
        prefStoreRepository: PrefStoreRepository
    ): SourceRepository =
        SourceRepository(context, prefStoreRepository)
}