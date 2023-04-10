package cnam.smb116.tp7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cnam.smb116.tp7.StationBusiness.Station;

public class StationDetailActivity extends AppCompatActivity {

    private TextView stationName, stationLattitude, stationLongitude, stationCapacite, stationCode, stationVelos, stationEmplacements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        stationVelos = findViewById(R.id.stationVelos);
        stationLattitude = findViewById(R.id.stationLattitude);
        stationCode = findViewById(R.id.stationCode);
        stationCapacite = findViewById(R.id.stationCapacite);
        stationName = findViewById(R.id.stationName);
        stationLongitude = findViewById(R.id.stationLongitude);
        stationEmplacements = findViewById(R.id.stationEmplacements);

        Intent intent = getIntent();
        Station s = intent.getParcelableExtra("selectedStation");
        if (s != null) {
            stationVelos.setText(s.getNumBikesAvailable());
            stationLattitude.setText(s.getLat());
            stationCode.setText(s.getStationCode());
            stationCapacite.setText(s.getCapacity());
            stationName.setText(s.getName());
            stationLongitude.setText(s.getLon());
            stationEmplacements.setText(s.getNumDocksAvailable());
        }
    }

    protected void subscribeToStation(View v) {

    }
}
