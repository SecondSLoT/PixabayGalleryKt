package com.secondslot.pixabaygallerykt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.secondslot.pixabaygallerykt.data.model.PhotoItem
import com.secondslot.pixabaygallerykt.data.Repository

private const val TAG = "GalleryViewModel"

class GalleryViewModel : ViewModel() {

    private val mRepository: Repository = Repository.get()
    val mPhotoItemLiveData: LiveData<List<PhotoItem>>
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        fetchPhotos()
        mPhotoItemLiveData =
            Transformations.switchMap(mutableSearchTerm) { searchTerm ->
                mRepository.getPhotos(searchTerm)
            }
    }

    fun fetchPhotos(query: String = "") {
        mutableSearchTerm.value = query
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.cancelPhotosRequest()
    }
}