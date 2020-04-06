package io.github.horaciocome1.nexthome.data.ad

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import io.github.horaciocome1.nexthome.util.toObjectsAsync
import io.github.horaciocome1.nexthome.data.profile.Owner
import io.github.horaciocome1.nexthome.util.toObjectAsync
import kotlinx.coroutines.tasks.await

class ADsService : ADsServiceInterface {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val adsCollection: CollectionReference by lazy {
        firestore.collection(COLLECTION_ADS)
    }

    private val savedASsCollection: CollectionReference by lazy {
        firestore.collection(COLLECTION_OWNERS)
            .document(auth.currentUser!!.uid)
            .collection(COLLECTION_ADS)
    }

    override suspend fun createAD(ad: AD): Boolean = try {
        ad.id = adsCollection.document().id
        adsCollection.add(ad)
            .await()
        true
    } catch (exception: FirebaseFirestoreException) { false }

    override suspend fun retrieveRentingADs(filter: Filter): List<AD> = try {
        val query = adsCollection.whereEqualTo(FIELD_AD_TYPE, AD_TYPE_RENTING)
            .filterByRooms(filter.rooms)
            .filterByWCs(filter.wcs)
        if (filter.hood.isNotBlank())
            query.whereEqualTo(FIELD_HOOD, filter.hood)
        if (filter.hasWater)
            query.whereEqualTo(FIELD_HAS_WATER, true)
        if (filter.hasLight)
            query.whereEqualTo(FIELD_HAS_LIGHT, true)
        if (filter.hasFurniture)
            query.whereEqualTo(FIELD_HAS_FURNITURE, true)
        when (filter.orderBy) {
            ORDER_BY_DATE_DESCENDING ->
                query.orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING)
            ORDER_BY_DATE_ASCENDING ->
                query.orderBy(FIELD_CREATED_AT, Query.Direction.ASCENDING)
            ORDER_BY_PRICE_DESCENDING ->
                query.orderBy(FIELD_PRICE, Query.Direction.DESCENDING)
            ORDER_BY_PRICE_ASCENDING ->
                query.orderBy(FIELD_PRICE, Query.Direction.ASCENDING)
        }
      query.get()
            .await()
            .toObjectsAsync()
    } catch (exception: FirebaseFirestoreException) { listOf() }

    override suspend fun retrieveSellingADs(filter: Filter): List<AD> = try {
        val query = adsCollection.whereEqualTo(FIELD_AD_TYPE, AD_TYPE_SELLING)
            .filterByRooms(filter.rooms)
            .filterByWCs(filter.wcs)
        if (filter.hood.isNotBlank())
            query.whereEqualTo(FIELD_HOOD, filter.hood)
        if (filter.hasWater)
            query.whereEqualTo(FIELD_HAS_WATER, true)
        if (filter.hasLight)
            query.whereEqualTo(FIELD_HAS_LIGHT, true)
        if (filter.hasFurniture)
            query.whereEqualTo(FIELD_HAS_FURNITURE, true)
        when (filter.orderBy) {
            ORDER_BY_DATE_DESCENDING ->
                query.orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING)
            ORDER_BY_DATE_ASCENDING ->
                query.orderBy(FIELD_CREATED_AT, Query.Direction.ASCENDING)
            ORDER_BY_PRICE_DESCENDING ->
                query.orderBy(FIELD_PRICE, Query.Direction.DESCENDING)
            ORDER_BY_PRICE_ASCENDING ->
                query.orderBy(FIELD_PRICE, Query.Direction.ASCENDING)
        }
        query.get()
            .await()
            .toObjectsAsync()
    } catch (exception: FirebaseFirestoreException) { listOf() }

    override suspend fun retrieveSavedADs(): List<AD> = try {
        savedASsCollection.orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjectsAsync()
    } catch (exception: Exception) { listOf() }

    override suspend fun retrieveAD(adId: String): AD? = try {
        adsCollection.document(adId)
            .get()
            .await()
            .toObjectAsync()
    } catch (exception: FirebaseFirestoreException) { null }

    override suspend fun isADSaved(adId: String): Boolean = try {
        savedASsCollection.document(adId)
            .get()
            .await()
            .exists()
    } catch (exception: Exception) { false }

    override suspend fun saveAD(ad: AD): Boolean = try {
        savedASsCollection.document(ad.id)
            .set(ad)
            .await()
        true
    } catch (exception: FirebaseFirestoreException) { false }

    override suspend fun unSaveAD(adId: String): Boolean = try {
        savedASsCollection.document(adId)
            .delete()
            .await()
        true
    } catch (exception: FirebaseFirestoreException) { false }

    override fun amITheOwnerOfThisAD(owner: Owner): Boolean {
        return owner.id == auth.currentUser?.uid
    }

    override suspend fun deleteAD(adId: String): Boolean = try {
        val batch = firestore.batch()
        firestore.collectionGroup(COLLECTION_ADS)
            .whereEqualTo(FIELD_ID, adId)
            .get()
            .await()
            .forEach { batch.delete(it.reference) }
        batch.commit()
            .await()
        true
    } catch (exception: FirebaseFirestoreException) { false }

    private fun Query.filterByRooms(rooms: Int): Query {
        var i = 5
        while (i >= rooms) {
            whereEqualTo(FIELD_ROOMS, i)
            i--
        }
        return this
    }

    private fun Query.filterByWCs(wcs: Int): Query {
        var i = 3
        while (i >= wcs) {
            whereEqualTo(FIELD_ROOMS, i)
            i--
        }
        return this
    }

    companion object {

        const val AD_TYPE_RENTING = "RENTING"
        const val AD_TYPE_SELLING = "SELLING"

        private const val COLLECTION_ADS = "ads"
        private const val COLLECTION_OWNERS = "owners"

        private const val FIELD_AD_TYPE = "type"
        private const val FIELD_HOOD = "hood"
        private const val FIELD_CREATED_AT = "createdAt"
        private const val FIELD_ID = "id"
        private const val FIELD_ROOMS = "rooms"
        private const val FIELD_WCS = "wcs"
        private const val FIELD_HAS_WATER = "hasWater"
        private const val FIELD_HAS_LIGHT = "hasLight"
        private const val FIELD_HAS_FURNITURE = "hasFurniture"
        private const val FIELD_PRICE = "price"

        const val ORDER_BY_DATE_DESCENDING = 0
        const val ORDER_BY_DATE_ASCENDING = 1
        const val ORDER_BY_PRICE_DESCENDING = 2
        const val ORDER_BY_PRICE_ASCENDING = 3

    }

}