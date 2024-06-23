package com.elekiwi.amazingnotes.note_list.domain.use_case

import com.elekiwi.amazingnotes.core.domain.model.NoteItem
import com.elekiwi.amazingnotes.core.domain.repository.NoteRepository

class GetAllNotes(
    private val notesRepository: NoteRepository
) {

    suspend operator fun invoke(
        isOrderByTitle: Boolean
    ) : List<NoteItem> {
        return if (isOrderByTitle) {
            notesRepository.getAllNotes().sortedBy { it.title.lowercase() }
        } else {
            notesRepository.getAllNotes().sortedBy { it.dateAdded }
        }
    }
}