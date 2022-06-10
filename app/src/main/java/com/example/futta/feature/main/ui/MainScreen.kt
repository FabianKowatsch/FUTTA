package com.example.futta.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.futta.R
import com.example.futta.domain.model.CalendarEvent
import com.example.futta.feature.main.navigation.BottomNavigationItem
import com.example.futta.feature.main.navigation.BottomNavigationItem.*
import com.example.futta.feature.main.navigation.MainBottomNavigation
import com.example.futta.feature.main.navigation.MainNavigationGraph
import com.example.futta.feature.main.navigation.NavigationItem
import com.example.futta.feature.month.ui.MonthScreenUi

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    viewModel.bindUi().observeAsState()
    MainScreenUI(viewModel)
}

@Composable
fun MainScreenUI(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MainBottomNavigation(navController) },
        floatingActionButton = {
            ActionButton(viewModel, navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavigationGraph(navController, viewModel)

        }
    }
}

@Composable
fun ActionButton(viewModel: MainViewModel, navController: NavController) {
    val route by viewModel.currentRoute.observeAsState()
    when (route) {
        Month.routeName, Day.routeName-> FloatingActionButton(
            onClick = { navigateToAddEventScreen(navController) },
            content = {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
            }
        )
        else -> {}
    }

}
private fun navigateToAddEventScreen(navController: NavController) {
    navController.navigate(AddEvent.routeName) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = false
    }
}