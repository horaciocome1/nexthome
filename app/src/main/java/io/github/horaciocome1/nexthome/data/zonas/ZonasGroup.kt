package io.github.horaciocome1.nexthome.data.zonas

import com.google.firebase.Timestamp

data class ZonasGroup(
    var id: String = "",
    var zonas: ArrayList<String> = arrayListOf(),
    var createdAt: Timestamp = Timestamp.now()
)