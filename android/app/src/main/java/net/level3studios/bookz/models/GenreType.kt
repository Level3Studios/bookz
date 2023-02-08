package net.level3studios.bookz.models

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.level3studios.bookz.R
import net.level3studios.bookz.ui.theme.*

enum class GenreType(val id: Int) {
    ADVENTURE(0),
    FANTASY(1),
    SCIFI(2),
    ROMANCE(3);

    companion object  {
        fun getType(id: Int): GenreType {
            return when (id) {
                ADVENTURE.id -> ADVENTURE
                FANTASY.id -> FANTASY
                SCIFI.id -> SCIFI
                ROMANCE.id -> ROMANCE
                else -> ADVENTURE
            }
        }
        @Composable
        fun GenreType.getPrimaryColor(): Color {
            return when (this) {
                ADVENTURE -> MaterialTheme.colors.customBlue
                FANTASY -> MaterialTheme.colors.customOrange
                SCIFI -> MaterialTheme.colors.customGreen
                ROMANCE -> MaterialTheme.colors.customPink
            }
        }
        @Composable
        fun GenreType.getPrimaryContainer(): Color {
            return when (this) {
                ADVENTURE -> MaterialTheme.colors.customBlueContainer
                FANTASY -> MaterialTheme.colors.customOrangeContainer
                SCIFI -> MaterialTheme.colors.customGreenContainer
                ROMANCE -> MaterialTheme.colors.customPinkContainer
            }
        }

        fun GenreType.getImageId(): Int {
            return when (this) {
                ADVENTURE -> R.drawable.adventure
                FANTASY -> R.drawable.fortnite
                SCIFI -> R.drawable.alien
                ROMANCE -> R.drawable.couple
            }
        }
        fun GenreType.getDisplayLabel(): String {
            return when (this) {
                ADVENTURE -> "Adventure"
                FANTASY -> "Fantasy"
                SCIFI -> "Sci-Fi"
                ROMANCE -> "Romance"
            }
        }
        fun GenreType.getSearchString(): String {
            val option: String = when (this) {
                ADVENTURE -> "adventure"
                FANTASY -> "fantasy"
                SCIFI -> "science%20fiction"
                ROMANCE -> "romance"
            }
            return "+subject:$option"
        }
    }
}