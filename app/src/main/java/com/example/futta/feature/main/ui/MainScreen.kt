package com.example.futta.feature.main.ui

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.futta.domain.DeleteEventUseCase
import com.example.futta.domain.model.EventId
import com.example.futta.feature.main.navigation.*
import com.example.futta.feature.main.navigation.BottomNavigationItem.*
import kotlinx.coroutines.launch
import com.example.futta.R

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    MainScreenUI(viewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenUI(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            FuttaTopBar(viewModel, navController) {
                if (modalState.isVisible)
                    scope.launch { modalState.hide() }
                else
                    scope.launch { modalState.show() }
            }
        },
        bottomBar = {
            MainBottomNavigation(navController)
        },
        floatingActionButton = {
            ActionButton(viewModel, navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ModalBottomSheetLayout(
                scrimColor = Color(getColor(context, R.color.scrim_color)),
                sheetState = modalState,
                sheetContent = {
                    BottomSheet(
                        viewModel,
                        navController,
                        onOptionSelect = { scope.launch { modalState.hide() } })
                }) {
                MainNavigationGraph(navController, viewModel)
            }
        }

    }
}

@Composable
fun FuttaTopBar(
    viewModel: MainViewModel,
    navController: NavController,
    onOptionsClick: () -> Unit
) {
    val context = LocalContext.current
    val route by viewModel.currentRoute.observeAsState()
    TopAppBar(
        title = { Text(context.getString(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.secondary,
        navigationIcon = {
            when (route) {
                Month.routeName, "" -> {
                    IconButton(
                        onClick = { },
                        content = {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        })
                }
                else -> {
                    IconButton(
                        onClick = { navigateTo(navController, Month.routeName) },
                        content = {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Back"
                            )
                        })
                }
            }
        },
        actions = {
            IconButton(
                onClick = { onOptionsClick() },
                content = {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "options"
                    )
                })
        })
}

@Composable
fun ActionButton(viewModel: MainViewModel, navController: NavController) {
    val route by viewModel.currentRoute.observeAsState()
    when (route) {
        Month.routeName, Day.routeName, Week.routeName -> FloatingActionButton(
            onClick = { navigateTo(navController, NavigationItem.AddEvent.routeName) },
            content = {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
            }
        )
        else -> {}
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    viewModel: MainViewModel,
    navController: NavController,
    onOptionSelect: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val route by viewModel.currentRoute.observeAsState()
    val optionsList: List<SheetOption> = when (route) {
        NavigationItem.Event.routeName -> BottomSheetOptions.EventScreen.options
        else -> BottomSheetOptions.DefaultScreen.options
    }
    LazyColumn {
        items(optionsList) { option ->
            ListItem(
                icon = { Icon(option.icon, option.name) },
                text = { Text(text = option.name) },
                modifier = Modifier.clickable(onClick = {
                    onOptionSelect()
                    when (option) {
                        is SheetOption.Delete -> {
                            val currentEventId =
                                navController.currentBackStackEntry?.arguments?.getString("eventId")
                            scope.launch { currentEventId?.let { DeleteEventUseCase()(EventId(it)) } }
                        }
                        is SheetOption.Edit -> {
                            val currentEventId =
                                navController.currentBackStackEntry?.arguments?.getString("eventId")
                            currentEventId?.let {
                                navigateTo(
                                    navController, NavigationItem.UpdateEvent.createRoute(
                                        EventId(currentEventId)
                                    )
                                )
                            }

                        }
                        is SheetOption.Settings -> {
                            navigateTo(navController, NavigationItem.Settings.routeName)
                        }
                    }

                })
            )
        }
    }
}