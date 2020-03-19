package io.github.horaciocome1.nexthome.data.zonas

class ZonasService : ZonasInterface {

    override suspend fun retrieveZonas(): ArrayList<String> {
        return arrayListOf(
            "Xipamanine",
            "Trevo",
            "Machava Socimol",
            "Djuba",
            "Belo Horizonte"
        )
    }

}