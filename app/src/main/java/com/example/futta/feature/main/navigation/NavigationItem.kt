package com.example.futta.feature.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.futta.domain.model.EventId

sealed class NavigationItem {
    abstract val routeName: String
    abstract val title: String
    object Event : NavigationItem() {
        override val routeName = "event/{eventId}"
        override val title = "Event"
        fun createRoute(eventId: EventId) = "event/${eventId.value}"
    }
    object UpdateEvent : NavigationItem() {
        override val routeName = "updateevent/{eventId}"
        override val title = "Update Event"
        fun createRoute(eventId: EventId) = "updateevent/${eventId.value}"
    }
    object AddEvent : NavigationItem() {
        override val title = "Add Event"
        override val routeName = "addevent"
    }
    object Settings : NavigationItem() {
        override val title = "Settings"
        override val routeName = "settings"
    }
}

sealed class BottomNavigationItem: NavigationItem() {
    abstract override val routeName: String
    abstract override val title: String
    abstract val icon: ImageVector

    object Month : BottomNavigationItem() {
        override val routeName = "month"
        override val title = "Month"
        override val icon = Icons.Outlined.DateRange
    }
    object Day : BottomNavigationItem() {
        override val title = "Day"
        override val icon = Icons.Outlined.List
        override val routeName = "day/{date}"
        fun createRoute(date: String) = "day/$date"
    }
    object Week : BottomNavigationItem() {
        override val title = "Week"
        override val icon = Icons.Outlined.DateRange
        override val routeName = "week"
    }

}

