package com.example.rickandmorty

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.viewModel.CharactersListViewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

val site_typo = FontFamily(Font(R.font.poppins))
val rickAndMortyFont = FontFamily(Font(R.font.rickfont))

class CharactersListActivity : ComponentActivity() {

    private val viewModel: CharactersListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                CharactersListScreen(viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersListScreen(viewModel: CharactersListViewModel) {
    val characters = viewModel.characters.observeAsState(emptyList())
    val errorMessage = viewModel.errorMessage.observeAsState()
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Rick And Morty",
                        fontSize = 50.sp,
                        fontFamily = rickAndMortyFont
                    ) },
                actions = {
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://www.primevideo.com/-/fr/detail/Rick-and-Morty/0OPM5609MNUMK1E6FAAQ0GIWMP")
                        }
                        // Lancer l'intention pour ouvrir le lien
                        context.startActivity(intent)

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
                            .width(120.dp)
                    )

                    {
                        Text(
                            text = "Saison 8",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF4CAF50).copy(alpha = 0.7f))
        ) {
            if (errorMessage.value != null) {
                Text(
                    text = errorMessage.value!!,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Red
                )
            } else {

                // Affiche la liste des personnages
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    viewModel.fetchAllCharacters()
                    items(characters.value) { character ->
                        CharacterItem(character) { selectedCharacter ->
                            val intent = Intent(context, CharacterDetailsActivity::class.java).apply {
                                putExtra("character_name", selectedCharacter.name)
                                putExtra("character_status", selectedCharacter.status)
                                putExtra("character_species", selectedCharacter.species)
                                putExtra("character_type", selectedCharacter.type)
                                putExtra("character_gender", selectedCharacter.gender)
                                putExtra("character_origin", selectedCharacter.origin.name)
                                putExtra("character_location", selectedCharacter.location.name)
                                putExtra("character_image", selectedCharacter.image)
                            }
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterItem(character: Character, onClick: (Character) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(character) }
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Image du personnage
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = "Character image",
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .size(105.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // Nom du personnage
            Text(
                text = character.name,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = site_typo,
                    color = Color(0xFF013220),
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}





