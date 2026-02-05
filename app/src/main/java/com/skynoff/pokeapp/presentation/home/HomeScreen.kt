package com.skynoff.pokeapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.skynoff.pokeapp.presentation.home.components.PokemonItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pokemonItems = viewModel.pokemonList.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pokemonItems.itemCount) { index ->
                val pokemon = pokemonItems[index]
                pokemon?.let { PokemonItem(pokemon = it) }
            }
            if (pokemonItems.loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }

        if (pokemonItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (pokemonItems.loadState.refresh is LoadState.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Ocurrió un error al cargar los datos")
                Button(onClick = { pokemonItems.retry() }) {
                    Text(text = "Reintentar")
                }
            }
        }
    }
}