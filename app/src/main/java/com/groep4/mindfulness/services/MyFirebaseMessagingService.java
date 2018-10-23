package com.groep4.mindfulness.services;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Retrofit;

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

    /**
     * Sla de token op naar backend.
     * @param token
     */
    private void sendRegistrationToServer(String token) {
         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("localhost:3000")
                // .addConverterFactory(GsonConverterFactory.create())
                .build();

         DbService service = retrofit.create(DbService.class);
         //service.
    }

    @Override
    public void onMessageReceived(RemoteMessage var1) {

    }
}




