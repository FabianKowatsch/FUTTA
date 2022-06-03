package com.example.futta.feature.main.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.futta.feature.day.ui.DayScreen
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
            DayScreen(date = LocalDate.parse(date))
        }
        composable(BottomNavigationItem.AddEvent.routeName) {
            Text(text=BottomNavigationItem.AddEvent.title)
        }
    }

}