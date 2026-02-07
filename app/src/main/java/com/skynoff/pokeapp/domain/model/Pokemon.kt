package com.skynoff.pokeapp.domain.model

/**
 * * Esta clase es el "Modelo de Dominio", es la estructura
 * limpia que utiliza la interfaz de usuario (UI) para mostrar la información,
 * independientemente de cómo vengan los datos desde internet.
 */


data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String> = emptyList(),
    val height: Int = 0,
    val weight: Int = 0,
    val stats: List<PokemonStat> = emptyList()
)

data class PokemonStat(
    val name: String,
    val value: Int
)