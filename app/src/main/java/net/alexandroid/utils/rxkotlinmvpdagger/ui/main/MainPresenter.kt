package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.schedulers.Schedulers
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.Photo
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class MainPresenter : MainMvp.RequiredPresenterOps, MainMvp.PresenterOps {

    private val disposables = CompositeDisposable()

    // Layer View reference
    private var mView: WeakReference<MainMvp.RequiredViewOps>? = null

    // Layer Model reference
    private val mModel: MainMvp.ModelOps

    init {
        MyLog.d("Init MainPresenter")
        this.mModel = MainModel(this)
    }

    override fun attachView(view: MainMvp.RequiredViewOps) {
        this.mView = WeakReference(view)
    }

    override fun onDestroy() {
        mModel.onDestroy()
        disposables.clear()
    }

    override fun searchObservableReady(searchObservable: Observable<String>) {
        disposables.add(
                searchObservable.debounce(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { text ->
                                    mView?.get()?.setSearchText(text)
                                    onNewSearchQuery(text)
                                },
                                { t -> throw OnErrorNotImplementedException(t) }))
    }

    private fun onNewSearchQuery(text: String?) {
        MyLog.d("New query: $text")
        val photoRetriever: PhotoRetriever? = mView?.get()?.getPhotoRetriever2()
        if (text == null || text.length < 3 || photoRetriever == null) {
            return
        }
        mView?.get()?.setProgressBarVisible(true)

        disposables.add(
                photoRetriever.getPhotosObservable(text)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ list: PhotoList? ->
                            MyLog.d("Size: " + list?.hits?.size)
                            mView?.get()?.setProgressBarVisible(false)
                            if (list?.hits?.size != null) {
                                mView?.get()?.updateResultsList(list.hits)
                            }
                        }))

    }

    override fun onImageClick(photo: Photo) {
        mView?.get()?.startDetailActivity(photo)
    }
}