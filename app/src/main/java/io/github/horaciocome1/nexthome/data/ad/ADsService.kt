package io.github.horaciocome1.nexthome.data.ad

import io.github.horaciocome1.nexthome.data.profile.Proprietario

class ADsService : ADsServiceInterface {

    private val ads = arrayListOf(
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte"),
        AD(zona = "Belo Horionte")
    )

    override suspend fun createAD(ad: AD): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveRentingADs(zona: String): ArrayList<AD> {
        return ads
    }

    override suspend fun retrieveSellingADs(zona: String): ArrayList<AD> {
        return ads
    }

    override suspend fun retrieveSavedADs(): ArrayList<AD> {
        return ads
    }

    override suspend fun retrieveAD(adId: String): AD {
        return AD(zona = "Machava Socimol")
    }

    override suspend fun isADSaved(adId: String): Boolean {
        return true
    }

    override suspend fun saveAD(ad: AD): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun unSaveAD(adId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun amITheOwnerOfThisAD(proprietario: Proprietario): Boolean {
        return true
    }

    override suspend fun deleteAD(adId: String): Boolean {
        TODO("Not yet implemented")
    }

    companion object {

        const val AD_TYPE_RENTING = "RENTING"
        const val AD_TYPE_SELLING = "SELLING"

    }

}