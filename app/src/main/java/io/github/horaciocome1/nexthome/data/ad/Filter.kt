package io.github.horaciocome1.nexthome.data.ad

import com.google.firebase.Timestamp
import io.github.horaciocome1.nexthome.data.profile.Owner

data class Filter(
    var rooms: Int = 1,
    var wcs: Int = 1,
    var hood: String = "",
    var hasFurniture: Boolean = false,
    var hasWater: Boolean = false,
    var hasLight: Boolean = false,
    var orderBy: Int = ADsService.ORDER_BY_DATE_DESCENDING
)