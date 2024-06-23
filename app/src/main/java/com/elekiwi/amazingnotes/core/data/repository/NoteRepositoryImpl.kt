package com.elekiwi.amazingnotes.core.data.repository

import com.elekiwi.amazingnotes.core.data.local.NoteDb
import com.elekiwi.amazingnotes.core.data.mapper.toNoteEntityForDelete
import com.elekiwi.amazingnotes.core.data.mapper.toNoteEntityForInsert
import com.elekiwi.amazingnotes.core.data.mapper.toNoteItem
import com.elekiwi.amazingnotes.core.domain.model.NoteItem
import com.elekiwi.amazingnotes.core.domain.repository.NoteRepository

class NoteRepositoryImpl(
    noteDb: NoteDb
): NoteRepository {

    private val noteDao = noteDb.noteDao
    override suspend fun upsertNote(noteItem: NoteItem) {
        noteDao.upsertNoteEntity(noteItem.toNoteEntityForInsert())
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteDao.deleteNoteEntity(noteItem.toNoteEntityForDelete())
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteDao.getAllNoteEntities().map { it.toNoteItem() }
    }
}