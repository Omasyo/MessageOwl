package com.xtapps.messageowl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.log

const val TAG = "UserDataSource"
class UserDataSource {
    var _newVal = false
    var _user: UserModel? = null
    init {
        val phoneNo = FirebaseAuth.getInstance().currentUser?.phoneNumber
        if (phoneNo != null) {
            Firebase.firestore.collection("users").document(phoneNo)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: ${snapshot.data}")
                        val user = UserModel(
                            name = snapshot["name"] as String,
                            phoneNo = phoneNo,
                            id = snapshot["uid"] as String,
                        )
                        _user = user
                        _newVal = true
                    } else {
                        Log.d(TAG, "Current data: null")
                    }
                }
        }
    }

    val userData: Flow<UserModel> = flow {
        while (true) {
//            Log.d(TAG, "newVal $_newVal")
    delay(5000)
                emit(_user!!)


        }
    }
}
