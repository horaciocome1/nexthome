package io.github.horaciocome1.nexthome.data.zonas

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.horaciocome1.nexthome.util.toObjectAsync
import kotlinx.coroutines.tasks.await

class ZonasService : ZonasInterface {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val zonasGroupMaputoReference: DocumentReference by lazy {
        firestore.collection(COLLECTION_ZONAS_GROUP)
            .document(ZONAS_GROUP_MAPUTO)
    }

    override suspend fun retrieveZonas(): ArrayList<String> = try {
        var zonas = arrayListOf<String>()
        zonasGroupMaputoReference.get()
            .await()
            .toObjectAsync<ZonasGroup>()
            ?.let { zonas = it.zonas }
        zonas
    } catch (exception: Exception) { arrayListOf() }

    companion object {

        private const val COLLECTION_ZONAS_GROUP = "zonasGroup"

        private const val ZONAS_GROUP_MAPUTO = "maputo"

    }

}