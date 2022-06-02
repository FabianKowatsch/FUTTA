package com.example.futta.feature.main.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.futta.feature.month.ui.MonthScreenUi

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavigationItem.Month.routeName) {
        composable(BottomNavigationItem.Month.routeName) {
            MonthScreenUi(navController)
        }
        composable(BottomNavigationItem.Day.routeName) {
            Text(text=BottomNavigationItem.Day.title)
        }
        composable(BottomNavigationItem.AddEvent.routeName) {
            Text(text=BottomNavigationItem.AddEvent.title)
        }
    }
}