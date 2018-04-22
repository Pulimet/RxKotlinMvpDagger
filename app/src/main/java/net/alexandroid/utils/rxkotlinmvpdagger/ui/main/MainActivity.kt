package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.MyApplication
import net.alexandroid.utils.rxkotlinmvpdagger.R
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainMvp.RequiredViewOps {

    @Inject
    lateinit var mPresenter: MainMvp.PresenterOps
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var photoRetriever: PhotoRetriever

    private val disposables = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.component.inject(this)

        attachPresenter()

        picasso.load(R.mipmap.ic_launcher).into(imageView)

        setSearchListener()

        photoRetriever.getPhotosObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: PhotoList? ->
                    MyLog.d("Size: " + list?.hits?.size)
                    MyLog.d("Result: " + list?.hits?.get(0)?.webformatURL)
                    MyLog.d("Result: " + list?.hits?.get(1)?.webformatURL)
                    MyLog.d("Result: " + list?.hits?.get(2)?.webformatURL)
                })

    }

    private fun attachPresenter() {
        mPresenter.attachView(this)
    }

    override fun onRetainCustomNonConfigurationInstance(): MainMvp.PresenterOps? {
        return mPresenter
    }


    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
        disposables.clear()
    }

    @SuppressLint("CheckResult")
    private fun setSearchListener() {
        disposables.add(
                Observable.create(ObservableOnSubscribe<String> { subscriber ->
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) = Unit
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = subscriber.onNext(s.toString())
                    })
                }).debounce(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { text ->
                                    textView.text = text
                                    mPresenter.onNewSearchQuery(text)
                                },
                                { t -> throw OnErrorNotImplementedException(t) }))
    }

}
