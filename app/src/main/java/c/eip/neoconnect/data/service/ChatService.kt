package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.message.AllChannelResponse
import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.data.model.message.OneChannelResponse
import retrofit2.http.*

interface ChatService {
    /**
     * Récupération de tous les canaux de discussions
     */
    @GET("/message")
    suspend fun getAllChannel(
        @Header("authorization") token: String?
    ): List<AllChannelResponse>

    /**
     * Récupération d'un canal de discussion
     */
    @GET("/message/{idChannel}")
    suspend fun getAllMessageFromOneChannel(
        @Header("authorization") token: String?, @Path("idChannel") id: Int?
    ): OneChannelResponse

    /**
     * Ajout d'un message dans un canal de discussion
     */
    @POST("/message")
    suspend fun postMessage(
        @Header("authorization") token: String?, @Body message: MessageModel
    ): String
}