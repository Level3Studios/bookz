package net.level3studios.bookz.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.level3studios.bookz.R

@Composable
fun EmptyListView(title: String, topPadding: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = R.drawable.baseline_menu_book_24),
            contentDescription = "Book",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
        Text(text = "No Books found in $title")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyListPreview() {
    EmptyListView(title = "Preview", topPadding = 12.dp)
}