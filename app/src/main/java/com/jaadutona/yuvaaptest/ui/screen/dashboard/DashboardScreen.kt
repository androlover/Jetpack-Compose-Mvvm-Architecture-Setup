import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jaadutona.yuvaaptest.R
import com.jaadutona.yuvaaptest.ui.navigation.Route
import com.jaadutona.yuvaaptest.ui.screen.about.AboutScreen
import com.jaadutona.yuvaaptest.ui.screen.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Logo"
                    )
                },
                actions = {
                    IconButton(onClick = { /* profile click */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = Route.HOME.toString()) },
                    selected = currentDestination?.route == Route.HOME.toString(),
                    onClick = {
                        bottomNavController.navigate(Route.HOME.toString()) {
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = Route.ABOUT.toString()
                        )
                    },
                    selected = currentDestination?.route == Route.ABOUT.toString(),
                    onClick = {
                        bottomNavController.navigate(Route.ABOUT.toString()) {
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Route.HOME.toString(),
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.HOME.toString()) { HomeScreen() }
            composable(Route.ABOUT.toString()) { AboutScreen() }
        }
    }
}


