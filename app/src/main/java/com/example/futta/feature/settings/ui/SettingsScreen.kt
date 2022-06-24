package com.example.futta.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.futta.feature.main.ui.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    val scrollState = rememberLazyListState()
    val isChecked by viewModel.darkMode.observeAsState()
    LazyColumn(state = scrollState) {
        items(count = 1) {
            ListItem(
                text = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Darkmode")
                        isChecked?.let { it ->
                            Switch(
                                checked = it,
                                onCheckedChange = { viewModel.toggleDarkMode() }
                            )
                        }
                    }
                })
        }
    }
}