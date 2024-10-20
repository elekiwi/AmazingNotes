package com.elekiwi.amazingnotes.note_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.amazingnotes.MainCoroutineRule
import com.elekiwi.amazingnotes.core.data.repository.FakeNoteRepository
import com.elekiwi.amazingnotes.core.domain.model.NoteItem
import com.elekiwi.amazingnotes.note_list.domain.use_case.DeleteNote
import com.elekiwi.amazingnotes.note_list.domain.use_case.GetAllNotes
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class  NoteListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNotes: GetAllNotes
    private lateinit var deleteNote: DeleteNote

    private lateinit var noteListViewModel: NoteListViewModel

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        getAllNotes = GetAllNotes(fakeNoteRepository)

        noteListViewModel = NoteListViewModel(getAllNotes, deleteNote)
    }

    @Test
    fun `get notes from an empty list, returns an empty list`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)
        noteListViewModel.loadNotes()
        assertTrue(noteListViewModel.noteListState.value.isEmpty())
    }

    @Test
    fun `get notes from a filled list, returns a list not empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(noteListViewModel.noteListState.value.isNotEmpty())
    }

    @Test
    fun `delete note from list, the note is deleted`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        val note = noteListViewModel.noteListState.value[0]

        noteListViewModel.deleteNote(note)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertFalse(noteListViewModel.noteListState.value.contains(note))
    }

    @Test
    fun `sort note by date, notes are sorted by date`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.loadNotes()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        var notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].dateAdded < notes[i + 1].dateAdded)
        }
        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].title < notes[i + 1].title)
        }
        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].dateAdded < notes[i + 1].dateAdded)
        }
    }

    @Test
    fun `sort note by title, notes are sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val notes = noteListViewModel.noteListState.value
        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].title < notes[i + 1].title)
        }

    }
}