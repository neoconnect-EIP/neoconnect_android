package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.comment.CommentModel
import c.eip.neoconnect.data.model.mark.MarkModel
import c.eip.neoconnect.data.model.report.UserReportModel
import c.eip.neoconnect.data.service.UserService
import c.eip.neoconnect.utils.Constants

class UserRepository {
    private var userService: UserService = Constants.userService

    suspend fun markUser(token: String, id: Int, mark: MarkModel) =
        userService.rateUser(token, id, mark)

    suspend fun commentUser(token: String, id: Int, comment: CommentModel) =
        userService.commentUser(token, id, comment)

    suspend fun deleteAccount(token: String) = userService.deleteAccount(token)

    suspend fun reportUser(token: String, id: Int, report: UserReportModel) =
        userService.reportUser(token, id, report)
}