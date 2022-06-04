package com.example.futta.feature.main.navigation

import com.example.futta.R

sealed class NavigationItem {
    abstract val routeName: String
    abstract val title: String
}

sealed class BottomNavigationItem: NavigationItem() {
    abstract override val routeName: String
    abstract override val title: String
    abstract val icon: String

    object Month : BottomNavigationItem() {
        override val routeName = "month"
        override val title = "Month"
        override val icon = "R.drawable.ic_month"
    }
    object Day : BottomNavigationItem() {
        override val title = "Day"
        override val icon = "R.drawable.ic_day"
        override val routeName = "day/{date}"
        fun createRoute(date: String) = "day/$date"
    }
    object AddEvent : BottomNavigationItem() {
        override val title = "AddEvent"
        override val icon = "R.drawable.ic_add_event"
        override val routeName = "addevent"
    }
}

