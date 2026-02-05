package com.skynoff.pokeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skynoff.pokeapp.domain.model.Pokemon
import com.skynoff.pokeapp.domain.use_case.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    val pokemonList: Flow<PagingData<Pokemon>> = getPokemonListUseCase()
        .cachedIn(viewModelScope)
}