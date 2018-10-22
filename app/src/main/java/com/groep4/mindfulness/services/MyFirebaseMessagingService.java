package com.groep4.mindfulness.services;


import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.provider.FirebaseInitProvider;

/**
 * MyFirebaseMessagingService zorgt ervoor dat als de token aangeroepen word, deze doorgestuurd word naar de server.
 * Deze zou bij login gebruikt moeten worden.
 *
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
/**
 * TODO: zet hier backend code voor token op te slaan
 */


    }

}




