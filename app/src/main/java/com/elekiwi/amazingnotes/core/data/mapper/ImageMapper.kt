package com.elekiwi.amazingnotes.core.data.mapper

import com.elekiwi.amazingnotes.core.data.remote.dto.ImageListDto
import com.elekiwi.amazingnotes.core.domain.model.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { imageDto ->
            imageDto.imageURL ?: ""
        } ?: emptyList()
    )
}