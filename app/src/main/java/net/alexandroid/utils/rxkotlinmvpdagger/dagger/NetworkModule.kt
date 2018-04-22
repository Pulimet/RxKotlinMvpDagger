package net.alexandroid.utils.rxkotlinmvpdagger.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import net.alexandroid.utils.mylog.MyLog
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.*
import java.util.Arrays.asList


@Module(includes = [(ContextModule::class)])
class NetworkModule {

    @Provides
    @MyApplicationScope
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @MyApplicationScope
    internal fun cache(cacheFile: File): Cache {
        cacheFile.mkdirs()
        return Cache(cacheFile, (10 * 1024 * 1024).toLong()) // 10 MB)
    }

    @Provides
    @MyApplicationScope
    internal fun cacheFile(context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    @MyApplicationScope
    internal fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> MyLog.d(message) })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return loggingInterceptor
    }
}