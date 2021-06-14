package com.example.geobirdclient.ui.map;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.Target.TargetResponse;
import com.example.geobirdclient.ui.qr.QrScanFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private Activity activity;
    private MapViewModel mViewModel;
    private MapView mapView;
    GoogleMap googleMap;
    private Button findNewObject;
    private Button scanobject;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        activity = getActivity();
        mapView =  root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        addAction();
        return root;
    }

    private void addAction() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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




//https://javapapers.com/android/get-current-location-in-android/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        MapsInitializer.initialize(this.getActivity());

        System.out.println("XFDD");
        //pobieranie danych dot. celow
        TargetService targetService = Api.getRetrofit().create(TargetService.class);
        Call<List<Target>> call = targetService.getTargets();

        call.enqueue(new Callback<List<Target>>() {
            @Override
            public void onResponse(Call<List<Target>> call, Response<List<Target>> response) {
                List<Target> targetList = response.body();
                System.out.println("pokaz targets: " + targetList);

                List<LatLng> latLngO = new ArrayList<>();


                for (int i = 0; i < targetList.size(); i++){
                    LatLng location = new LatLng(targetList.get(i).getLocX(), targetList.get(i).getLocY());
                    latLngO.add(location);
                }
// dodawanie punktow na mapie


                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        for (int i = 0; i <= latLngO.size(); i++){
                            latLng = latLngO.get(i);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                            googleMap.clear();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(markerOptions);
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Target>> call, Throwable t) {

            }
        });
    }
}