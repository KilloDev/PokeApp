package com.skynoff.pokeapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skynoff.pokeapp.data.mapper.toDomain
import com.skynoff.pokeapp.domain.model.Pokemon

class PokemonPagingSource(
    private val api: PokeApi
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val offset = params.key ?: 0
            val response = api.getPokemonList(limit = params.loadSize, offset = offset)
            val pokemonList = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = pokemonList,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (pokemonList.isEmpty()) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
