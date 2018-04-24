package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import io.reactivex.Observable
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.Photo
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList

interface MainMvp {
    /**
     * View mandatory methods. Available to Presenter
     * Presenter -> View
     */
    interface RequiredViewOps {
        fun updateResultsList(photosList: List<Photo>)
        fun setSearchText(text: String)
        fun getPhotoRetriever2(): PhotoRetriever
        fun startDetailActivity(photo: Photo)
        fun setProgressBarVisible(visible: Boolean)
    }

    /**
     * Operations offered from Presenter to View
     * View -> Presenter
     */
    interface PresenterOps {
        fun onDestroy()

        fun attachView(view: RequiredViewOps)

        fun searchObservableReady(searchObservable: Observable<String>)

        fun onImageClick(it: Photo)
    }

    /**
     * Operations offered from Presenter to Model
     * Model -> Presenter
     */
    interface RequiredPresenterOps {
        fun onPhotosReceived(list: PhotoList?)
    }

    /**
     * Model operations offered to Presenter
     * Presenter -> Model
     */
    interface ModelOps {
        fun getPhotosByQuery(text: String, photoRetriever: PhotoRetriever)
        fun onDestroy()
    }

}