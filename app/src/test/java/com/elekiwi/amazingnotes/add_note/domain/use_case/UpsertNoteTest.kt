package com.elekiwi.amazingnotes.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.amazingnotes.MainCoroutineRule
import com.elekiwi.amazingnotes.core.data.repository.FakeNoteRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertNoteTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var upsertNote: UpsertNote

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        upsertNote = UpsertNote(fakeNoteRepository)
    }

    @Test
    fun `upsert note with empty title, retuns false`() = runTest {
         val isInserted = upsertNote.invoke(
              title = "",
             description = "description",
             imageUrl = "image"
         )
        
        assertFalse(isInserted)
    }

    @Test
    fun `upsert note with empty description, retuns false`() = runTest {
        val isInserted = upsertNote.invoke(
            title = "title",
            description = "",
            imageUrl = "image"
        )

        assertFalse(isInserted)
    }

    @Test
    fun `upsert a valid, retuns true `() = runTest {
        val isInserted =   upsertNote.invoke(
            title = "title",
            description = "description",
            imageUrl = "image"
        )

        assertTrue(isInserted)
    }
}