package com.jmajyo.pingmyserver.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.jmajyo.pingmyserver.activities.MainActivity;
import com.jmajyo.pingmyserver.util.Notifications;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class PingService extends IntentService{

    public PingService(){
        this("PingService");
    }

    public PingService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String s = intent.getStringExtra("TEXT");
        Log.d("PING_SERVICE", "Hello World! " + s);
        pingServer();
        Log.d("PING_SERVICE", "Tarea finalizada ");

        //Notifications.postNotification(this, MainActivity.class, "Hola hola", "Este es el texto",android.R.drawable.ic_menu_call, 0xFF00FF00, 889988);

    }

    private void pingServer() {
        Socket socket;
        final String host = "192.168.1.1";
        final int port = 80;
        final int timeout = 30000;   // 30 seconds
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeout);
            Notifications.postNotification(this, MainActivity.class, "Cocreta viva", "Muy viva",android.R.drawable.ic_menu_call, 0xFF00FF00, 889988);
        }
        catch (UnknownHostException uhe) {
            Log.e("ServerSock", "I couldn't resolve the host you've provided!");
        }
        catch (SocketTimeoutException ste) {
            Log.e("ServerSock", "After a reasonable amount of time, I'm not able to connect, Server is probably down!");
        }
        catch (IOException ioe) {
            Notifications.postNotification(this, MainActivity.class, "Cocreta muerta", "PUM",android.R.drawable.ic_menu_call, 0xFFFF0000, 889988);
            Log.e("ServerSock", "Hmmm... Sudden disconnection, probably you should start again!");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("PingService", "It´s alive!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("PingService", "My mind is going...");
    }

    public static void startPingService(Context context, String s, int millis) {
        Intent i = new Intent(context, PingService.class);
        i.putExtra("TEXT", s);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                millis,
                pendingIntent
        );

        //startService(i);
    }

    public static void stopPingService(Context context) {
        Intent i = new Intent(context, PingService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
