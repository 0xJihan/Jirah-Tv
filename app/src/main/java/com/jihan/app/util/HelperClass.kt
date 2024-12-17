package com.jihan.app.util

import androidx.compose.ui.graphics.Color
import com.jihan.app.local.Channel
import kotlinx.serialization.json.Json

fun getCategories(list: List<Channel>): List<String> {

    val categories = mutableListOf<String>()

    list.forEachIndexed { index, channel ->
        categories.add(channel.category)
    }

    return categories.distinct()

}


fun getChannelsByCategory(category: String, list: List<Channel>): List<Channel> {
    return list.filter { it.category == category }
}

fun String.toTitleCase(): String {
    return trim().lowercase().replaceFirstChar { it.uppercase() }

}

val json = Json { ignoreUnknownKeys = true }

fun generateRandomMaterialColor(): Color {
    val red = (0..255).random()
    val green = (0..255).random()
    val blue = (0..255).random()
    return Color(red, green, blue, 150)
}


