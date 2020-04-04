package io.github.horaciocome1.nexthome.data.hoods

import com.google.firebase.Timestamp

data class HoodsGroup(
    var id: String = "",
    var hoods: ArrayList<String> = arrayListOf(),
    var createdAt: Timestamp = Timestamp.now()
)