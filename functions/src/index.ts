import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const addToForum = functions.auth.user().onCreate((user) => {
  admin.firestore().collection("rooms").doc("general")
      .update({participants: admin.firestore.FieldValue.arrayUnion(user.uid)***REMOVED***);
  functions.logger.info("Added user ${auth.uid***REMOVED***", {structuredData: true***REMOVED***);
***REMOVED***);

export const removeFromForum = functions.auth.user().onDelete((user) => {
  admin.firestore().collection("rooms").doc("general")
      .update({participants: admin.firestore.FieldValue.arrayRemove(user.uid)***REMOVED***);
  functions.logger.info(`Removed user ${user.uid***REMOVED***`, {structuredData: true***REMOVED***);
***REMOVED***);

// export const addPhotoUrl =
//     functions.storage.object().onFinalize(async (object) => {
//       const path = object.name as string;
//       const userId = path.slice(path.lastIndexOf("/")+1);
//
//       if (userId) {
//         admin.firestore().collection("users").doc(userId)
//             .update({profilePic: object.mediaLink***REMOVED***);
//         functions.logger.info(
//             `Added photo link for user ${userId***REMOVED***`,
//             {structuredData: true***REMOVED***);
//       ***REMOVED***
//     ***REMOVED***);
