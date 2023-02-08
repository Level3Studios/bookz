package net.level3studios.bookz.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WishlistView() { 
    Text(text = "Wishlist View")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WishlistPreview() {
    WishlistView()
}