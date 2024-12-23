package com.example.universitymap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;
    private TextView infoTextView;

    // Polygons for blocks
    private final Map<String, List<LatLng>> blockPolygons = new HashMap<String, List<LatLng>>() {{
        put("U4", createPolygon(
                new LatLng(),
                new LatLng(),
                new LatLng(),
                new LatLng()
        ));
        // Add other blocks here...
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.map);
        infoTextView = view.findViewById(R.id.textInfo);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Set the default location
        LatLng defaultLocation = new LatLng(13.169857422901195, 77.53530119314236);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 17)); // Zoom level 17

        // Check location permissions
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);
        drawPolygons();

        // Get user's last known location and update camera
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17));

                        String currentBlock = getCurrentBlock(userLocation);

                        mMap.addMarker(new MarkerOptions()
                                .position(userLocation)
                                .title("You are here: " + (currentBlock != null ? currentBlock : "Outside blocks")));

                        infoTextView.setText("Current Block: " + (currentBlock != null ? currentBlock : "Outside blocks"));
                    }
                });
    }


    private void drawPolygons() {
        for (Map.Entry<String, List<LatLng>> entry : blockPolygons.entrySet()) {
            mMap.addPolygon(new PolygonOptions()
                    .addAll(entry.getValue())
                    .strokeColor(0xFF000000)  // Black border
                    .fillColor(0x7F00FF00)); // Semi-transparent green
        }
    }

    private String getCurrentBlock(LatLng userLocation) {
        for (Map.Entry<String, List<LatLng>> entry : blockPolygons.entrySet()) {
            if (isPointInPolygon(userLocation, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean isPointInPolygon(LatLng point, List<LatLng> polygon) {
        int intersectCount = 0;
        for (int j = 0; j < polygon.size() - 1; j++) {
            if (rayCastIntersect(point, polygon.get(j), polygon.get(j + 1))) {
                intersectCount++;
            }
        }
        return (intersectCount % 2) == 1;
    }

    private boolean rayCastIntersect(LatLng point, LatLng vertA, LatLng vertB) {
        double ax = vertA.longitude;
        double ay = vertA.latitude;
        double bx = vertB.longitude;
        double by = vertB.latitude;
        double px = point.longitude;
        double py = point.latitude;

        if ((ay > py && by > py) || (ay < py && by < py) || (ax < px && bx < px)) {
            return false;
        }

        double slope = (bx - ax) == 0 ? Double.MAX_VALUE : (by - ay) / (bx - ax);
        double intersectX = slope == Double.MAX_VALUE ? ax : ax + (py - ay) / slope;
        return intersectX > px;
    }

    private List<LatLng> createPolygon(LatLng... points) {
        List<LatLng> polygon = new ArrayList<>();
        for (LatLng point : points) {
            polygon.add(point);
        }
        polygon.add(points[0]);
        return polygon;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
