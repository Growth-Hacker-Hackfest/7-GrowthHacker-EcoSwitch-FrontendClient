package com.example.ecoswitch.presentation.eco_assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecoswitch.R
import com.example.ecoswitch.components.eco_assistant.MyChatBubble
import com.example.ecoswitch.components.eco_assistant.TheirChatBubble
import com.example.ecoswitch.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoAssistantScreen() {
    val viewModel = hiltViewModel<EcoAssistantViewModel>()
    val historyChatState = viewModel.historyChatState.collectAsState()
    val newChatState = viewModel.chatRequestState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getAllChatbot()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    AsyncImage(
                        modifier = Modifier.size(40.dp),
                        model = R.drawable.ic_chatbot,
                        contentDescription = ""
                    )
                },
                title = {
                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(text = "Eco-Assistant", style = MaterialTheme.typography.titleMedium)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )

                            Text(text = "Selalu aktif", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        shape = RoundedCornerShape(Int.MAX_VALUE.dp),
                        modifier = Modifier.weight(1f),
                        value = viewModel.chatInput.value,
                        onValueChange = {
                            viewModel.chatInput.value = it
                        },
                        placeholder = {
                            Text(text = "Tanyakan sesuatu...", color = Color.LightGray)
                        }
                    )

                    FilledIconButton(
                        onClick = {
                            viewModel.sendChatbotRequest()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "")
                    }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            reverseLayout = true
        ) {
            if (newChatState.value is Resource.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        TheirChatBubble(message = "", isLoading = true)
                    }
                }
            }

            items(viewModel.historyChatList) { item ->
                when (item.role) {
                    "assistant" -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            TheirChatBubble(message = item.message)
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            MyChatBubble(message = item.message)
                        }
                    }
                }
            }
        }
    }
}