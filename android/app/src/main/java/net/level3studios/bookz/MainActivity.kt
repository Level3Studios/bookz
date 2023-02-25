package net.level3studios.bookz

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.level3studios.bookz.components.BottomNavItem
import net.level3studios.bookz.network.NetworkViewModel
import net.level3studios.bookz.ui.theme.BookzTheme
import net.level3studios.bookz.views.HomePageView
import net.level3studios.bookz.views.LibraryView
import net.level3studios.bookz.views.WishlistView

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel =
                NetworkViewModel.modelWithContext()
            BookzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MainContainer(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer(navController: NavHostController, viewModel: NetworkViewModel) {
    Scaffold(bottomBar = {
        BottomNavigationBar(
            navController = navController,
            viewModel = viewModel
        )
    }) { padding ->
        NavigationGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, viewModel: NetworkViewModel) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Library,
        BottomNavItem.Wishlist
    )

    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        items.forEach { item ->
            val currentRoute = backStackEntry?.destination?.route
            val isSelected = currentRoute == item.route
            val tintColor = if (isSelected) Color.White else Color.Black
            BottomNavigationItem(icon = {
                Icon(
                    painterResource(id = item.icon),
                    contentDescription = item.title,
                    tint = tintColor
                )
            },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        color = tintColor
                    )
                },
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    item.viewTypes?.let { viewModel.updateList(it) }
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute.let { route ->
                            if (route != null) {
                                popUpTo(route) {
                                    saveState = true
                                }

                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: NetworkViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomePageView(viewModel)
        }
        composable(BottomNavItem.Library.route) {
            LibraryView(viewModel)
        }
        composable(BottomNavItem.Wishlist.route) {
            WishlistView(viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BookzTheme(useDarkTheme = false) {
        MainContainer(
            navController = rememberNavController(),
            viewModel = NetworkViewModel.modelWithContext()
        )
    }
}