package com.example.ecoswitch.components.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ecoswitch.MainNavRoutes
import com.example.ecoswitch.R

@Composable
fun MyNavbar(
    onItemClick: (route: String) -> Unit,
    currentRoute: String
) {
    val items = listOf(
        MyNavbarItem(
            "Dashboard",
            R.drawable.ic_navbar_dashboard,
            MainNavRoutes.Dashboard.name
        ),
        MyNavbarItem(
            "Perangkat",
            R.drawable.ic_navbar_perangkat,
            MainNavRoutes.Perangkat.name
        ),
        MyNavbarItem(
            "Eco-Assistant",
            R.drawable.ic_navbar_ecoassistant,
            MainNavRoutes.EcoAssistant.name
        ),
        MyNavbarItem(
            "Profil",
            R.drawable.ic_navbar_profil,
            MainNavRoutes.Profil.name
        )
    )
    val maxWidthPerBtn = LocalConfiguration.current.screenWidthDp.toFloat() / items.size
    BottomAppBar {
        Row {
            items.forEach {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(maxWidthPerBtn.dp)
                        .clickable { onItemClick(it.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(Int.MAX_VALUE.dp))
                                .background(
                                    when {
                                        currentRoute == it.route -> MaterialTheme.colorScheme.primary
                                        else -> Color.Unspecified
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(4.dp),
                                painter = rememberAsyncImagePainter(model = it.iconId),
                                contentDescription = "",
                                tint = when {
                                    currentRoute == it.route -> MaterialTheme.colorScheme.onPrimary
                                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                        Text(
                            textAlign = TextAlign.Center,
                            text = it.word,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}