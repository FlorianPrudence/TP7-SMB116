package cnam.smb116.tp7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static String CHANNEL_ID = "4e2cd5a7-8818-48e3-8cba-b7ac8f1e0e81";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }

    public void loadStations(View v) {
        Intent intent = new Intent(MainActivity.this, StationListActivity.class);
        startActivity(intent);
    }

    // On créer un NotificationChannel pour pouvoir envoyer des notifications
    // N'est pas recréé s'il existe déjà
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Station";
            String description = "Affiche les notifications concernant les stations de Velib";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}