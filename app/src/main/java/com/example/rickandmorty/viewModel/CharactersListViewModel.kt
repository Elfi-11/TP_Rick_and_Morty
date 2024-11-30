package com.example.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.network.RickAndMortyApiService
import com.example.rickandmorty.network.RetrofitInstance
import kotlinx.coroutines.launch


class CharactersListViewModel(
    private val apiService: RickAndMortyApiService = RetrofitInstance.retrofitService
) : ViewModel() {

    // LiveData contenant la liste des personnages récupérés depuis l'API.
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    // LiveData contenant le message d'erreur, utilisé pour notifier l'UI en cas de problème.
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    //afficher tous les personnages via retrofit et mettre le messsage d'erreur à jour
    fun fetchAllCharacters(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = apiService.getCharacters(page)
                if (response.isSuccessful) {
                    _characters.value = response.body()?.results ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _characters.value = emptyList()
                    _errorMessage.value = "Erreur : ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _characters.value = emptyList()
                _errorMessage.value = "Une erreur s'est produite : ${e.message}"
            }
        }
    }

    //afficher le message d'erreur
    private fun handleError(message: String) {
        _errorMessage.value = message
        Log.e("CharactersListViewModel", message)
    }

    // Réinitialise le message derreur
    fun resetErrorMessage() {
        _errorMessage.value = null
    }

}
