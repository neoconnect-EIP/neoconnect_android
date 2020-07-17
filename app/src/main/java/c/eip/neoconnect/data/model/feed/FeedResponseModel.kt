package c.eip.neoconnect.data.model.feed

import c.eip.neoconnect.data.model.offres.OffreResponseModel
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel

class FeedResponseModel {
    var listInfPopulaires = emptyList<InfluenceurResponseModel>()
    var listInfTendances = emptyList<InfluenceurResponseModel>()
    var listInfNotes = emptyList<InfluenceurResponseModel>()
    var listShopPopulaires = emptyList<ShopResponseModel>()
    var listShopTendances = emptyList<ShopResponseModel>()
    var listShopNotes = emptyList<ShopResponseModel>()
    var listOffrePopulaires = emptyList<OffreResponseModel>()
    var listOffreTendances = emptyList<OffreResponseModel>()
    var listOffreNotes = emptyList<OffreResponseModel>()
}