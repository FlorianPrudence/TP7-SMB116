package cnam.smb116.tp7;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import cnam.smb116.tp7.StationBusiness.Station;

public class SubscribeStationService extends Service {

    private Station station;
    private HashMap<String, Station> hmap_station = new HashMap<>();
    private Thread t;

    private static final int notificationId = 1;

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateSubscribedStation(intent.getStringExtra("stationId"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (t != null) {
            running.set(false);
            t.interrupt();
            t = null;
        }
    }

    private void updateSubscribedStation(String stationID) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                Station.loadStations(hmap_station, new ArrayList<>());
                running.set(true);
                while (running.get()) {

                        Station.loadCapacity(hmap_station);
                        station = hmap_station.get(stationID);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(SubscribeStationService.this, MainActivity.CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("StationUpdater")
                                .setContentText(station.getName() + " : " + station.getNumBikesAvailable() + " vélos disponibles; "  + station.getNumDocksAvailable() + " emplacements disponibles.")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(SubscribeStationService.this);
                        if(ContextCompat.checkSelfPermission(SubscribeStationService.this, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                            notificationManager.notify(notificationId, builder.build());
                        }
                    try {
                        Thread.sleep(20000);
                    } catch (Exception ex) {
                    }
                }
            }
        });
        t.start();
    }
}
