package net.alexandroid.utils.rxkotlinmvpdagger

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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var picasso:Picasso

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.component.inject(this)

        picasso.load(R.mipmap.ic_launcher).into(imageView)

        setSearchListener()
    }

    override fun onDestroy() {
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
                        .subscribe({ text ->
                            textView.text = text
                            onNewSearchQuery()
                        }))
    }

    private fun onNewSearchQuery() {

    }
}
