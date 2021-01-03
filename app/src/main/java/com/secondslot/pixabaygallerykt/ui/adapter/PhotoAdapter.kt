package com.secondslot.pixabaygallerykt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.secondslot.pixabaygallerykt.R
import com.secondslot.pixabaygallerykt.data.model.PhotoItem
import com.squareup.picasso.Picasso

class PhotoAdapter(private val mPhotoItems: List<PhotoItem>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    /**
     * Требуемый интерфейс для активности-хоста фрагмента, вызывающего
     * этот адаптер
     */
    interface Callbacks {
        fun onPhotoSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return PhotoHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        return holder.bind(mPhotoItems)
    }

    override fun getItemCount(): Int =
        mPhotoItems.size

    /*
    Класс PhotoHolder
    */
    class PhotoHolder(
        view: View,
        private val mContext: Context
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var mPhotoItems: List<PhotoItem>

        private var mPhotoImageView: ImageView = view.findViewById(R.id.photo_image_view)
        private var mAuthorTextView: TextView = view.findViewById(R.id.author_text_view)
        private var mLikesTextView: TextView = view.findViewById(R.id.likes_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(photoItems: List<PhotoItem>) {
            mPhotoItems = photoItems
            val photoItem = mPhotoItems[bindingAdapterPosition]
            Picasso.get()
                .load(photoItem.previewURL)
                .placeholder(R.drawable.placeholder_photo)
                .into(mPhotoImageView)

            val author = mContext.resources.getString(R.string.author) + ": " + photoItem.author
            mAuthorTextView.text = author

            val likes = mContext.resources.getString(R.string.likes) + ": " + photoItem.likes
            mLikesTextView.text = likes
        }

        override fun onClick(v: View?) {
            (mContext as Callbacks).onPhotoSelected(bindingAdapterPosition)
        }
    }
}
