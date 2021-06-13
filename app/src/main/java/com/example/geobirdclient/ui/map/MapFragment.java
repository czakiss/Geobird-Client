package com.example.geobirdclient.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.Target.TargetResponse;
import com.example.geobirdclient.ui.qr.QrScanFragment;
import com.example.geobirdclient.ui.qr.QrScanViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapView;
    GoogleMap googleMap;
    private Button findNewObject;
    private Button scanobject;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        findNewObject = findViewById(R.id.find_new_possition);
        scanobject = findViewById(R.id.scanner_view);

        addAction();
    }

    private void addAction() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

        //-----------------SZUKANIE W POBLIZU
        locationManager.getAllProviders();
        //locationManager.GPS_PROVIDER. https://developer.android.com/reference/android/location/LocationManager#GPS_PROVIDER


        scanobject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this, QrScanFragment.class);
                startActivity(intent);
            }
        });

//https://javapapers.com/android/get-current-location-in-android/
    }
//https://developer.android.com/training/location/change-location-settings




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //pobieranie danych dot. celow
        TargetService targetService = Api.getRetrofit().create(TargetService.class);
        Call<List<Target>> call = targetService.getTargets();

        call.enqueue(new Callback<List<Target>>() {
            @Override
            public void onResponse(Call<List<Target>> call, Response<List<Target>> response) {
                TargetResponse targetResponse = (TargetResponse) response.body();
                List<Target> targetList = (List<Target>) targetResponse.getTargetX();
                System.out.println("pokaz targets: " + targetList);

                mapView = googleMap;
                List<LatLng> latLngO = new ArrayList<>();


                for (int i = 0; i <= targetList.size(); i++){
                    LatLng location = new LatLng(targetList.get(i).getLocX(), targetList.get(i).getLocY());
                    latLngO.add(location);
                }
// dodawanie punktow na mapie
                mapView.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        for (int i = 0; i <= latLngO.size(); i++){
                            latLng = latLngO.get(i);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                            mapView.clear();
                            mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            mapView.addMarker(markerOptions);
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Target>> call, Throwable t) {

            }
        });
    }

///https://www.youtube.com/watch?v=2ppri1ovIQA

}
