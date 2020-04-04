package io.github.horaciocome1.nexthome.util

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * converts receiver document snapshot to specified Type asynchronously
 */
suspend inline fun <reified T : Any> DocumentSnapshot?.toObjectAsync(): T? =
    withContext(Dispatchers.Default) {
        if (this@toObjectAsync == null)
            return@withContext null
        toObject<T>()
    }

/**
 * converts receiver query snapshot to specified Type asynchronously
 */
suspend inline fun <reified T : Any> QuerySnapshot?.toObjectsAsync(): List<T> =
    withContext(Dispatchers.Default) {
        if (this@toObjectsAsync == null)
            return@withContext emptyList<T>()
        toObjects<T>()
    }