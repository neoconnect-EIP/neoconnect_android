package c.eip.neoconnect.data.repository

import c.eip.neoconnect.data.model.register.RegisterShopModel
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.service.ShopService
import c.eip.neoconnect.utils.Constants

class ShopRepository {
    private var shopService: ShopService = Constants.shopService

    suspend fun getProfilShop(token: String) = shopService.getShopProfil(token)

    suspend fun getOtherShop(token: String, id: Int) = shopService.getOtherShop(token, id)

    suspend fun updateProfilShop(token: String, shop: RegisterShopModel) =
        shopService.updateShopProfil(token, shop)

    suspend fun getListInf(token: String) = shopService.getListInf(token)

    suspend fun searchShop(token: String, keyword: SearchModel) =
        shopService.searchShop(token, keyword)
}