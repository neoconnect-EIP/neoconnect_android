package c.eip.neoconnect.data.service

import c.eip.neoconnect.data.model.message.AllChannelResponse
import c.eip.neoconnect.data.model.message.MessageModel
import c.eip.neoconnect.data.model.message.OneChannelResponse
import retrofit2.http.*

interface ChatService {
    @GET("/message")
    suspend fun getAllChannel(
        @Header("authorization") token: String?
    ): List<AllChannelResponse>

    @GET("/message/{idChannel}")
    suspend fun getAllMessageFromOneChannel(
        @Header("authorization") token: String?, @Path("idChannel") id: Int?
    ): OneChannelResponse

    @POST("/message")
    suspend fun postMessage(
        @Header("authorization") token: String?, @Body message: MessageModel
    ): String
}