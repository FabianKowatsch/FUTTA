package com.example.futta.feature.main.navigation

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
            BottomNavigationItem.AddEvent
        ).forEach { navItem ->
            BottomNavigationItem(
                    modifier = Modifier.background(colorResource(id = R.color.green_3100)),
                selected = currentRoute == navItem.routeName,
                icon = {
                       Icon(navItem.icon, contentDescription = null)
                },
               label = { Text(navItem.title)},
                onClick = {
                   val route =  when(navItem.routeName){
                        BottomNavigationItem.Day.routeName -> BottomNavigationItem.Day.createRoute("${LocalDate.now()}")
                       else -> navItem.routeName
                    }
                    navController.navigate(route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
            )
        }
    }
}