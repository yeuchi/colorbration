package com.ctyeung.colorbration.di

import android.content.Context
import com.ctyeung.colorbration.data.ObserverRepository
import com.ctyeung.colorbration.data.PrefStoreRepository
import com.ctyeung.colorbration.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ObserverModule {
    @Provides
    fun provideMainViewModel(
        @ApplicationContext context: Context,
        observerRepository: ObserverRepository
    ): MainViewModel = MainViewModel(context, observerRepository)

    @Provides
    fun provideObserverRepository(
        @ApplicationContext context: Context,
        prefStoreRepository: PrefStoreRepository
    ): ObserverRepository =
        ObserverRepository(context, prefStoreRepository)

    @Provides
    fun providePreferenceStoreRepository(@ApplicationContext context: Context): PrefStoreRepository =
        PrefStoreRepository(context)
}