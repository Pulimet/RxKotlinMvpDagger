package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import kotlinx.android.synthetic.main.activity_main.*
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.MyApplication
import net.alexandroid.utils.rxkotlinmvpdagger.R
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.Photo
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainMvp.RequiredViewOps, View.OnClickListener {

    @Inject
    lateinit var mPresenter: MainMvp.PresenterOps
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var photoRetriever: PhotoRetriever

    var mainAdapter: MainAdapter? = null

    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        MyApplication.component.inject(this)
        mPresenter.attachView(this)
        setSearchListener()
    }


    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
        disposables.clear()
    }


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
                                    mPresenter.onNewSearchQuery(text, photoRetriever)
                                },
                                { t -> throw OnErrorNotImplementedException(t) }))
    }


    //MainMvp.RequiredViewOps
    override fun updateResultsList(photosList: List<Photo>) {
        MyLog.d("")
        mainAdapter = MainAdapter(photosList, this@MainActivity, picasso)
        recyclerView.adapter = mainAdapter
    }

    //View.OnClickListener
    override fun onClick(v: View?) {
//        val intent = Intent(this, DetailActivity::class.java)
//        val holder = v?.tag as MainAdapter.PhotoViewHolder
//        intent.putExtra(DetailActivity.PHOTO, mainAdapter?.getPhoto(holder.adapterPosition))
//        startActivity(intent)
    }
}
