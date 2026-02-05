package com.skynoff.pokeapp.data.remote.dto

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