package com.example.futta.feature.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomSheetOptions {
    abstract val options: List<SheetOption>
    object DefaultScreen: BottomSheetOptions() {
        override val options = listOf(SheetOption.Settings)
    }
    object EventScreen: BottomSheetOptions() {
        override val options = listOf(SheetOption.Edit, SheetOption.Delete, SheetOption.Settings)
    }
}

sealed class SheetOption{
    abstract val icon: ImageVector
    abstract val name: String
    object Edit: SheetOption() {
        override val icon = Icons.Outlined.Edit
        override val name = "Edit"
    }
    object Delete: SheetOption() {
        override val icon = Icons.Outlined.Delete
        override val name = "Delete"
    }
    object Settings: SheetOption() {
        override val icon = Icons.Outlined.Settings
        override val name = "Settings"
    }
}