package com.elekiwi.amazingnotes.core.data.repository

import com.elekiwi.amazingnotes.core.data.mapper.toImages
import com.elekiwi.amazingnotes.core.data.remote.api.ImagesApi
import com.elekiwi.amazingnotes.core.domain.model.Images
import com.elekiwi.amazingnotes.core.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi
): ImagesRepository {
    override suspend fun searchImages(query: String): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }
}