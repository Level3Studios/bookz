package net.level3studios.bookz.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.level3studios.bookz.models.GenreType
import net.level3studios.bookz.models.GenreType.Companion.getDisplayLabel
import net.level3studios.bookz.models.GenreType.Companion.getImageId
import net.level3studios.bookz.models.GenreType.Companion.getPrimaryContainer
import net.level3studios.bookz.ui.theme.BookzTheme

@Composable
fun GenreCardView(genre: GenreType) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = genre.getPrimaryContainer()),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        modifier = Modifier
            .width(320.dp)
            .height(240.dp)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painterResource(id = genre.getImageId()),
                contentDescription = genre.getDisplayLabel()
            )
            Text(
                text = genre.getDisplayLabel().uppercase(),
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                fontSize = 22.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardPreview() {
    BookzTheme(useDarkTheme = true) {
        GenreCardView(genre = GenreType.ADVENTURE)
    }
}