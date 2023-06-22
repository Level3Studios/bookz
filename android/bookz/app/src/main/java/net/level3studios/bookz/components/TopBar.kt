@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package net.level3studios.bookz.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.level3studios.bookz.network.NetworkViewModel


enum class SearchBarState {
    OPENED, CLOSED
}

@Composable
fun BooksTopBar(
    title: String,
    barState: MutableState<SearchBarState>,
    query: MutableState<String>,
    placeholder: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            if (barState.value == SearchBarState.OPENED) {
                SearchViewTopBar(barState = barState, query = query, placeholder = placeholder)
            } else {
                PlainTopBar(title = title, barState = barState)
            }
        },
        content = content
    )
}

@Composable
fun PlainTopBar(
    title: String,
    barState: MutableState<SearchBarState>
) {
    val barColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = Color.White,
        actionIconContentColor = Color.White
    )
    CenterAlignedTopAppBar(title = { Text(text = title) },
        colors = barColors,
        actions = {
            IconButton(onClick = { barState.value = SearchBarState.OPENED }) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
            }
        })
}

@Composable
fun HomeTopBar(viewModel: NetworkViewModel, content: @Composable (PaddingValues) -> Unit) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val barColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = Color.White,
        navigationIconContentColor = Color.White
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed,
        confirmStateChange = { status ->
            if (status == DrawerValue.Closed) {
                focusManager.clearFocus()
            }
            true
        })

    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = { BookSearchView(drawerState, viewModel) },
        content = {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "Dashboard") },
                    colors = barColors,
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "menu")
                        }
                    })
            }, content = content)
        })
}

@Composable
fun SearchViewTopBar(
    barState: MutableState<SearchBarState>,
    query: MutableState<String>,
    placeholder: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        SearchField(barState, query = query, placeholder = placeholder)
    }
}

@Composable
fun SearchField(
    barState: MutableState<SearchBarState>,
    query: MutableState<String>,
    placeholder: String
) {

    TextField(
        value = query.value,
        onValueChange = { onQueryChange ->
            query.value = onQueryChange
        },
        placeholder = { Text(text = "Search $placeholder") },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
        },
        trailingIcon = {
            IconButton(onClick = {
                if (query.value.isNotEmpty() || query.value.isNotBlank()) {
                    query.value = ""
                } else {
                    barState.value = SearchBarState.CLOSED
                }
            }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "Clear")
            }

        },
        singleLine = true,
        shape = RectangleShape,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val state = remember { mutableStateOf(SearchBarState.OPENED) }
    val query = remember { mutableStateOf("") }
    val viewModel = NetworkViewModel.modelWithContext()
    HomeTopBar(viewModel) {
        Text(text = "Hello")
    }
}