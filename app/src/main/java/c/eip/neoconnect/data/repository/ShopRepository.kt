package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.service.ShopService
import c.eip.neoconnect.utils.Constants

class ShopRepository {
    private var shopService: ShopService = Constants.shopService

    /**
     * Récupération de son profil Boutique
     */
    suspend fun getProfilShop(token: String) = shopService.getShopProfil(token)

    /**
     * Récupération d'un autre profil Boutique
     */
    suspend fun getOtherShop(token: String, id: Int) = shopService.getOtherShop(token, id)

    /**
     * Mise à jour de son compte Boutique
     */
    suspend fun updateProfilShop(token: String, shop: RegisterShopModel) =
        shopService.updateShopProfil(token, shop)

    /**
     * Récupération de la liste des Influenceurs inscrites
     */
    suspend fun getListInf(token: String) = shopService.getListInf(token)

    /**
     * Recherche d'une Boutique
     */
    suspend fun searchShop(token: String, keyword: SearchModel) =
        shopService.searchShop(token, keyword)
}