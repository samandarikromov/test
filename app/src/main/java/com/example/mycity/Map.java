package com.example.mycity;

import android.os.Bundle;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import androidx.appcompat.app.AppCompatActivity;

public class Map extends AppCompatActivity {
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapview);
        // Markaziy joylashuvni sozlash (masalan, Toshkent koordinatalari)
        mapView.getMap().move(
                new CameraPosition(
                        new com.yandex.mapkit.geometry.Point(41.2995, 69.2401), // Toshkent koordinatalari
                        14.0f, // Zoom darajasi
                        0.0f,  // Azimut
                        0.0f   // Tilt
                )
        );
    }
    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

//    private class CameraPosition extends com.yandex.mapkit.map.CameraPosition {
//        public CameraPosition(Point point, float v, float v1, float v2) {
//
//        }
//    }
}