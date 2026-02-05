package com.skynoff.pokeapp.data.mapper

import com.skynoff.pokeapp.data.remote.dto.PokemonEntryDto
import com.skynoff.pokeapp.domain.model.Pokemon


fun PokemonEntryDto.toDomain(): Pokemon {
    val number = if (url.endsWith("/")) {
        url.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        url.takeLastWhile { it.isDigit() }
    }

    val pokemonId = number.toInt()
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"

    return Pokemon(
        id = pokemonId,
        name = name.replaceFirstChar { it.uppercase() }, // Ponemos la primera letra en mayúscula
        imageUrl = imageUrl
    )
}