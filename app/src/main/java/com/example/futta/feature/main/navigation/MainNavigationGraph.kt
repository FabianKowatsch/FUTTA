package com.example.futta.feature.main.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.futta.domain.model.EventId
import com.example.futta.feature.addEvent.ui.AddEventScreen
import com.example.futta.feature.day.ui.DayScreen
import com.example.futta.feature.event.ui.CalendarEventScreen
import com.example.futta.feature.month.ui.MonthScreenUi
import java.time.LocalDate

@Composable
fun MainNavigationGraph(navController: NavHostController) {

    NavHost(navController, startDestination = BottomNavigationItem.Month.routeName) {
        composable(BottomNavigationItem.Month.routeName) {
            MonthScreenUi(navController)
        }
        composable(route = BottomNavigationItem.Day.routeName) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            requireNotNull(date) { "date parameter wasn't found. Please make sure it's set!" }
            DayScreen(date = LocalDate.parse(date), navController = navController)
        }
        composable(route = NavigationItem.Event.routeName) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            requireNotNull(eventId) { "eventId parameter wasn't found. Please make sure it's set!" }
            CalendarEventScreen(eventId = EventId(eventId))
        }
        composable(BottomNavigationItem.AddEvent.routeName) {
            AddEventScreen()
        }
    }

}