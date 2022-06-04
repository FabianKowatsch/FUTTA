package com.example.futta.feature.main.navigation

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.futta.R

@Composable
fun MainBottomNavigation(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        listOf(
            BottomNavigationItem.Month,
            BottomNavigationItem.Day,
            BottomNavigationItem.AddEvent
        ).forEach { navItem ->
            BottomNavigationItem(
                    modifier = Modifier.background(colorResource(id = R.color.green_3100)),
                selected = currentRoute == navItem.routeName,
                icon = {
                    Text(text = navItem.title)
                },
                onClick = {
                    navController.navigate(navItem.routeName) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}