package com.secondslot.pixabaygallerykt.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.secondslot.pixabaygallerykt.data.model.PhotoItem
import com.secondslot.pixabaygallerykt.data.network.Fetcher

class Repository private constructor(context: Context) {

    private val mFetcher = Fetcher()
    lateinit var mPhotoItemLiveData: LiveData<List<PhotoItem>>

    companion object {
        private var INSTANCE: Repository? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE
                ?: throw IllegalStateException("Репозиторий должен быть инициализирован")
        }
    }

    fun getPhotos(query: String?): LiveData<List<PhotoItem>> {

        mPhotoItemLiveData = if (query.isNullOrBlank()) {
            mFetcher.fetchPhotos()
        } else {
            mFetcher.searchPhotos(query)
        }

        return mPhotoItemLiveData
    }

    fun cancelPhotosRequest() = mFetcher.cancelPhotosRequestInFlight()
}