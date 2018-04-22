package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.alexandroid.utils.mylog.MyLog
import net.alexandroid.utils.rxkotlinmvpdagger.R
import net.alexandroid.utils.rxkotlinmvpdagger.model.Photo

class MainAdapter(var photos: List<Photo>, var clickListener: View.OnClickListener, var picasso: Picasso) :
        RecyclerView.Adapter<MainAdapter.PhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_item, parent, false))
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        MyLog.d("$position")
        val photo = photos[position]
        holder.tags.text = photo.tags
        holder.likes.text = photo.likes.toString()
        holder.favorites.text = photo.favorites.toString()

        MyLog.d("Url: " + photo.previewURL)
        picasso.load(photo.previewURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.photo_item)
    }

    fun getPhoto(adapterPosition: Int): Photo {
        return photos[adapterPosition]
    }


    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tags: TextView
        var likes: TextView
        var favorites: TextView
        var photo_item: ImageView

        init {
            itemView.setOnClickListener(clickListener)
            itemView.tag = this
            tags = itemView.findViewById(R.id.tags) as TextView
            likes = itemView.findViewById(R.id.likes) as TextView
            favorites = itemView.findViewById(R.id.favorites) as TextView
            photo_item = itemView.findViewById(R.id.photo_item) as ImageView
        }
    }


}