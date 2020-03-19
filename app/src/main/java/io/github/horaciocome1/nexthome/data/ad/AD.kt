package io.github.horaciocome1.nexthome.data.ad

import io.github.horaciocome1.nexthome.data.profile.Proprietario

data class AD(
    var id: String = "",
    var quartos: Int = 2,
    var suites: Int = 1,
    var wcs: Int = 1,
    var zona: String = "",
    var price: Int = 1000000,
    var isMobilada: Boolean = true,
    var hasAgua: Boolean = true,
    var hasLuz: Boolean = true,
    var type: String = ADsService.AD_TYPE_RENTING,
    var proprietario: Proprietario = Proprietario(
        name = "José Imobiliária"
    )
)