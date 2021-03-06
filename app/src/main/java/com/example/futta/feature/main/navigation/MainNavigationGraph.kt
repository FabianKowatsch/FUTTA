package com.example.futta.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.futta.domain.model.EventId
import com.example.futta.feature.addEvent.ui.AddEventScreen
import com.example.futta.feature.addEvent.ui.UpdateEventScreen
import com.example.futta.feature.day.ui.DayScreen
import com.example.futta.feature.event.ui.CalendarEventScreen
import com.example.futta.feature.main.ui.MainViewModel
import com.example.futta.feature.month.ui.MonthScreenUi
import com.example.futta.feature.settings.ui.SettingsScreen
import com.example.futta.feature.week.ui.WeekScreenUi
import java.time.LocalDate

@Composable
fun MainNavigationGraph(navController: NavHostController, viewModel: MainViewModel) {

    NavHost(navController, startDestination = BottomNavigationItem.Month.routeName) {
        composable(BottomNavigationItem.Month.routeName) {
            viewModel.currentRoute.value = BottomNavigationItem.Month.routeName
            MonthScreenUi(navController)
        }
        composable(BottomNavigationItem.Week.routeName) {
            viewModel.currentRoute.value = BottomNavigationItem.Week.routeName
            WeekScreenUi(navController)
        }
        composable(route = BottomNavigationItem.Day.routeName) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            requireNotNull(date) { "date parameter wasn't found. Please make sure it's set!" }
            viewModel.currentRoute.value = BottomNavigationItem.Day.routeName
            DayScreen(date = LocalDate.parse(date), navController = navController)
        }
        composable(route = NavigationItem.Event.routeName) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            requireNotNull(eventId) { "eventId parameter wasn't found. Please make sure it's set!" }
            viewModel.currentRoute.value  = NavigationItem.Event.routeName
            CalendarEventScreen(eventId = EventId(eventId), navController = navController)
        }
        composable(NavigationItem.AddEvent.routeName) {
            viewModel.currentRoute.value = NavigationItem.AddEvent.routeName
            AddEventScreen(navController = navController)
        }
        composable(route = NavigationItem.UpdateEvent.routeName) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            requireNotNull(eventId) { "eventId parameter wasn't found. Please make sure it's set!" }
            viewModel.currentRoute.value  = NavigationItem.UpdateEvent.routeName
            UpdateEventScreen(eventId = EventId(eventId), navController = navController)
        }
        composable(NavigationItem.Settings.routeName) {
            viewModel.currentRoute.value = NavigationItem.AddEvent.routeName
            SettingsScreen(viewModel = viewModel)
        }
    }

}