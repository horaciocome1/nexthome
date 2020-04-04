package io.github.horaciocome1.nexthome.data.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.horaciocome1.nexthome.util.toObjectAsync
import kotlinx.coroutines.tasks.await

class ProfileService : ProfileInterface {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val ownerProfileReference: DocumentReference by lazy {
        firestore.collection(COLLECTION_OWNERS)
            .document(auth.currentUser!!.uid)
    }

    override suspend fun retrieveProfile(): Owner? = try {
        ownerProfileReference.get()
            .await()
            .toObjectAsync()
    } catch (exception: Exception) { null }

    override suspend fun updateProfile(owner: Owner): Boolean = try {
        owner.id = ownerProfileReference.id
        ownerProfileReference.set(owner)
            .await()
        true
    } catch (exception: Exception) { false }

    companion object {

        private const val COLLECTION_OWNERS = "owners"

    }

}