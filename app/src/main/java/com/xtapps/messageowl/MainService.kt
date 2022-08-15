package com.xtapps.messageowl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainService : Service() {

//    private var serviceLooper: Looper? = null
//    private var serviceHandler: ServiceHandler? = null

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

        Firebase.firestore.collection("users").document("eASJwSsDCyaXtzfbsWqalOkeKDt1")
            .addSnapshotListener { snapshot, error ->


                Toast.makeText(this, snapshot!!["name"].toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

//        // For each start request, send a message to start a job and deliver the
//        // start ID so we know which request we're stopping when we finish the job
//        serviceHandler?.obtainMessage()?.also { msg ->
//            msg.arg1 = startId
//            serviceHandler?.sendMessage(msg)
//        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {

        //TODO("Return the communication channel to the service.")

        // We don't provide binding, so return null
        return null
    }

//    override fun onDestroy() {
//        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
//    }
}