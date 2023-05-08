package com.example.okrprojectfinal.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.okrprojectfinal.MockData
import com.example.okrprojectfinal.TestCoroutineRule
import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private val mockData = MockData.mockMoviesList()

    private val mockedFlow: Flow<List<Movie>?> = flow {
        emit(mockData)
    }

    private val testDispatcher = StandardTestDispatcher()
    @Mock
    lateinit var repository: MovieRepository

    @get:Rule
    val instantTaskExecutorRule= InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
    }
    @Test
    fun test_getPopularMovies()= runTest{
        val result = MockData.mockMoviesList()
        val mockData = MovieResponse(result, "null")
        Mockito.`when`(repository.getPopularMovies()).thenReturn(NetworkResult.Success(mockData))

        val SUT = HomeViewModel(repository)
        SUT.getPopularMovies()
        testDispatcher.scheduler.advanceUntilIdle()
        val movieResponse = SUT.movieResponse
        Assert.assertEquals(1, movieResponse.value!!.data!!.results.size)
    }

    @After
    fun tearsDown(){
        Dispatchers.resetMain()
    }

}