package com.elekiwi.amazingnotes.note_list.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.amazingnotes.core.data.repository.FakeNoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllNotesTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNotes: GetAllNotes

    @Before
    fun setUp(){
        fakeNoteRepository = FakeNoteRepository()
        getAllNotes = GetAllNotes(fakeNoteRepository)

    }

    @Test
    fun `get notes sort by title, sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = getAllNotes.invoke(true)

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].title < notes[i + 1].title)
            //assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes sort by date added, sorted by date added`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = getAllNotes.invoke(false)

        for (i in 0..notes.size - 2) {
            assertTrue(notes[i].dateAdded < notes[i + 1].dateAdded)
            //assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes from empty list, empty list`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)
        val notes = getAllNotes.invoke(true)

        //assertThat(notes.isEmpty())
        assertTrue(notes.isEmpty())
    }
}