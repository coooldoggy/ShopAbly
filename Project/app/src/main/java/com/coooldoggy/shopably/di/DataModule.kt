package com.coooldoggy.shopably.di

import android.content.Context
import com.coooldoggy.shopably.data.FavoriteDao
import com.coooldoggy.shopably.data.AppDatabase
import com.coooldoggy.shopably.data.api.ShopApiHelper
import com.coooldoggy.shopably.data.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ShopApiHelper,
                          localDataSource: FavoriteDao
    ) = HomeRepository(remoteDataSource, localDataSource)
}