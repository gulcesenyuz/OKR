package com.example.okrprojectfinal.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.okrprojectfinal.data.model.Movie
import com.example.okrprojectfinal.data.model.response.MovieResponse
import com.example.okrprojectfinal.data.model.response.NetworkResult
import com.example.okrprojectfinal.repository.MovieRepository
import com.example.okrprojectfinal.ui.adapter.MovieAdapter
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    @Inject
    lateinit var movieAdapter: MovieAdapter

    private var _movieResponse = MutableLiveData<NetworkResult<MovieResponse>>()
    val movieResponse: LiveData<NetworkResult<MovieResponse>>
        get() = _movieResponse

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    private val _movieLiveData = MutableLiveData<MovieResponse>()
    val movieLiveData: LiveData<MovieResponse>
        get() = _movieLiveData

    private val db = FirebaseFirestore.getInstance()

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            val result = repository.getPopularMovies()
            _movieResponse.postValue(result)
        }
    }

    fun storeMovieToDatabase(movies: List<Movie>) {
        val totalMovies = movies.size
        var processedMovies = 0
        for (movieItem in movies) {
            val movie = HashMap<String, Any>()
            movie["originalTitle"] = movieItem.originalTitle.toString()
            movie["overview"] = movieItem.overview.toString()
            movie["popularity"] = movieItem.popularity.toString()
            movie["releaseDate"] = movieItem.releaseDate.toString()
            db.collection("popular_movies").document(movieItem.originalTitle.toString()).set(movie)
                .addOnSuccessListener {
                    processedMovies++
                    if (processedMovies == totalMovies) {
                        _isSuccess.value = true
                    }
                }.addOnFailureListener {
                    processedMovies++
                    if (processedMovies == totalMovies) {
                        _isSuccess.value = false
                    }
                }
        }
    }

    //Method 1: Snapshot
    fun fetchPopularMovies() {
        val docReference = db.collection("popular_movies")
        docReference.get().addOnSuccessListener { document ->
            document?.let {
                // Log.d(TAG, "DocumentSnapshot data: " + document.documents)
                var movieList = mutableListOf<Movie>()
                for (item in document) {
                    val data = item.toObject(Movie::class.java)
                    movieList.add(data)
                }
                val response = MovieResponse(movieList)
                _movieLiveData.value = response
            }
        }
            .addOnFailureListener { e ->
                Log.d("Failure", e.toString())
            }

        /* val docReference = db.collection("popular_movies")
         docReference.addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->
             if (e != null) {
                 Log.d("Failure",  e.toString())
                 return@EventListener
             }
             if (snapshot != null) {
                 Log.d("Success", snapshot.documents.toString())
             } else {
                 Log.d("Success", "Current data: null")
             }
         })*/
    }

    fun deleteADocument(id: String) {
        // val docReference = db.collection("popular_movies").document(id) -> id of a movie that will be deleted
        val docReference = db.collection("popular_movies").document("Dragon Kingdom")
        docReference.delete()
    }

    fun deleteAFieldInDocument(){
        val docReference = db.collection("popular_movies").document("Dragon Kingdom")
        val deleteField = HashMap<String, Any>()
        //write the field that will be deleted
        deleteField["popularity"] = FieldValue.delete()
        docReference.update(deleteField)
    }

}
