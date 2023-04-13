package cnam.smb116.tp7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cnam.smb116.tp7.StationBusiness.Station;

public class StationDetailActivity extends AppCompatActivity {
    private String stationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        // Affiche les informations de la station dans l'IHM
        TextView stationVelos = findViewById(R.id.stationVelos);
        TextView stationLattitude = findViewById(R.id.stationLattitude);
        TextView stationCode = findViewById(R.id.stationCode);
        TextView stationCapacite = findViewById(R.id.stationCapacite);
        TextView stationName = findViewById(R.id.stationName);
        TextView stationLongitude = findViewById(R.id.stationLongitude);
        TextView stationEmplacements = findViewById(R.id.stationEmplacements);

        Intent intent = getIntent();
        Station s = intent.getParcelableExtra("selectedStation");
        if (s != null) {
            stationId = s.getStation_id();
            stationVelos.setText(s.getNumBikesAvailable());
            stationLattitude.setText(s.getLat());
            stationCode.setText(s.getStationCode());
            stationCapacite.setText(s.getCapacity());
            stationName.setText(s.getName());
            stationLongitude.setText(s.getLon());
            stationEmplacements.setText(s.getNumDocksAvailable());
        }
    }

    // On arrête le service au cas où il tournait et on le relance pour ne pas dupliquer les notifications
    public void subscribeToStation(View v) {
        Intent intentService = new Intent(this, SubscribeStationService.class);
        stopService(intentService);
        intentService.putExtra("stationId", stationId);
        startService(intentService);
        Toast.makeText(this, "Inscription à la station terminée.", Toast.LENGTH_SHORT).show();
    }
}
