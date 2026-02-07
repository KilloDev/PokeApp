package com.skynoff.pokeapp.domain.repository

import androidx.paging.PagingData
import com.skynoff.pokeapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<PagingData<Pokemon>>

    suspend fun getPokemonInfo(name: String): Result<Pokemon>

    fun getFavorites(): Flow<List<Pokemon>>
    fun isFavorite(id: Int): Flow<Boolean>
    suspend fun toggleFavorite(pokemon: Pokemon, isFavorite: Boolean)

}