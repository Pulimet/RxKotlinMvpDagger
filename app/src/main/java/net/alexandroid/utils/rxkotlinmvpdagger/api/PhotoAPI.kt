package net.alexandroid.utils.rxkotlinmvpdagger.api

import io.reactivex.Observable
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import retrofit2.Call
import retrofit2.http.GET

interface PhotoAPI {
    @GET("?key=8485027-69f9d03dde29a38b6c34f9f38&q=nature&image_type=photo")
    fun getPhotos(): Observable<PhotoList>
}