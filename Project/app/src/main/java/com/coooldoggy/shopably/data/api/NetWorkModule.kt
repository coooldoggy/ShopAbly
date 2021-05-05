package com.coooldoggy.shopably.data.api

import com.coooldoggy.shopably.BASE_API_URL
import com.coooldoggy.shopably.BuildConfig
import com.coooldoggy.shopably.ShopApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ShopApplication::class)
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
    fun provideApiHelper(shopApiHelper: ShopApiHelper): ShopApiHelper = shopApiHelper

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ShopServiceApi::class.java)

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