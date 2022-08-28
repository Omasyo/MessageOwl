import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const addToForum = functions.auth.user().onCreate((user) => {
  admin.firestore().collection("rooms").doc("general")
      .update({participants: admin.firestore.FieldValue.arrayUnion(user.uid)});
  functions.logger.info("Added user ${auth.uid}", {structuredData: true});
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
        console.log("Block statement execution no." + i);
        admin.firestore().collection("users").doc(participants[i])
            .update({rooms: admin.firestore.FieldValue.arrayUnion(roomId)});

        functions.logger.info(
            "User $participants now in room $roomId",
            {structuredData: true});
      }
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
