package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.data.service.ChatService
import c.eip.neoconnect.utils.Constants

class ChatRepository {
    private var chatService: ChatService = Constants.chatService

    /**
     * Récupération de tous les canaux de discussions
     */
    suspend fun getAllChannel(token: String) = chatService.getAllChannel(token)

    /**
     * Récupération d'un canal de discussion
     */
    suspend fun getOneChannel(token: String, id: Int) =
        chatService.getAllMessageFromOneChannel(token, id)

    /**
     * Ajout d'un message dans un canal de discussion
     */
    suspend fun postMessage(token: String, message: MessageModel) =
        chatService.postMessage(token, message)
}