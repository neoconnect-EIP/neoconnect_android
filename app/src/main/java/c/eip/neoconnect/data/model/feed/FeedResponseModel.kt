package c.eip.neoconnect.data.model.feed

import c.eip.neoconnect.data.model.offres.OffreResponseModel
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.data.model.profil.ShopResponseModel

class FeedResponseModel {
    var listInfPopulaire = emptyList<InfluenceurResponseModel>()
    var listInfTendances = emptyList<InfluenceurResponseModel>()
    var listInfNotes = emptyList<InfluenceurResponseModel>()
    var listShopPopulaire = emptyList<ShopResponseModel>()
    var listShopTendance = emptyList<ShopResponseModel>()
    var listShopNotes = emptyList<ShopResponseModel>()
    var listOfferPopulaire = emptyList<OffreResponseModel>()
    var listOfferTendance = emptyList<OffreResponseModel>()
    var listOfferNotes = emptyList<OffreResponseModel>()
}