package com.example.ecoswitch.presentation.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecoswitch.components.multi_screen.IotCard
import com.example.ecoswitch.util.Resource
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen() {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val bannerState = viewModel.bannerState.collectAsState()
    val devicesState = viewModel.devicesState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getAllBanner()
        viewModel.getAllDeviceIot()
    }

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item(
            span = { GridItemSpan(maxCurrentLineSpan) }
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(text = "Hai, Fulan!", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Selamat datang di EcoSwitch.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item(
            span = { GridItemSpan(maxCurrentLineSpan) }
        ) {
            if (bannerState.value is Resource.Success) {
                bannerState.value.data?.data?.let {
                    val state = rememberPagerState(pageCount = { it.size })


                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(3000L)
                            state.animateScrollToPage(
                                page = (state.currentPage + 1) % (state.pageCount)
                            )
                        }
                    }

                    HorizontalPager(state = state) { index ->
                        it[index].apply {
                            AsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = this.link,
                                contentScale = ContentScale.FillWidth,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .clip(RoundedCornerShape(16.dp))
//                    .background(Color.LightGray)
//            )
        }

        item {
            Card(
                modifier = Modifier.height(140.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Proyeksi Pemakaian Listrik Bulan Ini")
                    Text(text = "N/A", style = MaterialTheme.typography.titleLarge)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.height(140.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Proyeksi Biaya Listrik Bulan Ini")
                    Text(text = "N/A", style = MaterialTheme.typography.titleLarge)
                }
            }
        }

        item(
            span = { GridItemSpan(maxCurrentLineSpan) }
        ) {
            Text(text = "Shortcut", style = MaterialTheme.typography.titleMedium)
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