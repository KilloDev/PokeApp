package com.skynoff.pokeapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    init {
        savedStateHandle.get<String>("pokemonName")?.let { name ->
            getPokemonDetails(name)
        }
    }

    private fun getPokemonDetails(name: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.getPokemonInfo(name)

            result.onSuccess { pokemon ->
                state = state.copy(
                    pokemon = pokemon,
                    isLoading = false,
                    error = null
                )
            }.onFailure { error ->
                state = state.copy(
                    pokemon = null,
                    isLoading = false,
                    error = error.message ?: "Ocurrió un error desconocido"
                )
            }
        }
    }

    data class DetailState(
        val pokemon: Pokemon? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}