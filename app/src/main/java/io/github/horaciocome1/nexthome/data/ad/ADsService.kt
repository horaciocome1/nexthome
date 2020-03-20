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
        adsCollection.add(ad)
            .await()
        true
    } catch (exception: FirebaseFirestoreException) { false }

    override suspend fun retrieveRentingADs(zona: String): List<AD> = try {
        adsCollection.whereEqualTo(FIELD_AD_TYPE, AD_TYPE_RENTING)
            .whereEqualTo(FIELD_ZONA, zona)
            .orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjectsAsync()
    } catch (exception: FirebaseFirestoreException) { listOf() }

    override suspend fun retrieveSellingADs(zona: String): List<AD> = try {
        adsCollection.whereEqualTo(FIELD_AD_TYPE, AD_TYPE_SELLING)
            .whereEqualTo(FIELD_ZONA, zona)
            .orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING)
            .get()
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
        adsCollection.document(adId)
            .delete()
            .await()
        true
    } catch (exception: FirebaseFirestoreException) { false }

    companion object {

        const val AD_TYPE_RENTING = "RENTING"
        const val AD_TYPE_SELLING = "SELLING"

        private const val COLLECTION_ADS = "ads"
        private const val COLLECTION_OWNERS = "owners"

        private const val FIELD_AD_TYPE = "type"
        private const val FIELD_ZONA = "zona"
        private const val FIELD_CREATED_AT = "createdAt"

    }

}