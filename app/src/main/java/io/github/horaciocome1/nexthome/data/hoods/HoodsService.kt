package io.github.horaciocome1.nexthome.data.hoods

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.horaciocome1.nexthome.util.toObjectAsync
import kotlinx.coroutines.tasks.await

class HoodsService : HoodsInterface {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val zonasGroupMaputoReference: DocumentReference by lazy {
        firestore.collection(COLLECTION_HOODS_GROUP)
            .document(HOODS_GROUP_MAPUTO)
    }

    override suspend fun retrieveZonas(): ArrayList<String> = try {
        var zonas = arrayListOf<String>()
        zonasGroupMaputoReference.get()
            .await()
            .toObjectAsync<HoodsGroup>()
            ?.let { zonas = it.hoods }
        zonas
    } catch (exception: Exception) { arrayListOf() }

    companion object {

        private const val COLLECTION_HOODS_GROUP = "hoodsGroup"

        private const val HOODS_GROUP_MAPUTO = "maputo"

    }

}