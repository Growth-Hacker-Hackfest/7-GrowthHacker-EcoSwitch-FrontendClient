package com.example.ecoswitch.components.eco_assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ecoswitch.R

@Composable
fun TheirChatBubble(
    message: String,
    isLoading: Boolean = false
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Row(
        modifier = Modifier.widthIn(max = (screenWidth * 3 / 4).dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(40.dp),
            model = R.drawable.ic_chatbot,
            contentDescription = ""
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 24.dp, topEnd = 24.dp, bottomEnd = 24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (isLoading) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    text = ". . .",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}