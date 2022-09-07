package com.xtapps.messageowl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.xtapps.messageowl.models.MessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
        if (message.data.isEmpty()) return

        createNotificationChannel()

        val timeStampString = message.data["time"] as String
        val timeStamp = timeStampString.toLong()

        val messageModel = MessageModel(
            id = message.data["messageId"] as String,
            roomId = message.data["roomId"] as String,
            content = message.data["content"] as String,
            senderId = message.data["senderId"] as String,
            timestamp = Date(timeStamp),
        )

        if(messageModel.senderId == Firebase.auth.currentUser!!.uid) return

        CoroutineScope(Dispatchers.IO).launch {
            launch {
                (application as MessageOwlApplication).appDatabase.messageDao()
                    .insertMessage(messageModel)
            }
            launch {
                val recentMessages = (application as MessageOwlApplication).appDatabase.messageDao()
                    .getRecentMessages(messageModel.roomId).reversed()

                val bundle = Bundle().also { it.putString("room_id", messageModel.roomId) }

                val pendingIntent = NavDeepLinkBuilder(this@MessagingService)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.main_navigation)
                    .setDestination(R.id.roomFragment)
                    .setArguments(bundle)
                    .createPendingIntent()

                launch {
                    val room = (application as MessageOwlApplication).appDatabase.chatRoomDao()
                        .getRoomDetails(messageModel.roomId)

                    val notification = NotificationCompat.Builder(this@MessagingService, CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo_icon)
                        .setStyle(
                            NotificationCompat.MessagingStyle("Me")
                                .setConversationTitle(room.name)
                                .also {
                                    recentMessages.forEach { message ->
                                        it.addMessage(
                                            message.message.content,
                                            message.message.timestamp.time,
                                            Person.Builder().setName(message.user?.name).build()
                                        )
                                    }
                                })
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    NotificationManagerCompat.from(this@MessagingService).apply {
                        notify(room.id, 1001, notification.build())
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