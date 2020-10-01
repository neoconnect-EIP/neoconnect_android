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

    /**
     * 1ère étape de la récupération de mot de passe
     * Signalement de l'oubli de son mot de passe pour recevoir un code par mail
     */
    fun forgotPassword(email: ResetPasswordFirstStepModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.forgotPassword(email = email),
                    message = "Email envoyé avec succès"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * 2ème étape de la récupération de mot de passe
     * Vérification si le code reçu par mail est valide ou non
     */
    fun checkToken(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.checkResetPasswordToken(token = token),
                    message = "Token valide"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }

    /**
     * 3ème étape de la récupération de mot de passe
     * Modification du mot de passe si Etape 2 validé
     */
    fun updatePassword(form: ResetPasswordThirdStepModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = utilsRepository.updatePassword(form = form),
                    message = "Mot de passe mis à jour avec succès"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}