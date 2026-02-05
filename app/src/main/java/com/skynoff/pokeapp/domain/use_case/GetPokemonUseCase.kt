package com.skynoff.pokeapp.domain.use_case

import com.skynoff.pokeapp.domain.repository.PokemonRepository
import javax.inject.Inject


class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke() = repository.getPokemonList()
}