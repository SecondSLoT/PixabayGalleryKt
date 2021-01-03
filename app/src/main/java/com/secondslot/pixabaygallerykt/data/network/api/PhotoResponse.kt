package com.secondslot.pixabaygallerykt.data.network.api

import com.google.gson.annotations.SerializedName
import com.secondslot.pixabaygallerykt.data.model.PhotoItem

class PhotoResponse {
    @SerializedName("hits")
    lateinit var photoItems: List<PhotoItem>
}