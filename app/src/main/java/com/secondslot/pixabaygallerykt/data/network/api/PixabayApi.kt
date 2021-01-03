package com.secondslot.pixabaygallerykt.data.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("?")
    fun fetchPhotos(): Call<PhotoResponse>

    @GET("?")
    fun searchPhotos(@Query("q") query: String): Call<PhotoResponse>
}