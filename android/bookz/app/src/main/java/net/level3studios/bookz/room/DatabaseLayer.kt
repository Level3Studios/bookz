package net.level3studios.bookz.room

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

//region Book Entity
@Parcelize
@Entity(tableName = "bookzContainer")
data class SavedBook(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "type")
    var type: Int
) : Parcelable
//endregion

//region Database DAO
@Dao
interface SavedBooksDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBook(savedBook: SavedBook)

    @Query("SELECT * FROM bookzContainer WHERE type = :viewType")
    fun getSavedBooks(viewType: Int): List<SavedBook>

    @Update
    suspend fun updateBook(book: SavedBook)

    @Delete
    suspend fun deleteBook(book: SavedBook)
}
//endregion

//region Database
@Database(entities = [(SavedBook::class)], version = 1, exportSchema = false)
abstract class SavedBookDatabase : RoomDatabase() {
    abstract fun bookDAO(): SavedBooksDAO

    companion object {
        @Volatile
        private var Instance: SavedBookDatabase? = null

        fun getInstance(context: Context): SavedBookDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SavedBookDatabase::class.java,
                        "saved_books"
                    ).fallbackToDestructiveMigration().build()
                    Instance = instance
                }
                return instance
            }
        }
    }

}
//endregion

//region Database Repository
class BooksRepository(private val booksDAO: SavedBooksDAO) {

    val libraryBooks = MutableLiveData<List<SavedBook>>()
    val wishlistBooks = MutableLiveData<List<SavedBook>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        updateData()
    }

    fun updateData() {
        getLibraryBooks()
        getWishlistBooks()
    }

    fun saveBook(book: SavedBook) {
        coroutineScope.launch(Dispatchers.IO) {
            booksDAO.addBook(book)
            updateData()
        }
    }

    fun deleteBook(book: SavedBook) {
        coroutineScope.launch(Dispatchers.IO) {
            booksDAO.deleteBook(book)
            updateData()
        }
    }

    fun updateBook(book: SavedBook) {
        coroutineScope.launch(Dispatchers.IO) {
            booksDAO.updateBook(book)
            updateData()
        }
    }

    private fun getLibraryBooks() {
        coroutineScope.launch(Dispatchers.IO) {
            libraryBooks.postValue(booksDAO.getSavedBooks(0))
        }
    }

    private fun getWishlistBooks() {
        coroutineScope.launch(Dispatchers.IO) {
            wishlistBooks.postValue(booksDAO.getSavedBooks(1))
        }
    }

}
//endregion