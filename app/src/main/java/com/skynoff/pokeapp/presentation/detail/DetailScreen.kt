package com.skynoff.pokeapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.skynoff.pokeapp.ui.theme.PokedexBlue
import com.skynoff.pokeapp.ui.theme.PokedexRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.state

    Scaffold(
        containerColor = PokedexRed,
        topBar = {
            TopAppBar(
                title = { Text("DATOS POKÉMON", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            state.pokemon?.let { pokemon ->
                Column(modifier = Modifier.fillMaxSize()) {
                    // --- ÁREA DE LA IMAGEN (VISOR) ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(20.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(
                                4.dp,
                                Color(0xFF303030),
                                RoundedCornerShape(16.dp)
                            ), // Borde tipo pantalla
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = pokemon.imageUrl,
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(200.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = pokemon.name.uppercase(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF30A7D7) // Azul Pokédex
                                )
                                Text(
                                    text = "Nº ${pokemon.id.toString().padStart(3, '0')}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )
                            }

                            Row(modifier = Modifier.padding(vertical = 12.dp)) {
                                pokemon.types.forEach { type ->
                                    Surface(
                                        color = PokedexBlue,
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) {
                                        Text(
                                            text = type.uppercase(),
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 4.dp
                                            ),
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Divider(modifier = Modifier.padding(vertical = 16.dp))

                            Text(
                                "ESTADÍSTICAS BASE",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(12.dp))

                            pokemon.stats.forEach { stat ->
                                StatBar(statName = stat.name, statValue = stat.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatBar(statName: String, statValue: Int) {
    val cleanName = statName.uppercase()
        .replace("SPECIAL-", "SP. ")
        .replace("-", " ")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = cleanName,
            modifier = Modifier.weight(0.4f),
            fontSize = 11.sp,
            maxLines = 1,
            fontWeight = FontWeight.Medium
        )

        LinearProgressIndicator(
            progress = { statValue / 200f },
            modifier = Modifier
                .weight(0.6f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = if (statValue > 80) Color(0xFF4CAF50) else Color(0xFFF44336),
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Text(
            text = statValue.toString(),
            modifier = Modifier
                .width(35.dp)
                .padding(start = 8.dp),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}