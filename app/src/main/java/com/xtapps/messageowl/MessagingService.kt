package com.xtapps.messageowl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagingService : Service() {

    //    private var serviceLooper: Looper? = null
//    private var serviceHandler: ServiceHandler? = null

    private val authUser get() = Firebase.auth.currentUser!!
    private val userData = Firebase.firestore.collection("users").document(authUser.uid)
    private val roomDb = Firebase.firestore.collection("rooms")


//    // Handler that receives messages from the thread
//    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
//
//        override fun handleMessage(msg: Message) {
//            // Normally we would do some work here, like download a file.
//            // For our sample, we just sleep for 5 seconds.
//            try {
//                Thread.sleep(5000)
//            } catch (e: InterruptedException) {
//                // Restore interrupt status.
//                Thread.currentThread().interrupt()
//            }
//
//            // Stop the service using the startId, so that we don't stop
//            // the service in the middle of handling another job
//            stopSelf(msg.arg1)
//        }
//    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
//        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
//            start()
//
//            // Get the HandlerThread's Looper and use it for our Handler
//            serviceLooper = looper
//            serviceHandler = ServiceHandler(looper)
//        }

        if(Firebase.auth.currentUser == null) return

        userData.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val rooms = snapshot.data?.get("rooms") as ArrayList<String>? ?: arrayListOf()
                for (room in rooms) {
                    listentoMessageUpdates(room)
                }
            }
        }

        listentoUserUpdates()

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
        Firebase.firestore.collection("users").document("eASJwSsDCyaXtzfbsWqalOkeKDt1")
            .addSnapshotListener { snapshot, error ->
                Toast.makeText(this, snapshot!!["name"].toString(), Toast.LENGTH_SHORT).show()
            }
    }


    private fun listentoUserUpdates() = userData.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
            return@addSnapshotListener
        }
//        if( snapshot == null || snapshot.exists()) {
//            Log.w(com.xtapps.messageowl.ui.contacts.TAG, "snapshot does not exits")
//            return@addSnapshotListener
//        }

        CoroutineScope(Dispatchers.IO).launch {
            (application as MessageOwlApplication).appDatabase.userDao().insertUser(
                UserModel(
                    id = authUser.uid,
                    name = (snapshot?.get("name") ?: "") as String,
                    phoneNo = authUser.phoneNumber!!,
                    profilePic = snapshot?.get("profilePic") as String?
                )
            )
        }
    }

    //Todo: temp remove later
    private fun listentoMessageUpdates(roomId: String) =
        roomDb.document(roomId).collection("messages").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(com.xtapps.messageowl.ui.contacts.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshots != null) {
                for (dc in snapshots.documentChanges) {
                    val document = dc.document
                    CoroutineScope(Dispatchers.IO).launch {
                        launch {
                            (application as MessageOwlApplication).appDatabase.messageDao().insertMessage(
                                MessageModel(
                                    id = document.id,
                                    roomId = roomId,
                                    content = document["content"] as String,
                                    senderId = document["sender"] as String,
                                    timestamp = document.getDate("time")!!,
                                )
                            )
                        }
                        launch {
//                            application.appDatabase.chatRoomDao().
                        }
                    }
                }

            }
        }

//    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//
////        // For each start request, send a message to start a job and deliver the
////        // start ID so we know which request we're stopping when we finish the job
////        serviceHandler?.obtainMessage()?.also { msg ->
////            msg.arg1 = startId
////            serviceHandler?.sendMessage(msg)
////        }
//
//        // If we get killed, after returning from here, restart
//        return START_STICKY
//    }

    override fun onBind(intent: Intent): IBinder? {

        //TODO("Return the communication channel to the service.")

        // We don't provide binding, so return null
        return null
    }

//    override fun onDestroy() {
//        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
//    }
}