package net.level3studios.bookz.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LibraryView() { 
    Text(text = "Library Page")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LibraryPreview() {
    LibraryView()
}