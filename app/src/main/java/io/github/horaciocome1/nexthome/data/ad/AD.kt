package io.github.horaciocome1.nexthome.data.ad

import com.google.firebase.Timestamp
import io.github.horaciocome1.nexthome.data.profile.Owner

data class AD(
    var id: String = "",
    var rooms: Int = 1,
    var suites: Int = 0,
    var wcs: Int = 1,
    var hood: String = "",
    var price: Int = 0,
    var createdAt: Timestamp = Timestamp.now(),
    var hasFurniture: Boolean = false,
    var hasWater: Boolean = false,
    var hasLight: Boolean = false,
    var type: String = ADsService.AD_TYPE_RENTING,
    var owner: Owner = Owner()
)