package com.skynoff.pokeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.skynoff.pokeapp.data.local.AuthManager
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.use_case.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            authManager.logout()
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val pokemonList: Flow<PagingData<Pokemon>> = _searchQuery.debounce(300).flatMapLatest { query ->
        getPokemonListUseCase().map { pagingData ->
            if (query.isBlank()) pagingData
            else pagingData.filter { pokemon ->
                pokemon.name.contains(query, ignoreCase = true)
            }
        }
    }
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}