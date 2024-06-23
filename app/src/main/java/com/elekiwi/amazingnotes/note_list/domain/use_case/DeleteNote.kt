package com.elekiwi.amazingnotes.note_list.domain.use_case

import com.elekiwi.amazingnotes.core.domain.model.NoteItem
import com.elekiwi.amazingnotes.core.domain.repository.NoteRepository

class DeleteNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: NoteItem) {
        noteRepository.deleteNote(note)
    }

}