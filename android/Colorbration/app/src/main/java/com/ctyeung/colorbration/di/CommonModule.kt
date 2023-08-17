package com.ctyeung.colorbration.di

import android.content.Context
import com.ctyeung.colorbration.data.ColorDataRepository
import com.ctyeung.colorbration.data.PrefStoreRepository
import com.ctyeung.colorbration.viewmodels.ChromaticityViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    fun provideChromaticityViewModel(@ApplicationContext context: Context): ChromaticityViewModel =
        ChromaticityViewModel(context)

    @Provides
    fun providePreferenceStoreRepository(@ApplicationContext context: Context): PrefStoreRepository =
        PrefStoreRepository(context)

    @Provides
    fun provideColorDataRepository(
        @ApplicationContext context: Context,
        prefStoreRepository: PrefStoreRepository
    ): ColorDataRepository =
        ColorDataRepository(context, prefStoreRepository)
}