package com.elekiwi.amazingnotes.core.domain.model

data class NoteItem(
    val id: Int = 0,
    var title: String,
    var description: String,
    var imageUrl: String,
    val dateAdded: Long
)
