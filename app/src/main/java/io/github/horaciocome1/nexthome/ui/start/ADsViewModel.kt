package io.github.horaciocome1.nexthome.ui.start

import android.view.View
import androidx.lifecycle.ViewModel
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.data.ad.ADsService
import io.github.horaciocome1.nexthome.data.hoods.HoodsService
import io.github.horaciocome1.nexthome.util.navigate

class ADsViewModel : ViewModel() {

    private val aDsService: ADsService by lazy { ADsService() }

    private val hoodsService: HoodsService by lazy { HoodsService() }

    var selectedHood: String = ""

    suspend fun retrieveRentingADs(): List<AD> =
        aDsService.retrieveRentingADs(hood = selectedHood)

    suspend fun retrieveSellingADs(): List<AD> =
        aDsService.retrieveSellingADs(zona = selectedHood)

    suspend fun retrieveSavedADs(): List<AD> =
        aDsService.retrieveSavedADs()

    suspend fun retrieveHoods(): ArrayList<String> =
        hoodsService.retrieveZonas()

    fun navigateToAD(view: View, adId: String) =
        StartFragmentDirections.actionOpenAd(adId)
            .navigate(view)

    fun navigateToCreateAD(view: View) =
        StartFragmentDirections.actionOpenCreateAd()
            .navigate(view)

}