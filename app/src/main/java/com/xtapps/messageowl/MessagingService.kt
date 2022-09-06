package com.xtapps.messageowl

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MessagingService : FirebaseMessagingService() {


    override fun onCreate() {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
        Firebase.firestore.collection("users").document("eASJwSsDCyaXtzfbsWqalOkeKDt1")
            .addSnapshotListener { snapshot, error ->
                Toast.makeText(this, snapshot!!["name"].toString(), Toast.LENGTH_SHORT).show()
            }
    }

    val CHANNEL_ID = "message_owl_channel"
    val notifcationId = 9708

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "message_owl_channel"
//            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
//                .apply {
//                description = descriptionText
//            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: ${message.data}")

        createNotificationChannel()

//        CoroutineScope(Dispatchers.IO).launch {
//            launch {
//                (application as MessageOwlApplication).appDatabase.messageDao()
//                    .insertMessage(
//                        MessageModel(
//                            id = message.data["id"] as String,
//                            roomId = message.data["roomId"] as String,
//                            content = message.data["content"] as String,
//                            senderId = message.data["senderId"] as String,
//                            timestamp = document.getDate("time")!!,
//                        )
//                    )
//            }
//        }


        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setStyle(NotificationCompat.MessagingStyle("Me")
                .setConversationTitle("Team lunch")
                .addMessage("Hi", Date().time - 100000, null as CharSequence?) // Pass in null for user.
                .addMessage("What's up?", Date().time - 70000, "Coworker")
                .addMessage("Not much", Date().time - 35000, null as CharSequence?)
                .addMessage("How about lunch?", Date().time, "Coworker"))
            .build()



        val notification2 = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setStyle(NotificationCompat.MessagingStyle("Me").setGroupConversation(false)
                .setConversationTitle("Who's hungry")
                .addMessage("Hey", Date().time - 100000, Person.Builder().build()) // Pass in null for user.
                .addMessage("Still up?", Date().time - 70000, Person.Builder().setName("Coworker").build())
                .addMessage("Yeah", Date().time - 35000, Person.Builder().build())
                .addMessage("How about dinner?", Date().time, Person.Builder().setName("Coworker").build()))
            .build()

//        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_message)
//            .setContentTitle("My notification")
//            .setContentText("Much longer text that cannot fit one line...")
//            .setStyle(NotificationCompat.BigTextStyle()
//                .bigText("Much longer text that cannot fit one line..."))
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).apply {
            notify(1234, notification2)

            notify(1001, notification)
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
//
//    override fun onBind(intent: Intent): IBinder? {
//
//        //TODO("Return the communication channel to the service.")
//
//        // We don't provide binding, so return null
//        return null
//    }

//    override fun onDestroy() {
//        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
//    }
}