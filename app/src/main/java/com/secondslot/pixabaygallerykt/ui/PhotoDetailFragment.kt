package com.secondslot.pixabaygallerykt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.secondslot.pixabaygallerykt.R
import com.secondslot.pixabaygallerykt.data.model.PhotoItem
import com.secondslot.pixabaygallerykt.ui.viewmodel.PhotoSlidePagerViewModel
import com.squareup.picasso.Picasso

class PhotoDetailFragment(private val mPhotoItem: PhotoItem) : Fragment() {

//    private lateinit var photoSlidePagerViewModel: PhotoSlidePagerViewModel

    private lateinit var mPhotoImageView: ImageView
    private lateinit var mLikesTextView: TextView
    private lateinit var mAuthorPhotoImageView: ImageView
    private lateinit var mAuthorNameTextView: TextView

    companion object {
        fun newInstance(photoItem: PhotoItem): PhotoDetailFragment {
            return PhotoDetailFragment(photoItem)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_detail, container, false)

        mPhotoImageView = view.findViewById(R.id.photo_image_view)
        mLikesTextView = view.findViewById(R.id.likes_text_view)
        mAuthorPhotoImageView = view.findViewById(R.id.author_avatar_image_view)
        mAuthorNameTextView = view.findViewById(R.id.author_text_view)

        Picasso.get()
            .load(mPhotoItem.webformatURL)
            .into(mPhotoImageView)

        val likes = requireContext().resources.getString(R.string.likes) + ": " + mPhotoItem.likes
        mLikesTextView.text = likes

        if (mPhotoItem.authorImageURL.isNotEmpty()) {
            Picasso.get()
                .load(mPhotoItem.authorImageURL)
                .placeholder(R.drawable.placeholder_author_photo)
                .error(R.drawable.placeholder_author_photo)
                .into(mAuthorPhotoImageView)
        } else {
            mAuthorPhotoImageView.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.placeholder_author_photo))
        }

        mAuthorNameTextView.text = mPhotoItem.author

        return view

    }
}