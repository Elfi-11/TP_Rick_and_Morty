package com.example.rickandmorty.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String
)

data class Origin(val name: String)
data class Location(val name: String)