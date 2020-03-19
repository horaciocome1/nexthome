package io.github.horaciocome1.nexthome.ui.ad

import androidx.lifecycle.ViewModel
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.data.ad.ADsService
import io.github.horaciocome1.nexthome.data.ad.Proprietario

class ADViewModel : ViewModel() {

    private val service: ADsService by lazy { ADsService() }

    suspend fun retrieveAD(adId: String) = service.retrieveAD(adId)

    suspend fun saveAD(ad: AD) = service.saveAD(ad)

    suspend fun unSaveAD(adId: String) = service.unSaveAD(adId)

    fun amITheOwnerOfThisAD(proprietario: Proprietario) =
        service.amITheOwnerOfThisAD(proprietario)

    suspend fun deleteAD(adId: String) = service.deleteAD(adId)

}