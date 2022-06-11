package com.example.futta.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.futta.feature.main.navigation.*
import com.example.futta.feature.main.navigation.BottomNavigationItem.*
import com.example.futta.feature.month.ui.MonthScreenUi
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    viewModel.bindUi().observeAsState()
    MainScreenUI(viewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenUI(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            MainBottomNavigation(navController) {

                if (modalState.isVisible)
                    scope.launch { modalState.hide() }
                else
                    scope.launch { modalState.show() }
            }
        },
        floatingActionButton = {
            ActionButton(viewModel, navController)
        }
    ) { innerPadding ->
        ModalBottomSheetLayout(
            sheetState = modalState,
            sheetContent = { BottomSheet(viewModel) })
        {
            Box(modifier = Modifier.padding(innerPadding)) {
                MainNavigationGraph(navController, viewModel)

            }
        }

    }
}

@Composable
fun ActionButton(viewModel: MainViewModel, navController: NavController) {
    val route by viewModel.currentRoute.observeAsState()
    when (route) {
        Month.routeName, Day.routeName -> FloatingActionButton(
            onClick = { navigateToAddEventScreen(navController) },
            content = {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
            }
        )
        else -> {}
    }

}

private fun navigateToAddEventScreen(navController: NavController) {
    navController.navigate(NavigationItem.AddEvent.routeName) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = false
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(viewModel: MainViewModel) {
    val route by viewModel.currentRoute.observeAsState()
    val optionsList: List<SheetOption> = when (route) {
        NavigationItem.Event.routeName -> BottomSheetOptions.EventScreen.options
        else -> BottomSheetOptions.DefaultScreen.options
    }
    LazyColumn {
        items(optionsList) { option ->
            ListItem(
                icon = { Icon(option.icon, option.name) },
                text = { Text(text = option.name) }
            )
        }
    }
    Card() {
        Text(text = "Test")
    }


}