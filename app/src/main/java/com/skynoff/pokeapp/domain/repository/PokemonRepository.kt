package com.skynoff.pokeapp.domain.repository

import androidx.paging.PagingData
import com.skynoff.pokeapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<PagingData<Pokemon>>

    suspend fun getPokemonInfo(name: String): Result<Pokemon>
}