//import firebase functions modules
const functions = require('firebase-functions');
//import admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


// Listens for new messages added to messages/:pushId

exports.pushNotifications = functions.firestore
  .document(`partnersPanel/maids/instant_maids_requirement/{newUserId}`)
  .onCreate(async (snap, context) => {

    var valueObject = event.data.val();
    if(valueObject.confirmStatus === false) {

 // Create a notification
  const payload = {
       notification: {
          title:'Maids App',
          body: "You have a new work request.",
          sound: "default"
     	},
         };
    }

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };


    return admin.messaging().sendToTopic("maidsNotifications", payload, options);
});
