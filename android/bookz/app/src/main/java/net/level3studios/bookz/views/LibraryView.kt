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
fun LibraryView(viewModel: NetworkViewModel) {
    val libraryBooks = viewModel.libraryConvertedBooks.observeAsState().value
    val state = remember { mutableStateOf(SearchBarState.CLOSED) }
    val query = remember { mutableStateOf("") }
    val selectedBook = remember { mutableStateOf(BooksModel()) }
    val showDetail = remember { mutableStateOf(false) }

    BooksTopBar(
        title = "Library",
        barState = state,
        query = query,
        placeholder = "Library"
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        val savedBooks = libraryBooks ?: emptyList()
        var books: List<BooksModel>
        if (savedBooks.isEmpty()) {
            EmptyListView(title = "Library", topPadding = topPadding)
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
                        showDetail = showDetail,
                        currentView = ViewTypes.LIBRARY,
                        moveTarget = ViewTypes.WISHLIST,
                        viewModel = viewModel
                    )

                    if (showDetail.value) {
                        BookDetailDialog(
                            showDetails = showDetail,
                            selectedBook = selectedBook.value,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LibraryPreview() {
    LibraryView(viewModel = NetworkViewModel.modelWithContext())
}