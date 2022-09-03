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

export const notifyParticipants = functions.firestore
    .document("rooms/{roomId}/messages/{messageId}")
    .onCreate((snap, context) => {
      const roomId = context.params.roomId;
      const messageId = context.params.messageId;

      functions.logger.info(
          "Snap data: {Content=" + snap.get("content") + ", Sender=" +
          snap.get("sender") + ", Time= " + snap.get("time") +
          ", MessageId= " + messageId + ", RoomId = " + roomId,
          {structuredData: true});

      admin.firestore().collection("rooms").doc(roomId)
          .get().then((documentSnapshot) => {
            if (documentSnapshot.exists) {
              const participants = documentSnapshot.get("participants");

              for (let i = 0; i < participants.length; ++i) {
                console.log("Block statement execution no." + i);

                const payload = {
                  token: "cWdaPKRbSF6YNnumypTtVg:APA91bExkCJ" +
                  "eDfEdK_7TLPW7JJFOHroDXQGOXMrAltGMtdHinNRT" +
                  "xDQdCRbX93OWgUbkvD-9Q_gl7mHcLB8uErtcXNQk6" +
                  "4SKX5ULD15kAT7j9V3XBCWjKcR0KSxopNO2L4-WxU_epM6Z",
                  data: {
                    Nick: "Mario",
                    body: "great match!",
                    Room: "PortugalVSDenmark",
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
