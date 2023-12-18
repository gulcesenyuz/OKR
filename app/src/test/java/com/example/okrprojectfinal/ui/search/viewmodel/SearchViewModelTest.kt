package com.example.okrprojectfinal.ui.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.okrprojectfinal.MockData
import com.example.okrprojectfinal.TestCoroutineRule
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.repository.MovieRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest{
    private val mockData = MockData.mockMovieList()
    private val testDispatcher = StandardTestDispatcher()
    @Mock
    lateinit var repository: MovieRepository
    // executes each task synchronously
    @get:Rule
    val instantTaskExecutorRule= InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun test_searchList_hasItem()= runTest{
        val mockData = MovieResponse(mockData)
        Mockito.`when`(repository.searchMovie("jack",1)).thenReturn(NetworkResult.Success(mockData))
        val sut = SearchViewModel(repository)
        sut.getSearchResultMovies("jack",1)
        testDispatcher.scheduler.advanceUntilIdle()
        val movieResponse = sut.movieResponse
        assertEquals(1, movieResponse.value!!.data!!.results.size)
    }

}