package com.elekiwi.amazingnotes.core.domain.repository

import com.elekiwi.amazingnotes.core.domain.model.Images

interface ImagesRepository {
     suspend fun searchImages(query: String): Images?
}