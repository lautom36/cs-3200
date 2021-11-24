package com.example.assn5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.assn5.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private Marker marker;
    private Polyline line;
    double distance;
    private LatLng userLocation;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        setupMap();
                    } else {
                        System.out.println("USER DENIED PERMISSIONS");
                    }
                }
        );

        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

    }

    // set up map if permissions are granted
    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((map) -> {
            mMap = map;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                client = LocationServices.getFusedLocationProviderClient(this);
                client.getLastLocation().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraPosition pos = new CameraPosition.Builder()
                                .target(userLocation)
                                .zoom(10)
                                .build();
                        mMap.animateCamera(
                                CameraUpdateFactory.newCameraPosition(pos)
                        );
                    }
                });
                mMap.setMyLocationEnabled(true);
            }

            binding.startButton.setOnClickListener(view -> {
                // change name of button to start
                binding.startButton.setVisibility(View.GONE);
                binding.guessButton.setVisibility(View.VISIBLE);
                // show edit text
                binding.editTextNumber.setVisibility(View.VISIBLE);
                binding.textView.setText("");
                // find new location and draw line and marker

                int latMin = 30;
                int latMax = 50;
                int lonMin = -60;
                int lonMax = -125;

                int randLat = (int)Math.floor(Math.random()*(latMax-latMin+1)+latMin);
                int randLon = (int)Math.floor(Math.random()*(lonMax-lonMin+1)+lonMin);

                LatLng newPos = new LatLng(randLat, randLon);

                marker = mMap.addMarker(new MarkerOptions().position(newPos));
                line = mMap.addPolyline(
                        new PolylineOptions()
                        .add(userLocation, newPos)
                );

                CameraPosition pos = new CameraPosition.Builder()
                        .target(newPos)
                        .zoom(10)
                        .build();
                mMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(pos)
                );

                // find how far the two points are from one another
                float[] result = new float[1];
                Location.distanceBetween(randLat,randLon, userLocation.latitude, userLocation.longitude, result);
                double distanceMeters = result[0];
                distance = distanceMeters * .000621371;
            });

            binding.guessButton.setOnClickListener(view -> {
                double guessDistance = Integer.parseInt(binding.editTextNumber.getText().toString());
                // switch buttons
                binding.guessButton.setVisibility(View.GONE);
                binding.startButton.setVisibility((View.VISIBLE));

                // make edit text invisable
                binding.editTextNumber.setVisibility(View.GONE);

                // clear line and marker
                marker.remove();
                line.remove();

                // go back to location
                CameraPosition pos = new CameraPosition.Builder()
                        .target(userLocation)
                        .zoom(10)
                        .build();
                mMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(pos)
                );

                // display message
                double difference = Math.abs( distance - guessDistance );
                DecimalFormat df = new DecimalFormat("#.##");
                if (difference < 100) {
                    binding.textView.setText(
                            "You were really close! The actual distance was "
                                    + df.format(distance)
                                    + " and your guess was "
                                    + guessDistance);
                }
                else if (difference < 500) {
                    binding.textView.setText(
                            "You were pretty close! The actual distance was "
                                    + df.format(distance)
                                    + " and your guess was "
                                    + guessDistance);
                } else {
                    binding.textView.setText(
                            "You were not close! The actual distance was "
                                    + df.format(distance)
                                    + " and your guess was "
                                    + guessDistance);
                }


            });

        });
    }
}