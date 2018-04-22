package net.alexandroid.utils.rxkotlinmvpdagger.dagger

import dagger.Module
import dagger.Provides
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever

@Module
class PhotosApiModule {
    @MyApplicationScope
    @Provides
    fun providePhotoRetriever(): PhotoRetriever {
        return PhotoRetriever()
    }
}