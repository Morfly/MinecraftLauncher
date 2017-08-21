package org.tlauncher.tlauncherpe.mc.datalayer.di

import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.morfly.cleanarchitecture.core.di.qualifier.Remote
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitApiModule(private val apiEndpoint : String) {

    @Provides
    @Singleton
    @Remote
    internal fun provideGson() : Gson {
        return GsonBuilder()
                //.registerTypeAdapter(Date::class.java, DateDeserializer())
                .create()
    }

    @Provides
    @Singleton
    fun provideLoginInterceptor(): HttpLoggingInterceptor {
        val login = HttpLoggingInterceptor()
        login.level = HttpLoggingInterceptor.Level.BODY
        return login
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(loggingInterceptor : HttpLoggingInterceptor) : OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor)
            okHttpClient.addNetworkInterceptor(StethoInterceptor())
        }
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                /*.addInterceptor {
                    var req : Request = it.request()
                    if (req.header("XSessionToken") == null) {
                        req = req.newBuilder()
                                .addHeader("X-Session-Token", manager.getString(TOKEN))
                                .build()
                    }
                    it.proceed(req)
                }*/
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRestApi(@Remote gson: Gson, okHttpClient: OkHttpClient): RestApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(RestApi::class.java)
    }
}