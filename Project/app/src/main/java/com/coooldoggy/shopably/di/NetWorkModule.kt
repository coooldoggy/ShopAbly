package com.coooldoggy.shopably.di

import com.coooldoggy.shopably.BASE_API_URL
import com.coooldoggy.shopably.BuildConfig
import com.coooldoggy.shopably.data.api.ShopApiHelper
import com.coooldoggy.shopably.data.api.ShopApiHelperImpl
import com.coooldoggy.shopably.data.service.ShopApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    private val TAG = NetWorkModule::class.java.simpleName

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_API_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ShopApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(shopApiHelperImpl: ShopApiHelperImpl): ShopApiHelper = shopApiHelperImpl

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor =HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }
}