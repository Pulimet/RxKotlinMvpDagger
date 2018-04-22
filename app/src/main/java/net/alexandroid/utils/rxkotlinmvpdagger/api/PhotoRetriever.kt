package net.alexandroid.utils.rxkotlinmvpdagger.api

import io.reactivex.Observable
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PhotoRetriever {
    private val service: PhotoAPI

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://pixabay.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        service = retrofit.create(PhotoAPI::class.java)
    }

    fun getPhotosObservable(text: String): Observable<PhotoList> {
        return service.getPhotos(text)
    }
}