package com.skynoff.pokeapp.data.mapper

import com.skynoff.pokeapp.data.local.entities.PokemonEntity
import com.skynoff.pokeapp.data.remote.dto.PokemonDetailDto
import com.skynoff.pokeapp.data.remote.dto.PokemonEntryDto
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.model.PokemonStat

/**
 * Traductores de Datos: Estas funciones convierten la información que
 * viene de internet en objetos que nuestra aplicación entiende y puede dibujar.
 */

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
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = imageUrl
    )
}

fun PokemonDetailDto.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = sprites.other.officialArtwork.frontDefault,
        height = height,
        weight = weight,
        types = types.map { it.type.name },
        stats = stats.map {
            PokemonStat(
                name = it.stat.name,
                value = it.baseStat
            )
        }
    )
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}