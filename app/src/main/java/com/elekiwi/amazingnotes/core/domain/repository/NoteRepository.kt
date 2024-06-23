package com.elekiwi.amazingnotes.core.domain.repository

import com.elekiwi.amazingnotes.core.domain.model.NoteItem

interface NoteRepository {

    suspend fun upsertNote(noteItem: NoteItem)
    suspend fun deleteNote(noteItem: NoteItem)
    suspend fun getAllNotes(): List<NoteItem>
}


