package com.team1.wat2watch.data.repository

import com.team1.wat2watch.data.api.RetrofitInstance
import com.team1.wat2watch.data.model.MovieResponse
import retrofit2.Response

class MovieRepository {
    suspend fun fetchMovies(apiKey: String, page: Int = 1): Response<MovieResponse> {
        return RetrofitInstance.api.fetchMovies(
            apiKey = apiKey,
            page = page,
            startDate = "2020-01-01",
            endDate = "2024-12-31",
            genres = "28,12"
        )
    }
}