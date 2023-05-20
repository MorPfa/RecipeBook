package com.example.recipebook.data

interface ListItem

data class Title(
    val title: String
) : ListItem

data class Recipe(
    val title: String,
    val description: String,
    val flavor: Flavor
) : ListItem

enum class Flavor {
    SAVORY,
    SWEET
}
