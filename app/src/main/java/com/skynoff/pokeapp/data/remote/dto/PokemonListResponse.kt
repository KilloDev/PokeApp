package com.skynoff.pokeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonEntryDto(
    val id: Int,
    val name: String,
    val url: String,
    val sprites: SpritesDto,
    val height: Int,
    val weight: Int
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