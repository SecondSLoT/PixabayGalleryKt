package com.secondslot.pixabaygallerykt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.secondslot.pixabaygallerykt.data.Repository
import com.secondslot.pixabaygallerykt.data.model.PhotoItem

class PhotoSlidePagerViewModel : ViewModel() {

    private val mRepository: Repository = Repository.get()
    val mPhotoItemLiveData: LiveData<List<PhotoItem>> = mRepository.mPhotoItemLiveData
}