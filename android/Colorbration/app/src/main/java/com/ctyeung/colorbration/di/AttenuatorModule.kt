package com.ctyeung.colorbration.di

import android.content.Context
import com.ctyeung.colorbration.data.AttenuatorRepository
import com.ctyeung.colorbration.data.ColorDataRepository
import com.ctyeung.colorbration.data.PrefStoreRepository
import com.ctyeung.colorbration.viewmodels.ChromaticityViewModel
import com.ctyeung.colorbration.viewmodels.ReflectanceViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AttenuatorModule {
    @Provides
    fun provideReflectanceViewModel(
        @ApplicationContext context: Context,
        attenuatorRepository: AttenuatorRepository
    ): ReflectanceViewModel =
        ReflectanceViewModel(context, attenuatorRepository)

    @Provides
    fun provideAttenuatorRepository(
        @ApplicationContext context: Context,
        colorDataRepository: ColorDataRepository
    ): AttenuatorRepository =
        AttenuatorRepository(context, colorDataRepository)
}