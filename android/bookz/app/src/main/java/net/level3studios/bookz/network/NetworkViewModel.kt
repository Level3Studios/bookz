package net.level3studios.bookz.network

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.level3studios.bookz.models.GenreType
import net.level3studios.bookz.models.GenreType.Companion.getSearchString
import net.level3studios.bookz.room.BooksRepository
import net.level3studios.bookz.room.SavedBook
import net.level3studios.bookz.room.SavedBookDatabase

enum class ViewTypes(val id: Int) {
    LIBRARY(0), WISHLIST(1);

    fun display(): String {
        return when (this) {
            LIBRARY -> "Library"
            WISHLIST -> "Wishlist"
        }
    }
}

class NetworkViewModel(application: Application) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var adventureBooks = mutableListOf<BooksModel>()
    private var fantasyBooks = mutableListOf<BooksModel>()
    private var sciFiBooks = mutableListOf<BooksModel>()
    private var romanceBooks = mutableListOf<BooksModel>()
    private var librarySavedBooks: LiveData<List<SavedBook>>
    private var wishlistSavedBooks: LiveData<List<SavedBook>>

    private val databaseRepository: BooksRepository

    var top5Books = MutableLiveData<List<BooksModel>>()
    var isBookSaved = MutableLiveData(false)
    var libraryConvertedBooks = MutableLiveData<List<BooksModel>>()
    var wishlistConvertedBooks = MutableLiveData<List<BooksModel>>()
    var searchResults = MutableLiveData<List<BooksModel>>()

    init {
        val database = SavedBookDatabase.getInstance(application)
        val bookDao = database.bookDAO()
        databaseRepository = BooksRepository(bookDao)

        librarySavedBooks = databaseRepository.libraryBooks
        wishlistSavedBooks = databaseRepository.wishlistBooks
        updateLocalDB()
    }

    companion object {
        @Composable
        fun modelWithContext(): NetworkViewModel {
            val context = LocalContext.current.applicationContext
            return NetworkViewModel(context as Application)
        }
    }

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

    fun selectedBook(bookId: String) {
        val libraryCheck = librarySavedBooks.value?.any { it.id == bookId } ?: false
        val wishlistCheck = wishlistSavedBooks.value?.any { it.id == bookId } ?: false
        val check = arrayOf(libraryCheck, wishlistCheck).contains(true)
        isBookSaved.postValue(check)
    }

    fun getBookCoverSearch(bookId: String): String {
        return NetworkLayer.fetchImageSearch(bookId)
    }

    private fun updateLocalDB() {
        databaseRepository.updateData()
    }

    fun addBookToView(bookId: String, viewType: ViewTypes) {
        val newBook = SavedBook(bookId, viewType.id)
        databaseRepository.saveBook(newBook)
        isBookSaved.postValue(true)
        updateList(viewType)
    }

    fun updateList(viewType: ViewTypes) {
        when (viewType) {
            ViewTypes.LIBRARY -> {
                val books = librarySavedBooks.value ?: emptyList()
                for (book in books) {
                    NetworkLayer.fetchBook(bookId = book.id) { result ->
                        addBookToList(result, viewType)
                    }
                }
            }

            ViewTypes.WISHLIST -> {
                coroutineScope.launch(Dispatchers.IO) {
                    val books = wishlistSavedBooks.value ?: emptyList()
                    for (book in books) {
                        NetworkLayer.fetchBook(bookId = book.id) { result ->
                            addBookToList(result, viewType)
                        }
                    }
                }
            }
        }
    }

    private fun addBookToList(booksModel: BooksModel, viewType: ViewTypes) {
        when (viewType) {
            ViewTypes.LIBRARY -> {
                val books = libraryConvertedBooks.value ?: emptyList()
                if (!(books.any { it.id == booksModel.id })) {
                    val updated = books.plus(booksModel)
                    libraryConvertedBooks.postValue(updated)
                }
            }
            ViewTypes.WISHLIST -> {
                val books = wishlistConvertedBooks.value ?: emptyList()
                if (!(books.any { it.id == booksModel.id })) {
                    val updated = books.plus(booksModel)
                    wishlistConvertedBooks.postValue(updated)
                }
            }
        }
    }

    fun deleteBook(book: BooksModel, viewType: ViewTypes) {
        when (viewType) {
            ViewTypes.LIBRARY -> {
                val books = libraryConvertedBooks.value ?: emptyList()
                val updated = books.minus(book)
                libraryConvertedBooks.postValue(updated)
                val savedBook = librarySavedBooks.value?.find { it.id == book.id }
                if (savedBook != null) {
                    databaseRepository.deleteBook(savedBook)
                }
            }
            ViewTypes.WISHLIST -> {
                val books = wishlistConvertedBooks.value ?: emptyList()
                val updated = books.minus(book)
                wishlistConvertedBooks.postValue(updated)
                val savedBook = wishlistSavedBooks.value?.find { it.id == book.id }
                if (savedBook != null) {
                    databaseRepository.deleteBook(savedBook)
                }
            }
        }
    }

    fun moveBook(book: BooksModel, toView: ViewTypes) {
        when (toView) {
            ViewTypes.LIBRARY -> {
                val libraryBooks = libraryConvertedBooks.value ?: emptyList()
                val wishlistBooks = wishlistConvertedBooks.value ?: emptyList()
                val reduced = wishlistBooks.minus(book)
                val increased = libraryBooks.plus(book)
                libraryConvertedBooks.postValue(increased)
                wishlistConvertedBooks.postValue(reduced)
                val savedBook = wishlistSavedBooks.value?.find { it.id == book.id }
                if (savedBook != null) {
                    savedBook.type = toView.id
                    databaseRepository.updateBook(savedBook)
                }
            }
            ViewTypes.WISHLIST -> {
                val libraryBooks = libraryConvertedBooks.value ?: emptyList()
                val wishlistBooks = wishlistConvertedBooks.value ?: emptyList()
                val reduced = libraryBooks.minus(book)
                val increased = wishlistBooks.plus(book)
                libraryConvertedBooks.postValue(reduced)
                wishlistConvertedBooks.postValue(increased)
                val savedBook = librarySavedBooks.value?.find { it.id == book.id }
                if (savedBook != null) {
                    savedBook.type = toView.id
                    databaseRepository.updateBook(savedBook)
                }
            }
        }
    }

    fun performSearch(query: String) {
        NetworkLayer.searchForBooks(query, maxResults = 30) { result ->
            searchResults.postValue(result)
        }
    }
}