package com.example.ecoswitch.presentation.perangkat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecoswitch.components.multi_screen.IotCard
import com.example.ecoswitch.util.Resource

@Composable
fun PerangkatScreen(
    onAddClick: () -> Unit
) {
    val viewModel = hiltViewModel<PerangkatViewModel>()
    val devicesState = viewModel.devicesState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getAllDeviceIot()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item(
                span = { GridItemSpan(maxCurrentLineSpan) }
            ) {
                Spacer(Modifier)
            }

            if (devicesState.value is Resource.Success) {
                devicesState.value.data?.data?.let {
                    items(it) {
                        IotCard(
                            name = it.name,
                            mode = it.mode,
                            info = "-",
                            checked = it.is_on == true,
                            onCheckedChange = {
                                //TODO Handle this later
                            },
                            is_receiver = it.mode == "Receiver"
                        )
                    }
                }
            }
        }
    }
}