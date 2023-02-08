package net.level3studios.bookz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.level3studios.bookz.R
import net.level3studios.bookz.network.BooksModel
import net.level3studios.bookz.network.sampleBook
import net.level3studios.bookz.ui.theme.BookzTheme

@Composable
fun BookListItem(booksModel: BooksModel) {
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

@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    BookzTheme(useDarkTheme = false) {
        BookListItem(booksModel = BooksModel().sampleBook())
    }
}