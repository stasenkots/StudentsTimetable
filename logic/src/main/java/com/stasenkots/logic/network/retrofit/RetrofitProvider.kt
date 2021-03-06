package com.stasenkots.logic.network.retrofit


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stasenkots.logic.APPLICATION_ID
import com.stasenkots.logic.BASE_URL
import com.stasenkots.logic.REST_API_KEY
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.network.networking.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

private const val GROUP_ID_PATH="/classes/group_ids/"
class RetrofitProvider {
    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        })
        .addInterceptor { chain ->
            val original = chain.request()
            if (original.method != "GET" || original.url.encodedPath== GROUP_ID_PATH) chain.proceed(original)
            else {
                val newUrl = original.url.newBuilder()
                    .addQueryParameter("where", "{\"group_id\":\"${User.groupId}\"}")
                    .build()
                val newRequest = original.newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
        }
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun provideLessonApi() = retrofit.create<LessonApi>()
    fun provideSubjectApi() = retrofit.create<SubjectApi>()
    fun provideStatesApi() = retrofit.create<StateApi>()
    fun provideStudentApi() = retrofit.create<StudentApi>()
    fun provideGroupApi() = retrofit.create<GroupApi>()


}