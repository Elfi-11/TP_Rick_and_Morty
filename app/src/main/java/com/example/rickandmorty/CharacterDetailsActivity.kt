package com.example.rickandmorty


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily






class CharacterDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val name = intent.getStringExtra("character_name") ?: "Inconnu"
            val status = intent.getStringExtra("character_status") ?: "Inconnu"
            val species = intent.getStringExtra("character_species") ?: "Inconnu"
            val type = intent.getStringExtra("character_type") ?: "Inconnu"
            val gender = intent.getStringExtra("character_gender") ?: "Inconnu"
            val origin = intent.getStringExtra("character_origin") ?: "Inconnu"
            val location = intent.getStringExtra("character_location") ?: "Inconnu"
            val imageUrl = intent.getStringExtra("character_image") ?: ""

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = name,
                                fontSize = 50.sp,
                                fontFamily = rickAndMortyFont
                            ) },
                        actions = {
                            Button(
                                onClick = {
                                    finish() // Ferme l'activité actuelle
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4CAF50),
                                    contentColor = Color.Black,
                                    disabledContainerColor = Color.Gray,
                                    disabledContentColor = Color.DarkGray
                                ),
                                shape = RoundedCornerShape(12.dp), // Coins arrondis
                                modifier = Modifier
                                    .padding(6.dp)
                                    .height(40.dp)
                                    .width(90.dp)
                            )

                            {
                                Text(
                                    text = "Liste",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                    )
                                )

                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black,
                            titleContentColor = Color(0xFF4CAF50)
                        )
                    )
                }
            ) { paddingValues ->

                // Contenu principal
                CharacterDetailsScreen(
                    status = status,
                    species = species,
                    type = type,
                    gender = gender,
                    origin = origin,
                    location = location,
                    imageUrl = imageUrl,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}

@Composable
fun TextLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
        .padding(start = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = rickAndMortyFont,
                color = Color(0xFF013220)
            )
        )
        Text(
            text = " $value",
            style = TextStyle(
                fontWeight = FontWeight.ExtraLight,
                fontSize = 23.sp,
                fontFamily = site_typo,
                color = Color(0xFF1A661A)
            )
        )
    }
}

@Composable
fun CharacterDetailsScreen(
    status: String,
    species: String,
    type: String,
    gender: String,
    origin: String,
    location: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50).copy(alpha = 0.7f))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image du personnage
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            onError = {
                println("Error loading image: ${it.result.throwable.message}") // Log de l’erreur
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(190.dp)
                .shadow(20.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Image(
                painter = painter,
                contentDescription = "Character image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Lignes de détails
        TextLine(label = "Statut :", value = status)
        TextLine(label = "Espèce :", value = species)
        TextLine(label = "Type :", value = type)
        TextLine(label = "Genre :", value = gender)
        TextLine(label = "Origine :", value = origin)
        TextLine(label = "Localisation :", value = location)
    }
}

