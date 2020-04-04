package io.github.horaciocome1.nexthome.data.hoods

interface HoodsInterface {

    /**
     * responsible for retrieving a list of available zonas
     */
    suspend fun retrieveZonas(): List<String>

}