package io.github.horaciocome1.nexthome.ui.start

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.data.ad.ADsService
import io.github.horaciocome1.nexthome.data.zonas.ZonasService
import io.github.horaciocome1.nexthome.util.navigate

class ADsViewModel : ViewModel() {

    private val aDsService: ADsService by lazy { ADsService() }

    private val zonasService: ZonasService by lazy { ZonasService() }

    var selectedZona: String = ""

    suspend fun retrieveRentingADs(): List<AD> =
        aDsService.retrieveRentingADs(zona = selectedZona)

    suspend fun retrieveSellingADs(): List<AD> =
        aDsService.retrieveSellingADs(zona = selectedZona)

    suspend fun retrieveSavedADs(): List<AD> =
        aDsService.retrieveSavedADs()

    suspend fun retrieveZonas(): ArrayList<String> =
        zonasService.retrieveZonas()

    fun navigateToAD(view: View, adId: String) =
        StartFragmentDirections.actionOpenAd(adId)
            .navigate(view)

    fun navigateToCreateAD(view: View) =
        StartFragmentDirections.actionOpenCreateAd()
            .navigate(view)

}