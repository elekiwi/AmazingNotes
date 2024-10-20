package com.elekiwi.amazingnotes.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elekiwi.amazingnotes.add_note.presentation.util.Resource
import com.elekiwi.amazingnotes.core.data.repository.FakeImagesRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SearchImagesTest {

    @get:Rule
    var instantTasKExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var searchImages: SearchImages

    @Before
    fun setup() {
        fakeImagesRepository = FakeImagesRepository()
        searchImages = SearchImages(fakeImagesRepository)
    }

    @Test
    fun `search images with empty query, returns error`() = runTest {
        searchImages.invoke("")
            .collect { result ->
                when (result) {
                    is Resource.Error -> {
                        assertTrue(result.data?.images == null)
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> Unit
                }
            }
    }

    @Test
    fun `search images with valid query but network error , returns error`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)
        searchImages.invoke("")
            .collect { result ->
                when (result) {
                    is Resource.Error -> {
                        assertTrue(result.data?.images == null)
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> Unit
                }
            }
    }

    @Test
    fun `search images with valid query, returns success`() = runTest {
        searchImages.invoke("query")
            .collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        assertTrue(result.data?.images != null)
                    }
                }
            }
    }

    @Test
    fun `search images with valid query, list is not empty`() = runTest {
        searchImages.invoke("query")
            .collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        assertTrue(result.data?.images?.size!! > 0 )
                    }
                }
            }
    }
}






