package io.github.horaciocome1.nexthome.data.ad

import io.github.horaciocome1.nexthome.data.profile.Proprietario

interface ADsServiceInterface {

    /**
     * function that takes an @param AD object and writes it to the database
     */
    suspend fun createAD(ad: AD): Boolean

    /**
     * function that retrieves all ADs available for renting in passed @param zona
     */
    suspend fun retrieveRentingADs(zona: String): ArrayList<AD>

    /**
     * function that retrieves all ADs available for selling in passed @param zona
     */
    suspend fun retrieveSellingADs(zona: String): ArrayList<AD>

    /**
     * function that retrieves all ADs saved by the user
     */
    suspend fun retrieveSavedADs(): ArrayList<AD>

    /**
     * function that returns the AD corresponding to the specified @param adId
     */
    suspend fun retrieveAD(adId: String): AD

    /**
     * function that tells if the AD is saved (true) or not (false) by the user
     * by taking @param adId and checking if it is a node of user's "savedADs"
     */
    suspend fun isADSaved(adId: String): Boolean

    /**
     * function that takes @param AD and writes to the database's user's "savedADs" subCollection
     */
    suspend fun saveAD(ad: AD): Boolean

    /**
     * function that unsaves an AD
     * by deleting @param adId from user's "savedADs" subCollection
     */
    suspend fun unSaveAD(adId: String): Boolean

    /**
     * function that tells whether the AD is owned by the logged user or not
     */
    fun amITheOwnerOfThisAD(proprietario: Proprietario): Boolean

    /**
     * function that deletes from database the AD corresponding to the specified @param adId
     */
    suspend fun deleteAD(adId: String): Boolean
}