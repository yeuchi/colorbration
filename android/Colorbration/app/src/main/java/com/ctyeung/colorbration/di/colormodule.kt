package com.ctyeung.colorbration.di

import android.content.Context
import com.ctyeung.colorbration.viewmodels.ChromaticityViewModel
import com.ctyeung.colorbration.viewmodels.SpectralViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object colormodule {
    @Provides
    fun provideChromaticityViewModel(@ApplicationContext context: Context): ChromaticityViewModel = ChromaticityViewModel(context)

    @Provides
    fun provideSpectralViewModel(@ApplicationContext context: Context): SpectralViewModel = SpectralViewModel(context)
}