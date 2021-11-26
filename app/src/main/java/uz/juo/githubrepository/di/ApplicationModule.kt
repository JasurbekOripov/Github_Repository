package uz.juo.githubrepository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.juo.githubrepository.retrofit.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl(): String = "https://api.github.com/"
//    fun provideBaseUrl(): String = "http://10.0.2.2:3000/"

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client =
            OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)

        var retrofit = Retrofit.Builder().baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create()).client(client.build())
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun proviceApiService(): ApiService = providesRetrofit().create(ApiService::class.java)

}