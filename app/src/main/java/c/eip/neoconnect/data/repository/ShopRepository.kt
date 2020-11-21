package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.service.ShopService
import c.eip.neoconnect.utils.Constants

class ShopRepository {
    private var shopService: ShopService = Constants.shopService

    /**
     * Récupération de son profil Marque
     */
    suspend fun getProfilShop(token: String) = shopService.getShopProfil(token)

    /**
     * Récupération d'un autre profil Marque
     */
    suspend fun getOtherShop(token: String, id: Int) = shopService.getOtherShop(token, id)

    /**
     * Mise à jour de son compte Marque
     */
    suspend fun updateProfilShop(token: String, shop: RegisterShopModel) =
        shopService.updateShopProfil(token, shop)

    /**
     * Récupération de la liste des Influenceurs inscrites
     */
    suspend fun getListInf(token: String) = shopService.getListInf(token)

    /**
     * Recherche d'une Marque
     */
    suspend fun searchShop(token: String, keyword: SearchModel) =
        shopService.searchShop(token, keyword)

    /**
     * Voir abonnées d'une marque
     */
    suspend fun getFollowShop(token: String, id: Int) = shopService.getFollowShop(token, id)

    /**
     * Suivre une marque
     */
    suspend fun followShop(token: String, id: Int) = shopService.followShop(token, id)


    /**
     * Ne plus suivre une marque
     */
    suspend fun unfollowShop(token: String, id: Int) = shopService.unfollowShop(token, id)
}