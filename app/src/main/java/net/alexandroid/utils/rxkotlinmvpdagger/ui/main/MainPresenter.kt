package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import net.alexandroid.utils.mylog.MyLog
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

    override fun onNewSearchQuery(text: String?) {
        MyLog.d("New query: $text")
    }
}