package io.github.horaciocome1.nexthome.ui.ad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.data.ad.ADsService
import kotlinx.coroutines.launch

class ADViewModel : ViewModel() {

    private val service: ADsService by lazy { ADsService() }

    var ad: AD = AD()

    suspend fun retrieveAD(adId: String) = viewModelScope.launch {
        service.retrieveAD(adId)?.let { ad = it }
    }

    suspend fun isADSaved() = service.isADSaved(adId = ad.id)

    suspend fun saveAD() = service.saveAD(ad)

    suspend fun unSaveAD() = service.unSaveAD(adId = ad.id)

    fun amITheOwnerOfThisAD() = service.amITheOwnerOfThisAD(owner = ad.owner)

    suspend fun deleteAD() = service.deleteAD(adId = ad.id)

}