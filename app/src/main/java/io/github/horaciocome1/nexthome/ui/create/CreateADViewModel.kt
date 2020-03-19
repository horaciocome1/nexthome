package io.github.horaciocome1.nexthome.ui.create

import androidx.lifecycle.ViewModel
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.data.ad.ADsService
import io.github.horaciocome1.nexthome.data.zonas.ZonasService

class CreateADViewModel : ViewModel() {

    val ad = AD()

    private val aDsService: ADsService by lazy { ADsService() }

    private val zonasService: ZonasService by lazy { ZonasService() }

    suspend fun createAD(): Boolean = aDsService.createAD(ad)

    suspend fun retrieveZonas(): ArrayList<String> = zonasService.retrieveZonas()

}