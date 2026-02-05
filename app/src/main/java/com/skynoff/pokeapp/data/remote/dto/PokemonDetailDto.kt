package com.skynoff.pokeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: SpritesDto,
    val types: List<TypeEntryDto>,
    val stats: List<StatEntryDto>
)

data class SpritesDto(
    val other: OtherSpritesDto
)

data class OtherSpritesDto(
    @SerializedName("official-artwork")
    val officialArtwork: ArtworkDto
)

data class ArtworkDto(
    @SerializedName("front_default")
    val frontDefault: String
)

data class TypeEntryDto(
    val type: TypeDetailDto
)

data class TypeDetailDto(
    val name: String
)

data class StatEntryDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatDetailDto
)

data class StatDetailDto(
    val name: String
)
