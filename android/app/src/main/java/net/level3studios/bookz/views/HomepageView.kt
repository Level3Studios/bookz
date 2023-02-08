package net.level3studios.bookz.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import net.level3studios.bookz.components.BookListItem
import net.level3studios.bookz.components.GenreCardView
import net.level3studios.bookz.models.GenreType
import net.level3studios.bookz.network.NetworkViewModel
import net.level3studios.bookz.ui.theme.BookzTheme


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePageView(viewModel: NetworkViewModel) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        val top5Books = viewModel.top5Books.observeAsState().value
        val pagerState = rememberPagerState()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                viewModel.updateSelectedGenre(page)
            }
        }
        Column {
            HorizontalPager(
                modifier = Modifier.height(330.dp),
                count = GenreType.values().size,
                state = pagerState
            ) { item ->
                GenreCardView(genre = GenreType.getType(item))
            }
            LazyColumn(
                contentPadding = PaddingValues(8.dp)
            ) {
                if (!top5Books.isNullOrEmpty()) {
                    items(top5Books) { book ->
                        Row(modifier = Modifier
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .clickable {
                                Log.i("Clicked Row", "clicked on ${book.id}")
                            }) {
                            BookListItem(booksModel = book)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    BookzTheme(useDarkTheme = true) {
        HomePageView(viewModel = NetworkViewModel())
    }
}