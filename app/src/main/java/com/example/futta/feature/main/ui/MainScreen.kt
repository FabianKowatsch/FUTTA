package com.example.futta.feature.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.futta.domain.model.CalendarEvent
import com.example.futta.feature.main.navigation.BottomNavigationItem.*
import com.example.futta.feature.main.navigation.MainBottomNavigation
import com.example.futta.feature.main.navigation.MainNavigationGraph
import com.example.futta.feature.month.ui.MonthScreenUi

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    viewModel.bindUi().observeAsState()
    MainScreenUI()
}

@Composable
fun MainScreenUI() {
    //MonthScreenUi()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            TopAppBar(
                title = {
                    when (currentRoute) {
                        Month.routeName -> Text(Month.title)
                        Day.routeName -> Text(Day.title)
                    }
                },
            )
        },
        bottomBar = { MainBottomNavigation(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavigationGraph(navController)
        }
    }
}
