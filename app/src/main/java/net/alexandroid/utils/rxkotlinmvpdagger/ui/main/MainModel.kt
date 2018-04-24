package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList

class MainModel(private val mPresenter: MainMvp.RequiredPresenterOps) : MainMvp.ModelOps {

    private val disposables = CompositeDisposable()

    override fun getPhotosByQuery(text: String, photoRetriever: PhotoRetriever) {
        disposables.add(
                photoRetriever.getPhotosObservable(text)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ list: PhotoList? ->
                            MyLog.d("Size: " + list?.hits?.size)
                            mPresenter.onPhotosReceived(list)
                        }))
    }

    override fun onDestroy() {
        disposables.clear()
    }
}