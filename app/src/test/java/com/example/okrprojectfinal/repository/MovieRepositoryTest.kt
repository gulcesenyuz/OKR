package com.example.okrprojectfinal.repository

import com.example.okrprojectfinal.MockData
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.data.service.MovieApi
import com.example.okrprojectfinal.data.utils.Credentials.API_KEY
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MovieRepositoryTest {
    private val mockDataSingle = MockData.mockMovieList()
    private val mockData = MockData.mockMoviesList()

    @Mock
    lateinit var apiService : MovieApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test_getPopularMovies_empty() = runTest{
        val mock = MovieResponse(listOf())
        Mockito.`when`(apiService.getPopularMovies(API_KEY)).thenReturn(Response.success(mock))
        val sut = MovieRepository(apiService)
        val result = sut.getPopularMovies()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(0, result.data?.results?.size)
    }

    @Test
    fun test_getPopularMovies_one_item() = runTest{
        val mockData = MovieResponse(mockDataSingle)
        Mockito.`when`(apiService.getPopularMovies(API_KEY)).thenReturn(Response.success(mockData))
        val sut = MovieRepository(apiService)
        val result = sut.getPopularMovies()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(1, result.data?.results?.size)
        Assert.assertEquals("Ad Astra", result.data?.results?.get(0)?.title)
    }
    @Test
    fun test_getPopularMovies_items() = runTest{
        val mockData = MovieResponse(mockData)
        Mockito.`when`(apiService.getPopularMovies(API_KEY)).thenReturn(Response.success(mockData))
        val sut = MovieRepository(apiService)
        val result = sut.getPopularMovies()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(3, result.data?.results?.size)
    }
    @Test
    fun test_getPopularMovies_expectedError() = runTest{
        Mockito.`when`(apiService.getPopularMovies(API_KEY)).thenReturn(Response.error(401, "Something went wrong".toResponseBody()))
        val sut = MovieRepository(apiService)
        val result = sut.getPopularMovies()
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something went wrong", result.message)
    }
}