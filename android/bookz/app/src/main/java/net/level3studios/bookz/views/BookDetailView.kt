package net.level3studios.bookz.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import net.level3studios.bookz.R
import net.level3studios.bookz.network.BooksModel
import net.level3studios.bookz.network.NetworkViewModel
import net.level3studios.bookz.network.ViewTypes
import net.level3studios.bookz.network.sampleBook
import net.level3studios.bookz.ui.theme.BookzTheme

@Composable
fun BookDetailDialog(
    showDetails: MutableState<Boolean>,
    selectedBook: BooksModel,
    viewModel: NetworkViewModel
) {
    Dialog(onDismissRequest = { showDetails.value = false }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f),
            color = MaterialTheme.colorScheme.surfaceTint
        ) {
            BookDetailView(booksModel = selectedBook, viewModel = viewModel)
        }
    }
}

@Composable
fun BookDetailView(booksModel: BooksModel, viewModel: NetworkViewModel) {
    val textColor = MaterialTheme.colorScheme.inverseOnSurface
    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.inversePrimary
    )
    val scrollState = rememberScrollState(0)
    val bookSaved = viewModel.isBookSaved.observeAsState().value

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceTint),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (bookSaved == false) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        viewModel.addBookToView(
                            booksModel.id!!,
                            viewType = ViewTypes.LIBRARY
                        )
                    },
                    colors = buttonColor
                ) {
                    Text(text = "+ Library")
                }
                Button(
                    onClick = {
                        viewModel.addBookToView(
                            booksModel.id!!,
                            viewType = ViewTypes.WISHLIST
                        )
                    },
                    colors = buttonColor
                ) {
                    Text(text = "+ Wishlist")
                }
            }
        }
        AsyncImage(
            model = viewModel.getBookCoverSearch(booksModel.id!!),
            placeholder = painterResource(id = R.drawable.baseline_image_24),
            contentDescription = "book cover",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
        )
        Text(
            text = booksModel.volumeInfo?.title!!,
            color = textColor
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = booksModel.volumeInfo.authorList,
                color = textColor
            )
            Text(
                text = booksModel.volumeInfo.publishedDate!!,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = booksModel.volumeInfo.categoryList,
            color = textColor,
            fontWeight = FontWeight.Light
        )
        Text(
            text = booksModel.volumeInfo.description!!,
            modifier = Modifier.verticalScroll(scrollState),
            color = textColor
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookDetailPreview() {
    BookzTheme(useDarkTheme = false) {
        BookDetailView(
            booksModel = BooksModel().sampleBook(),
            viewModel = NetworkViewModel.modelWithContext()
        )
    }
}