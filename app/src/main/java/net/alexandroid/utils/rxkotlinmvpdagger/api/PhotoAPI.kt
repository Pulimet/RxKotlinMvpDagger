package net.alexandroid.utils.rxkotlinmvpdagger.api

import io.reactivex.Observable
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAPI {
    @GET("?key=8485027-69f9d03dde29a38b6c34f9f38&image_type=photo")
    fun getPhotos(@Query("q") text: String): Observable<PhotoList>
}