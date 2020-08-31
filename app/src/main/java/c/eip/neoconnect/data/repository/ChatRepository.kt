package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.data.service.ChatService
import c.eip.neoconnect.utils.Constants

class ChatRepository {
    private var chatService: ChatService = Constants.chatService

    suspend fun getAllChannel(token: String) = chatService.getAllChannel(token)

    suspend fun getOneChannel(token: String, id: Int) =
        chatService.getAllMessageFromOneChannel(token, id)

    suspend fun postMessage(token: String, message: MessageModel) =
        chatService.postMessage(token, message)
}