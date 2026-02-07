package com.skynoff.pokeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skynoff.pokeapp.data.local.dao.PokemonDao
import com.skynoff.pokeapp.data.local.entities.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}