package net.level3studios.bookz.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.level3studios.bookz.components.BooksTopBar
import net.level3studios.bookz.components.EmptyListView
import net.level3studios.bookz.components.SearchBarState
import net.level3studios.bookz.components.SwipeBookListItem
import net.level3studios.bookz.network.BooksModel
import net.level3studios.bookz.network.NetworkViewModel
import net.level3studios.bookz.network.ViewTypes

@Composable
fun WishlistView(viewModel: NetworkViewModel) {
    val wishlistBooks = viewModel.wishlistConvertedBooks.observeAsState().value
    val state = remember { mutableStateOf(SearchBarState.CLOSED) }
    val query = remember { mutableStateOf("") }
    val selectedBook = remember { mutableStateOf(BooksModel()) }
    val showDetails = remember { mutableStateOf(false) }

    BooksTopBar(
        title = "Wishlist",
        barState = state,
        query = query,
        placeholder = "Wishlist"
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        val savedBooks = wishlistBooks ?: emptyList()
        var books: List<BooksModel>
        if (savedBooks.isEmpty()) {
            EmptyListView(title = "Wishlist", topPadding = topPadding)
        } else {
            LazyColumn(modifier = Modifier.padding(top = topPadding)) {
                val searchText = query.value
                books = if (searchText.isEmpty()) {
                    savedBooks
                } else {
                    val filterList = ArrayList<BooksModel>()
                    for (book in savedBooks) {
                        if (book.volumeInfo?.title?.contains(
                                searchText,
                                ignoreCase = true
                            ) == true
                        ) {
                            filterList.add(book)
                        }
                    }
                    filterList
                }
                items(books) { book ->
                    SwipeBookListItem(
                        book = book,
                        selectedBook = selectedBook,
                        showDetail = showDetails,
                        currentView = ViewTypes.WISHLIST,
                        moveTarget = ViewTypes.LIBRARY,
                        viewModel = viewModel
                    )
                }
            }
            if (showDetails.value) {
                BookDetailDialog(
                    showDetails = showDetails,
                    selectedBook = selectedBook.value,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WishlistPreview() {
    WishlistView(viewModel = NetworkViewModel.modelWithContext())
}