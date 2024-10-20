package com.elekiwi.amazingnotes.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.amazingnotes.MainCoroutineRule
import com.elekiwi.amazingnotes.add_note.domain.use_case.SearchImages
import com.elekiwi.amazingnotes.add_note.domain.use_case.UpsertNote
import com.elekiwi.amazingnotes.core.data.repository.FakeImagesRepository
import com.elekiwi.amazingnotes.core.data.repository.FakeNoteRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var addNoteViewModel: AddNoteViewModel

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        fakeImagesRepository = FakeImagesRepository()
        val upsertNote = UpsertNote(fakeNoteRepository)
        val searchImages = SearchImages(fakeImagesRepository)
        addNoteViewModel = AddNoteViewModel(upsertNote, searchImages)
    }

    @Test
    fun `upsert note with empty title, retuns false`() = runTest {
        val isInserted = addNoteViewModel.upsertNote(
            title = "",
            description = "description",
            imageUrl = "image"
        )

        assertFalse(isInserted)
    }

    @Test
    fun `upsert note with empty description, retuns false`() = runTest {
        val isInserted = addNoteViewModel.upsertNote(
            title = "title",
            description = "",
            imageUrl = "image"
        )

        assertFalse(isInserted)
    }

    @Test
    fun `upsert a valid, retuns true`() = runTest {
        val isInserted =  addNoteViewModel.upsertNote  (
            title = "title",
            description = "description",
            imageUrl = "image"
        )

        assertTrue(isInserted)
    }

    @Test
    fun `search image with empty query, image list is empty`() = runTest {
        addNoteViewModel.searchImages("")

        assertTrue(addNoteViewModel.addNoteState.value.imageList.isEmpty())
    }

    @Test
    fun `search image with valid query but with error, image list is empty`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)

        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(addNoteViewModel.addNoteState.value.imageList.isEmpty())
    }

    @Test
    fun `search image with valid query, image list is not empty`() = runTest {
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(addNoteViewModel.addNoteState.value.imageList.isNotEmpty())
    }
}