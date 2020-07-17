package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.login.LoginModel
import c.eip.neoconnect.data.repository.AuthRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    fun login(loginModel: LoginModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = authRepository.login(loginModel),
                    message = "Connexion réussie"
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = "Veuillez vérifier votre pseudo et mot de passe"
                )
            )
        }
    }
}
