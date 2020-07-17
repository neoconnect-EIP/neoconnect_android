package c.eip.neoconnect.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import c.eip.neoconnect.data.model.register.RegisterInfluenceurModel
import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.repository.AuthRepository
import c.eip.neoconnect.utils.Resource
import kotlinx.coroutines.Dispatchers

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    fun registerInfluencer(RegisterInfluenceurModel: RegisterInfluenceurModel) =
        liveData(Dispatchers.IO) {
            try {
                emit(
                    Resource.success(
                        data = authRepository.registerInfluencer(
                            RegisterInfluenceurModel
                        ), message = "Inscription réussie"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
            }
        }

    fun registerShop(registerShopModel: RegisterShopModel) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    data = authRepository.registerShop(registerShopModel),
                    message = "Inscription réussie"
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Une erreur est survenue"))
        }
    }
}