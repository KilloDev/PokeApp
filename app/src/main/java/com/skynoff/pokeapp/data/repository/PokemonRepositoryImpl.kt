package com.skynoff.pokeapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skynoff.pokeapp.data.mapper.toDomain
import com.skynoff.pokeapp.data.remote.PokeApi
import com.skynoff.pokeapp.data.remote.PokemonPagingSource
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(api)
            }
        ).flow
    }

    override suspend fun getPokemonInfo(name: String): Result<Pokemon> {
        return try {
            val response = api.getPokemonDetail(name)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
