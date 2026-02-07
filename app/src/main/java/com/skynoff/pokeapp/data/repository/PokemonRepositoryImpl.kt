package com.skynoff.pokeapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skynoff.pokeapp.data.local.dao.PokemonDao
import com.skynoff.pokeapp.data.mapper.toDomain
import com.skynoff.pokeapp.data.mapper.toEntity
import com.skynoff.pokeapp.data.remote.PokeApi
import com.skynoff.pokeapp.data.remote.PokemonPagingSource
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementación del Repositorio de Pokémon.
 * * La Función principal de esta clase es abstraer el origen de los datos
 * para que el resto de la aplicación no tenga que preocuparse de dónde vienen.
 */

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi,
    private val dao: PokemonDao
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(api) }
        ).flow
    }

    override suspend fun getPokemonInfo(name: String): Result<Pokemon> {
        return try {
            val response = api.getPokemonDetail(name)
            Result.success(response.toDomain())
        } catch (e: Exception) { Result.failure(e) }
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return dao.isFavorite(id)
    }

    override suspend fun toggleFavorite(pokemon: Pokemon, isFavorite: Boolean) {
        if (isFavorite) {
            dao.deleteFavorite(pokemon.toEntity())
        } else {
            dao.insertFavorite(pokemon.toEntity())
        }
    }

    override fun getFavorites(): Flow<List<Pokemon>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
