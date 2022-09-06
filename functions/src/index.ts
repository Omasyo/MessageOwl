import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const addToForum = functions.auth.user().onCreate((user) => {
  admin.firestore().collection("rooms").doc("general")
      .update({participants: admin.firestore.FieldValue.arrayUnion(user.uid)});
  functions.logger.info(`Added user ${user.uid}`, {structuredData: true});
});

export const removeFromForum = functions.auth.user().onDelete((user) => {
  admin.firestore().collection("rooms").doc("general")
      .update({participants: admin.firestore.FieldValue.arrayRemove(user.uid)});
  functions.logger.info(`Removed user ${user.uid}`, {structuredData: true});
});

export const updateUserRooms = functions.firestore
    .document("rooms/{roomId}")
    .onCreate((snap, context) => {
      const participants = snap.data().participants;
      const roomId = context.params.roomId;

      for (let i = 0; i < participants.length; ++i) {
        admin.firestore().collection("users").doc(participants[i])
            .update({rooms: admin.firestore.FieldValue.arrayUnion(roomId)});

        functions.logger.info(
            "User " + participants + " now in room " + roomId,
            {structuredData: true});
      }
    });

export const welcomeUser = functions.firestore;

export const notifyParticipants = functions.firestore
    .document("rooms/{roomId}/messages/{messageId}")
    .onCreate((snap, context) => {
      const roomId = context.params.roomId;
      const messageId = context.params.messageId;

      functions.logger.info(
          "Snap data: {Content=" + snap.get("content") + ", Sender=" +
          snap.get("sender") + ", Time= " + snap.get("time") +
          ", MessageId= " + messageId + ", RoomId = " + roomId +
          ", Time as string= " + snap.get("time").toMillis(),
          {structuredData: true});

      admin.firestore().collection("rooms").doc(roomId)
          .get().then((documentSnapshot) => {
            if (documentSnapshot.exists) {
              const participants = documentSnapshot.get("participants");

              for (let i = 0; i < participants.length; ++i) {
                console.log("Block statement execution no." + i);

                const payload = {
                  token: "c7xnaCi5Rjuga1mpxp9hz1:APA91bHv6Qipice" +
                  "3kr6lEFNcqp3UW-BuAKkOqGXdhsF1nFNkOJa3HIurMUvU2" +
                  "x0ozBRhHwR1264gdse9at40nE_hzLrisLm6_WRKTWMcuJJ" +
                  "eXtOATrGDEqa0rz6uM8jAGqlBGpF9P517",
                  data: {
                    messageId: messageId,
                    roomId: roomId,
                    senderId: snap.get("sender"),
                    content: snap.get("content"),
                    time: snap.get("time").toMillis().toString(),
                  },
                };

                admin.messaging().send(payload);

                functions.logger.info(
                    "Message sent to user " + participants[i] +
                    " from " + roomId,
                    {structuredData: true});

                break;
              }
            }
          });
    });

// export const addPhotoUrl =
//     functions.storage.object().onFinalize(async (object) => {
//       const path = object.name as string;
//       const userId = path.slice(path.lastIndexOf("/")+1);
//
//       if (userId) {
//         admin.firestore().collection("users").doc(userId)
//             .update({profilePic: object.mediaLink});
//         functions.logger.info(
//             `Added photo link for user ${userId}`,
//             {structuredData: true});
//       }
//     });
