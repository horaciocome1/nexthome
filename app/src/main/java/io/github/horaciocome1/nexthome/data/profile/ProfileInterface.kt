package io.github.horaciocome1.nexthome.data.profile

interface ProfileInterface {

    /**
     * function that returns data for logged proprietário
     */
    suspend fun retrieveProfile(): Proprietario

    /**
     * function that updates proprietário data in the database
     */
    suspend fun updateProfile(proprietario: Proprietario): Boolean

}