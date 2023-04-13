package cnam.smb116.tp7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import cnam.smb116.tp7.StationBusiness.Station;

public class StationListActivity extends AppCompatActivity {
    private ListView stationList;
    private HashMap<String, Station> hmap_stations = new HashMap<>();
    private ArrayList<Station> stations;

    private TextView textWait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        stationList = findViewById(R.id.listStation);
        textWait = findViewById(R.id.textWait);
        stations = new ArrayList<>();

        populateStationList();
    }

    private void populateStationList() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Chargement des stations et des disponibilité
                if(Station.loadStations(hmap_stations, stations) && Station.loadCapacity(hmap_stations)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // On peuple stationList avec les stations récupérées
                            ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(StationListActivity.this,
                                    android.R.layout.simple_list_item_1, stations);
                            stationList.setAdapter(adapter);
                            textWait.setVisibility(View.INVISIBLE);
                            stationList.setVisibility(View.VISIBLE);
                            // Fonction exécutée à l'appuie d'un des éléments de la liste de stations
                            // Accède aux détails de la station choisie
                            stationList.setOnItemClickListener((adapterView, view, i, l) -> {
                                Station s = adapter.getItem(i);
                                Intent intent = new Intent(StationListActivity.this, StationDetailActivity.class);
                                intent.putExtra("selectedStation", s);
                                startActivity(intent);
                            });

                        }
                    });
                }

            }
        });
        t.start();
    }
}
