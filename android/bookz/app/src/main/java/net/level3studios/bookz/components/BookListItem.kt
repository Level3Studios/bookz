@file:OptIn(ExperimentalMaterial3Api::class)

package net.level3studios.bookz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.level3studios.bookz.R
import net.level3studios.bookz.network.BooksModel
import net.level3studios.bookz.network.NetworkViewModel
import net.level3studios.bookz.network.ViewTypes
import net.level3studios.bookz.network.sampleBook
import net.level3studios.bookz.ui.theme.BookzTheme

@Composable
fun SwipeBookListItem(
    book: BooksModel,
    selectedBook: MutableState<BooksModel>,
    showDetail: MutableState<Boolean>,
    currentView: ViewTypes,
    moveTarget: ViewTypes,
    viewModel: NetworkViewModel
) {
    val dismissState = rememberDismissState(initialValue = DismissValue.Default)
    if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
        viewModel.moveBook(book, moveTarget)
    } else if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        viewModel.deleteBook(book, currentView)
    }
    SwipeToDismiss(
        state = dismissState,
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
                DismissDirection.EndToStart -> Color.Red
                null -> Color.Transparent
            }
            if (dismissState.dismissDirection == DismissDirection.StartToEnd) {
                SwipeMoveBox(color = color, text = moveTarget.display())
            } else if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                SwipeDeleteBox(color = color)
            }
        },
        directions = setOf(
            DismissDirection.EndToStart,
            DismissDirection.StartToEnd
        ),
        dismissContent = {
            BookListItem(booksModel = book, selectedBook, showDetail, viewModel)
        })
}

@Composable
fun BookListItem(
    booksModel: BooksModel,
    selectedBook: MutableState<BooksModel>,
    showDetails: MutableState<Boolean>,
    viewModel: NetworkViewModel
) {
    Row(modifier = Modifier
        .padding(4.dp)
        .background(MaterialTheme.colorScheme.inverseOnSurface)
        .clickable {
            selectedBook.value = booksModel
            showDetails.value = true
            viewModel.selectedBook(bookId = booksModel.id!!)
        }) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = booksModel.volumeInfo?.title!!,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                booksModel.volumeInfo.authors?.let {
                    Text(
                        text = it.joinToString(separator = ","),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                }
                Text(
                    text = booksModel.volumeInfo.publishedDate!!,
                    color = MaterialTheme.colorScheme.surfaceTint
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booksModel.volumeInfo.averageRatingString,
                    fontWeight = FontWeight.Light, color = Color.Gray
                )
                Icon(
                    painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = "star",
                    modifier = Modifier.height(22.dp),
                    tint = Color.Yellow
                )
                Text(
                    "(${booksModel.volumeInfo.ratingsCountString})",
                    fontWeight = FontWeight.Light, color = Color.Gray
                )
            }
            Text(
                text = booksModel.volumeInfo.description!!,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3
            )
            Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SwipeDeleteBox(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "trash",
            tint = Color.White
        )
    }
}

@Composable
fun SwipeMoveBox(color: Color, text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Move",
                tint = Color.White
            )
            Text(
                text = "Move to $text",
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    val selectedBook = remember {
        mutableStateOf(BooksModel().sampleBook())
    }
    val showDetails = remember { mutableStateOf(false) }
    val viewModel = NetworkViewModel.modelWithContext()
    BookzTheme(useDarkTheme = false) {
        BookListItem(
            booksModel = BooksModel().sampleBook(),
            selectedBook,
            showDetails,
            viewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BoxPreview() {
    SwipeMoveBox(color = Color.Green, text = "Wishlist")
}