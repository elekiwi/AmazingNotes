package com.elekiwi.amazingnotes.core.data.remote.api

import android.provider.SyncStateContract.Constants
import com.elekiwi.amazingnotes.core.data.remote.dto.ImageListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi  {

    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") apiKey: String = API_KEY
    ): ImageListDto?

    companion object {
        const val BASE_URL = "https://pixabay.com"
        const val API_KEY = "26068644-4f41ddc6880abbfcbdbc389ae"
    }
}