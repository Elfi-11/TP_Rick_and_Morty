package com.example.rickandmorty.network

import com.example.rickandmorty.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<ApiResponse>
}