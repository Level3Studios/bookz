package net.level3studios.bookz.models

enum class GenreType(val id: Int) {
    ADVENTURE(0),
    FANTASY(1),
    SCIFI(2),
    ROMANCE(3);

    companion object  {
        private fun getType(id: Int): GenreType {
            return when (id) {
                ADVENTURE.id -> ADVENTURE
                FANTASY.id -> FANTASY
                SCIFI.id -> SCIFI
                ROMANCE.id -> ROMANCE
                else -> ADVENTURE
            }
        }
        fun getDisplayLabel(genre: Int): String {
            return when (GenreType.getType(genre)) {
                ADVENTURE -> "Adventure"
                FANTASY -> "Fantasy"
                SCIFI -> "Sci-Fi"
                ROMANCE -> "Romance"
            }
        }
        fun getSearchString(genre: Int): String {
            val option: String = when (GenreType.getType(genre)) {
                ADVENTURE -> "adventure"
                FANTASY -> "fantasy"
                SCIFI -> "science%20fiction"
                ROMANCE -> "romance"
            }
            return "+subject:$option"
        }
    }
}