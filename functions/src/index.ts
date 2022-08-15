import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const addToForum =  functions.auth.user().onCreate((user) => {
    admin.firestore().collection("rooms").doc("general").update({test: admin.firestore.FieldValue.arrayUnion(user.uid)***REMOVED***)
    functions.logger.info("Added user ${auth.uid***REMOVED***", {structuredData: true***REMOVED***);
***REMOVED***);

export const removeFromForum =  functions.auth.user().onCreate((user) => {
    admin.firestore().collection("rooms").doc("general").update({test: admin.firestore.FieldValue.arrayRemove(user.uid)***REMOVED***)
    functions.logger.info("Removed user ${auth.uid***REMOVED***", {structuredData: true***REMOVED***);
***REMOVED***);