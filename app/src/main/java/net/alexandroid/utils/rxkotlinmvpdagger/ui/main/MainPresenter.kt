package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import java.lang.ref.WeakReference

class MainPresenter : MainMvp.RequiredPresenterOps, MainMvp.PresenterOps {

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
    }

    @SuppressLint("CheckResult")
    override fun onNewSearchQuery(text: String?, photoRetriever: PhotoRetriever) {
        MyLog.d("New query: $text")
        if (text == null || text.length < 3) {
            return
        }
        photoRetriever.getPhotosObservable(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: PhotoList? ->
                    MyLog.d("Size: " + list?.hits?.size)
                    if (list?.hits?.size != null) {
                        mView?.get()?.updateResultsList(list.hits)
                    }
                })

    }
}