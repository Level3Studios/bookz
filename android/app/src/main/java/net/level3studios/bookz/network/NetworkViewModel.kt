package net.level3studios.bookz.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.level3studios.bookz.models.GenreType
import net.level3studios.bookz.models.GenreType.Companion.getSearchString

class NetworkViewModel : ViewModel() {

    private var adventureBooks = mutableListOf<BooksModel>()
    private var fantasyBooks = mutableListOf<BooksModel>()
    private var sciFiBooks = mutableListOf<BooksModel>()
    private var romanceBooks = mutableListOf<BooksModel>()

    var top5Books = MutableLiveData<List<BooksModel>>()

    private fun genreNeedsSearch(type: GenreType): Boolean {
        return when (type) {
            GenreType.ADVENTURE -> adventureBooks.isEmpty()
            GenreType.FANTASY -> fantasyBooks.isEmpty()
            GenreType.SCIFI -> sciFiBooks.isEmpty()
            GenreType.ROMANCE -> romanceBooks.isEmpty()
        }
    }

    fun updateSelectedGenre(genreId: Int) {
        val genre = GenreType.getType(genreId)
        if (genreNeedsSearch(genre)) {
            NetworkLayer.searchForBooks(
                genre.getSearchString(),
                maxResults = 5
            ) { result ->
                updateSearch(genre, result)
            }
        } else {
            updateTop5(genre)
        }
    }

    private fun updateSearch(genreType: GenreType, results: List<BooksModel>) {
        when (genreType) {
            GenreType.ADVENTURE -> adventureBooks = results.toMutableList()
            GenreType.FANTASY -> fantasyBooks = results.toMutableList()
            GenreType.SCIFI -> sciFiBooks = results.toMutableList()
            GenreType.ROMANCE -> romanceBooks = results.toMutableList()
        }
        updateTop5(genreType)
    }

    private fun updateTop5(genreType: GenreType) {
        when (genreType) {
            GenreType.ADVENTURE -> top5Books.postValue(adventureBooks)
            GenreType.FANTASY -> top5Books.postValue(fantasyBooks)
            GenreType.SCIFI -> top5Books.postValue(sciFiBooks)
            GenreType.ROMANCE -> top5Books.postValue(romanceBooks)
        }
    }

}