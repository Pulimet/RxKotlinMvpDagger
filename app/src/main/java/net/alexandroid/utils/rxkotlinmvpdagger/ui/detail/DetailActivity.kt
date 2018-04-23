package net.alexandroid.utils.rxkotlinmvpdagger.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import net.alexandroid.utils.rxkotlinmvpdagger.R
import net.alexandroid.utils.rxkotlinmvpdagger.model.Photo

class DetailActivity : AppCompatActivity() {

    companion object {
        val PHOTO = "PHOTO"

        fun startDetailActivity(callingActivity: Activity, photo: Photo) {
            val intent = Intent(callingActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.PHOTO, photo)
            callingActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val photo = intent.getSerializableExtra(PHOTO) as Photo?
        photo?.webformatURL?.let {
            Glide.with(this)
                    .load(it)
                    .into(imageView)
        }

        imageView.setOnClickListener {
            finish()
        }
    }


}
