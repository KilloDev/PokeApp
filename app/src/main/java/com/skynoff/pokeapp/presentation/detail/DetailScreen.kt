package com.skynoff.pokeapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.error?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.align(Alignment.Center))
        }

        state.pokemon?.let { pokemon ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(250.dp)
                    )
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pokemon.name,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "#${pokemon.id.toString().padStart(3, '0')}",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Gray
                        )
                    }

                    Row(modifier = Modifier.padding(vertical = 12.dp)) {
                        pokemon.types.forEach { type ->
                            SuggestionChip(
                                onClick = {},
                                label = { Text(type.uppercase()) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InfoItem(label = "PESO", value = "${pokemon.weight / 10.0} kg")
                        InfoItem(label = "ALTURA", value = "${pokemon.height / 10.0} m")
                    }

                    Text(
                        text = "Estadísticas Base",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                    )

                    pokemon.stats.forEach { stat ->
                        StatBar(statName = stat.name, statValue = stat.value)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
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
            modifier = Modifier.width(35.dp).padding(start = 8.dp),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, color = Color.Gray, fontSize = 12.sp)
    }
}