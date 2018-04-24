package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_main.*
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.MyApplication
import net.alexandroid.utils.rxkotlinmvpdagger.R
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.Photo
import net.alexandroid.utils.rxkotlinmvpdagger.ui.detail.DetailActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainMvp.RequiredViewOps, View.OnClickListener {

    @Inject
    lateinit var mPresenter: MainMvp.PresenterOps
    @Inject
    lateinit var photoRetriever: PhotoRetriever

    var mainAdapter: MainAdapter? = null

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
    }


    private fun setSearchListener() {
        val searchObservable = Observable.create(ObservableOnSubscribe<String> { subscriber ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = subscriber.onNext(s.toString())
            })
        })

        mPresenter.searchObservableReady(searchObservable)

    }


    //MainMvp.RequiredViewOps
    override fun setSearchText(text: String) {
        textView.text = text
    }

    override fun getPhotoRetriever2(): PhotoRetriever {
        return photoRetriever
    }

    override fun updateResultsList(photosList: List<Photo>) {
        MyLog.d("")
        mainAdapter = MainAdapter(photosList, this@MainActivity)
        recyclerView.adapter = mainAdapter
    }

    override fun startDetailActivity(photo: Photo) {
        DetailActivity.startDetailActivity(this, photo)
    }

    override fun setProgressBarVisible(visible: Boolean) {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    //View.OnClickListener
    override fun onClick(v: View?) {
        val holder = v?.tag as MainAdapter.PhotoViewHolder
        val photo = mainAdapter?.getPhoto(holder.adapterPosition)

        photo?.let {
            mPresenter.onImageClick(it)
        }
    }


}
