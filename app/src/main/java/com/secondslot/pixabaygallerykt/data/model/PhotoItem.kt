package com.secondslot.pixabaygallerykt.data.model

import com.google.gson.annotations.SerializedName
import com.secondslot.pixabaygallerykt.R

data class PhotoItem(
    @SerializedName("user") var author: String = "",
    var likes: String = "",
    var previewURL: String = "",
    var webformatURL: String = "", // 640px default, 960px max
    var largeImageURL: String = "", // scaled to max 1280px
    @SerializedName("userImageURL") var authorImageURL: String = ""
)