package com.skynoff.pokeapp.data.remote

import com.skynoff.pokeapp.data.remote.dto.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse
}
