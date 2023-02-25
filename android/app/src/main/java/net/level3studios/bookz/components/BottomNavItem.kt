package net.level3studios.bookz.components

import net.level3studios.bookz.R
import net.level3studios.bookz.network.ViewTypes

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var route: String,
    var viewTypes: ViewTypes? = null
) {
    object Home : BottomNavItem("Home", R.drawable.baseline_home_24, "home")
    object Library : BottomNavItem(
        "Library",
        R.drawable.baseline_library_books_24,
        "library",
        viewTypes = ViewTypes.LIBRARY
    )

    object Wishlist : BottomNavItem(
        "Wishlist",
        R.drawable.baseline_diamond_24,
        "Wishlist",
        viewTypes = ViewTypes.WISHLIST
    )
}