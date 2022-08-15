import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const addToForum =  functions.auth.user().onCreate((user) => {
    admin.firestore().collection("rooms").doc("general").update({test: admin.firestore.FieldValue.arrayUnion(user.uid)})
    functions.logger.info("Added user ${auth.uid}", {structuredData: true});
});

export const removeFromForum =  functions.auth.user().onCreate((user) => {
    admin.firestore().collection("rooms").doc("general").update({test: admin.firestore.FieldValue.arrayRemove(user.uid)})
    functions.logger.info("Removed user ${auth.uid}", {structuredData: true});
});