package com.elekiwi.amazingnotes.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.amazingnotes.core.data.repository.FakeNoteRepository
import com.elekiwi.amazingnotes.core.domain.model.NoteItem
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteNoteTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        fakeNoteRepository.shouldHaveFilledList(true)

    }

    @Test
    fun `delete notes from list, note is deleted`() = runTest {
        val note = NoteItem("c title 2", "desc 2", "url2", 2)
        deleteNote.invoke(note)

        assertFalse(fakeNoteRepository.getAllNotes().contains(note))
    }
}