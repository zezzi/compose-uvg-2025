package com.zezziapp.testapp.features.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.zezziapp.testapp.core.util.Result
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.Ok(Unit)
        } catch (t: Throwable) {
            Result.Err(t)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Ok(Unit)
        } catch (t: Throwable) {
            Result.Err(t)
        }
    }

    override fun currentUserId(): String? = firebaseAuth.currentUser?.uid

    override fun logout() {
        firebaseAuth.signOut()
    }
}