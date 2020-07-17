package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordFirstStepModel
import c.eip.neoconnect.data.model.resetPassword.ResetPasswordThirdStepModel
import c.eip.neoconnect.data.repository.UtilsRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class ResetPasswordViewModel : ViewModel() {
    private val utilsRepository = UtilsRepository()

    fun forgotPassword(email: ResetPasswordFirstStepModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.forgotPassword(email),
                    message = "Email envoyé avec succès"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun checkToken(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.checkResetPasswordToken(token),
                    message = "Token valide"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    fun updatePassword(form: ResetPasswordThirdStepModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.updatePassword(form),
                    message = "Mot de passe mis à jour avec succès"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}