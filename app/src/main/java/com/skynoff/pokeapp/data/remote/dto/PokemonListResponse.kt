package com.skynoff.pokeapp.data.remote.dto


/**
 * Esta data class representa la respuesta global de la API al solicitar la lista de Pokémon.
 * * Diseñada para soportar paginación, indicando cuántos Pokémon existen en total.
 */

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonEntryDto>
)

data class PokemonEntryDto(
    val name: String,
    val url: String
)