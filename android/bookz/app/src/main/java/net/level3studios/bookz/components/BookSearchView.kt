@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package net.level3studios.bookz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.level3studios.bookz.network.BooksModel
import net.level3studios.bookz.network.NetworkViewModel
import net.level3studios.bookz.views.BookDetailDialog

@Composable
fun BookSearchView(drawerState: DrawerState, viewModel: NetworkViewModel) {
    val query = remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val searchResults = viewModel.searchResults.observeAsState().value
    val selectedBook = remember { mutableStateOf(BooksModel()) }
    val showDetail = remember { mutableStateOf(false) }

    ModalDrawerSheet() {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = query.value,
                onValueChange = { onQueryChange ->
                    query.value = onQueryChange
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search Google Books") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (query.value.text.isNotEmpty() || query.value.text.isNotBlank()) {
                            query.value = TextFieldValue("")
                        } else {
                            focusManager.clearFocus()
                            scope.launch { drawerState.close() }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "clear")
                    }
                },
                singleLine = true,
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    viewModel.performSearch(query.value.text)
                })
            )
            LazyColumn() {
                if (searchResults != null) {
                    items(searchResults) { item ->
                        BookListItem(
                            booksModel = item,
                            selectedBook = selectedBook,
                            showDetails = showDetail,
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
}

@Preview
@Composable
fun SearchViewPreview() {
    val viewModel = NetworkViewModel.modelWithContext()
    BookSearchView(drawerState = DrawerState(initialValue = DrawerValue.Closed), viewModel)
}