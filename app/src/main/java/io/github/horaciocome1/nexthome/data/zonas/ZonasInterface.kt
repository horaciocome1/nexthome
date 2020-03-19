package io.github.horaciocome1.nexthome.data.zonas

interface ZonasInterface {

    /**
     * responsible for retrieving a list of available zonas
     */
    suspend fun retrieveZonas(): ArrayList<String>

}