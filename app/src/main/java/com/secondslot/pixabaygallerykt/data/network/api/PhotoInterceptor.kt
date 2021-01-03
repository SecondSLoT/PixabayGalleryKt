package com.secondslot.pixabaygallerykt.data.network.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "18572416-4acf866e66a04d50a9de59f7a"
private const val TAG = "PhotoInterceptor"

class PhotoInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newUrl: HttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter("key", API_KEY)
            .addQueryParameter("safesearch", "true")
            .addQueryParameter("per_page", "99")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}