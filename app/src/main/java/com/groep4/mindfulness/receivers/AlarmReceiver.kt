package com.groep4.mindfulness.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.groep4.mindfulness.services.NotificationService
import android.os.PowerManager



class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "mindfulness:mywakelocktag")

        // acquire the lock
        wl.acquire()

        //
        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))

        context.startService(service)
        //

        // release the lock
        wl.release()
    }
}