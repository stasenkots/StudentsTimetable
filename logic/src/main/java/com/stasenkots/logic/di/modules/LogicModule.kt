package com.stasenkots.logic.di.modules

import com.parse.livequery.ParseLiveQueryClient
import com.stasenkots.logic.network.networking.LessonApi
import com.stasenkots.logic.network.retrofit.RetrofitProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.*

@Module
class LogicModule {
    private val retrofitProvider=RetrofitProvider()
    @Provides
    fun provideLessonApi()=retrofitProvider.provideLessonApi()
    @Provides
    fun provideSubjectApi()=retrofitProvider.provideSubjectApi()
    @Provides
    fun provideStateApi()=retrofitProvider.provideStatesApi()
    @Provides
    fun provideStudentApi()=retrofitProvider.provideStudentApi()
    @Provides
    fun provideGroupApi()=retrofitProvider.provideGroupApi()
    @Provides
    fun provideParseLiveQueryClient()= ParseLiveQueryClient.Factory.getClient()
}