package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.parrainage.ParrainageModel
import c.eip.neoconnect.data.model.report.UserReportModel
import c.eip.neoconnect.data.service.UserService
import c.eip.neoconnect.utils.Constants

class UserRepository {
    private var userService: UserService = Constants.userService

    /**
     * Noter un utilisateur
     */
    suspend fun markUser(token: String, id: Int, mark: MarkModel) =
        userService.rateUser(token, id, mark)

    /**
     * Commenter un utilisateur
     */
    suspend fun commentUser(token: String, id: Int, comment: CommentModel) =
        userService.commentUser(token, id, comment)

    /**
     * Supprimer son compte
     */
    suspend fun deleteAccount(token: String) = userService.deleteAccount(token)

    /**
     * Signaler un utilisateur
     */
    suspend fun reportUser(token: String, id: Int, report: UserReportModel) =
        userService.reportUser(token, id, report)

    /**
     * Suggestions d'offres
     */
    suspend fun suggestionUser(token: String) = userService.suggestionUser(token)

    /**
     * Récupérer follows
     */
    suspend fun getFollows(token: String) = userService.getFollows(token)

    /**
     * Entrer un code parrainage
     */
    suspend fun insertCodeParrainage(code: ParrainageModel) = userService.insertCodeParrainage(code)

}