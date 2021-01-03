package com.secondslot.pixabaygallerykt.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.secondslot.pixabaygallerykt.data.model.PhotoItem
import com.secondslot.pixabaygallerykt.data.network.api.PhotoInterceptor
import com.secondslot.pixabaygallerykt.data.network.api.PhotoResponse
import com.secondslot.pixabaygallerykt.data.network.api.PixabayApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "Fetcher"

class Fetcher {

    private val mPixabayApi: PixabayApi
    private lateinit var mPixabayRequest: Call<PhotoResponse>

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        mPixabayApi = retrofit.create(PixabayApi::class.java)
    }

    fun fetchPhotos(): LiveData<List<PhotoItem>> {
        return fetchPhotoMetadata(mPixabayApi.fetchPhotos())
    }

    fun searchPhotos(query: String): LiveData<List<PhotoItem>> {
        return fetchPhotoMetadata(mPixabayApi.searchPhotos(query))
    }

    fun fetchPhotoMetadata(photoRequest: Call<PhotoResponse>): LiveData<List<PhotoItem>> {
        val responseLiveData: MutableLiveData<List<PhotoItem>> = MutableLiveData()

        mPixabayRequest = photoRequest
        mPixabayRequest.enqueue(object : Callback<PhotoResponse> {

            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.e(TAG, "Не удалось загрузить фотографии", t)
            }

            override fun onResponse(
                call: Call<PhotoResponse>,
                response: Response<PhotoResponse>
            ) {
                Log.d(TAG, "Ответ получен")

                val photoResponse: PhotoResponse? = response.body()
                val photoItems: List<PhotoItem> = photoResponse?.photoItems
                    ?: mutableListOf()
                responseLiveData.value = photoItems
            }
        })

        return responseLiveData
    }

    // Отменяет запрос фотографий по сети
    fun cancelPhotosRequestInFlight() {
        if (::mPixabayRequest.isInitialized) {
            mPixabayRequest.cancel()
        }
    }

}