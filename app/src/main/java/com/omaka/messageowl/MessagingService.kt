package com.omaka.messageowl

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.omaka.messageowl.models.MessageModel
import com.omaka.messageowl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MessagingService : FirebaseMessagingService() {

    private val CHANNEL_ID = "message_owl_channel"

    val database by lazy {
        (application as MessageOwlApplication).appDatabase
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "message_owl_channel"
//            val descriptionText = getString(R.string.channel_description)

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
//                .apply {
//                    setShowBadge(true)
//            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val myProcess = RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(myProcess)
        val isInBackground =
            myProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND


        if (!isInBackground || message.data.isEmpty()) return

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
                val id = database.messageDao()
                    .insertMessage(messageModel)
                if (id != -1L) {
                    launch {
                        database.chatRoomDao().incrementUnreadCount(messageModel.roomId)
                    }
                }
            }
            launch {
                val recentMessages = database.messageDao()
                    .getRecentMessages(messageModel.roomId).reversed()

                val bundle = Bundle().also { it.putString("room_id", messageModel.roomId) }

                val pendingIntent = NavDeepLinkBuilder(this@MessagingService)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.main_navigation)
                    .setDestination(R.id.roomFragment)
                    .setArguments(bundle)
                    .createPendingIntent()

                launch {
                    val room = database.chatRoomDao()
                        .getRoomDetails(messageModel.roomId)

                    val notification = NotificationCompat.Builder(this@MessagingService, CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo_icon)
                        .setStyle(
                            NotificationCompat.MessagingStyle("Me")
                                .setConversationTitle(room.name)
                                .also {
                                    for(messageModel in recentMessages.slice(0 until recentMessages.lastIndex)) {
                                        it.addHistoricMessage(
                                            NotificationCompat.MessagingStyle.Message(
                                                messageModel.message.content,
                                                messageModel.message.timestamp.time,
                                                Person.Builder().setName(messageModel.user?.name).build()
                                            )
                                        )
                                    }
                                }
                                .addMessage(
                                    recentMessages.last().message.content,
                                    recentMessages.last().message.timestamp.time,
                                    Person.Builder().setName(recentMessages.last().user?.name).build()))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    NotificationManagerCompat.from(this@MessagingService).apply {
                        notify(room.id, 1001, notification.build())
                    }
                }
            }
        }
    }
}