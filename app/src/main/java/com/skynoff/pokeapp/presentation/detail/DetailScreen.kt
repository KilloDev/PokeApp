package com.skynoff.pokeapp.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.pokemon?.let { pokemon ->
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(200.dp)
                )
                Text(text = pokemon.name, style = MaterialTheme.typography.headlineLarge)

                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Altura: ${pokemon.height / 10.0} m")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Peso: ${pokemon.weight / 10.0} kg")
                }

                pokemon.stats.forEach { stat ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text(text = stat.name.uppercase(), modifier = Modifier.weight(1f))
                        Text(text = stat.value.toString(), fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                }
            }
        }
        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}