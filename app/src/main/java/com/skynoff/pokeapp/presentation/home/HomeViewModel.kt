package com.skynoff.pokeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.skynoff.pokeapp.data.local.AuthManager
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.repository.PokemonRepository
import com.skynoff.pokeapp.domain.use_case.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val repository: PokemonRepository
) : ViewModel() {
    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog = _showLogoutDialog.asStateFlow()

    val favoriteIds: StateFlow<Set<Int>> = repository.getFavorites()
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    fun toggleFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            val isFavorite = favoriteIds.value.contains(pokemon.id)
            repository.toggleFavorite(pokemon, isFavorite)
        }
    }

    fun onLogoutClick() {
        _showLogoutDialog.value = true
    }

    fun onDismissLogout() {
        _showLogoutDialog.value = false
    }

    fun onConfirmLogout() {
        _showLogoutDialog.value = false
        viewModelScope.launch {
            authManager.logout()
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val pokemonList = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            getPokemonListUseCase().map { pagingData ->
                if (query.isBlank()) pagingData
                else pagingData.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}