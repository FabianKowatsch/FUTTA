package com.example.futta.feature.main.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.futta.R
import java.time.LocalDate

@Composable
fun MainBottomNavigation(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        listOf(
            BottomNavigationItem.Month,
            BottomNavigationItem.Day,
            BottomNavigationItem.Week
        ).forEach { navItem ->
            BottomNavigationItem(
                modifier = Modifier.background(MaterialTheme.colors.secondary),
                selected = currentRoute == navItem.routeName,
                icon = {
                       Icon(navItem.icon, "", modifier = Modifier.padding(0.dp, 2.dp))

                },
                 label = { Text(navItem.title) },
                onClick = {
                    val route = when (navItem.routeName) {
                        BottomNavigationItem.Day.routeName -> BottomNavigationItem.Day.createRoute("${LocalDate.now()}")
                        else -> navItem.routeName
                    }
                        navigateTo(navController, route)
                },
            )
        }
    }
}
fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = false
    }
}
