package com.example.ecoswitch.presentation.eco_assistant

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.model.response.eco_assistant.SingleChatbotHistoryResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EcoAssistantViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val chatInput = mutableStateOf("")

    val historyChatState =
        MutableStateFlow<Resource<BaseResponse<List<SingleChatbotHistoryResponse>>>>(Resource.NotLoadedYet())
    val chatRequestState = MutableStateFlow<Resource<BaseResponse<String>>>(Resource.NotLoadedYet())
    val historyChatList = mutableStateListOf<SingleChatbotHistoryResponse>()

    fun getAllChatbot() {
        viewModelScope.launch {
            repository.getAllChatbotHistory().collect {
                historyChatState.value = it

                if (it is Resource.Success) {
                    historyChatList.clear()
                    it.data?.data?.let {
                        historyChatList.addAll(it)
                    }
                }
            }
        }
    }

    fun sendChatbotRequest() {
        historyChatList.add(
            0,
            SingleChatbotHistoryResponse(
                id = UUID.randomUUID().toString(),
                cluster_id = UUID.randomUUID().toString(),
                user_id = UUID.randomUUID().toString(),
                role = "user",
                message = chatInput.value
            )
        )

        viewModelScope.launch {
            repository.requestChatbot(chatInput.value).collect {
                chatRequestState.value = it

                if (it is Resource.Success) {
                    it.data?.data?.let { msg ->
                        historyChatList.add(
                            0,
                            SingleChatbotHistoryResponse(
                                id = UUID.randomUUID().toString(),
                                cluster_id = UUID.randomUUID().toString(),
                                user_id = UUID.randomUUID().toString(),
                                role = "assistant",
                                message = msg
                            )
                        )
                    }
                }
            }
        }

        chatInput.value = ""
    }
}