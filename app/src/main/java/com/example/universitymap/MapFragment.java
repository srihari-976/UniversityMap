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
        put("LS", createPolygon(
                new LatLng(13.16818, 77.53320),
                new LatLng(13.16823, 77.53270),
                new LatLng(13.16832, 77.53285),
                new LatLng(13.16817, 77.53317)
        ));
        put("NS", createPolygon(
                new LatLng(13.167983, 77.533650),
                new LatLng(13.168133, 77.533800),
                new LatLng(13.168483, 77.533833),
                new LatLng(13.167975, 77.533642)
        ));
        put("PS", createPolygon(
                new LatLng(13.16785, 77.53357),
                new LatLng(13.16788, 77.53397),
                new LatLng(13.16780, 77.53380),
                new LatLng(13.16782, 77.53390)
        ));
        put("QS", createPolygon(
                new LatLng(13.16742, 77.53370),
                new LatLng(13.16747, 77.53348),
                new LatLng(13.16762, 77.53335),
                new LatLng(13.16737, 77.53358)
        ));
        put("RS", createPolygon(
                new LatLng(13.16743, 77.53327),
                new LatLng(13.16735, 77.53363),
                new LatLng(13.16735, 77.53363),
                new LatLng(13.16740, 77.53338)
        ));
        put("SS", createPolygon(
                new LatLng(13.167617, 77.533250),
                new LatLng(13.167633, 77.533253),
                new LatLng(13.167367, 77.533167),
                new LatLng(13.167417, 77.533117)
        ));
        put("US", createPolygon(
                new LatLng(13.167300, 77.533483),
                new LatLng(13.167217, 77.533533),
                new LatLng(13.167233, 77.533517),
                new LatLng(13.167242, 77.533508)
        ));
        put("LT", createPolygon(
                new LatLng(13.16820, 77.53333),
                new LatLng(13.16817, 77.53267),
                new LatLng(13.16818, 77.53333),
                new LatLng(13.16828, 77.53290)
        ));
        put("NT", createPolygon(
                new LatLng(13.168133, 77.533567),
                new LatLng(13.168083, 77.533550),
                new LatLng(13.168083, 77.533800),
                new LatLng(13.168067, 77.533783)
        ));
        put("PT", createPolygon(
                new LatLng(13.16792, 77.53377),
                new LatLng(13.16787, 77.53398),
                new LatLng(13.16790, 77.53385),
                new LatLng(13.16788, 77.53380)
        ));
        put("QT", createPolygon(
                new LatLng(13.16765, 77.53320),
                new LatLng(13.16773, 77.53342),
                new LatLng(13.16747, 77.53365),
                new LatLng(13.16747, 77.53367)
        ));
        put("RT", createPolygon(
                new LatLng(13.16757, 77.53340),
                new LatLng(13.16763, 77.53343),
                new LatLng(13.16752, 77.53362),
                new LatLng(13.16753, 77.53345)
        ));
        put("ST", createPolygon(
                new LatLng(13.16728, 77.53312),
                new LatLng(13.16773, 77.53327),
                new LatLng(13.16730, 77.53315),
                new LatLng(13.16763, 77.53328)
        ));
        put("UT", createPolygon(
                new LatLng(13.16727, 77.53340),
                new LatLng(13.16727, 77.53355),
                new LatLng(13.16730, 77.53337),
                new LatLng(13.16732, 77.53348)
        ));
        put("N4", createPolygon(
                new LatLng(13.167900, 77.533733),
                new LatLng(13.168083, 77.533867),
                new LatLng(13.168133, 77.533883),
                new LatLng(13.167933, 77.533783)
        ));
        put("P4", createPolygon(
                new LatLng(13.167683, 77.533650),
                new LatLng(13.167883, 77.533467),
                new LatLng(13.167675, 77.533692),
                new LatLng(13.167708, 77.533675)
        ));
        put("Q4", createPolygon(
                new LatLng(13.167617, 77.533283),
                new LatLng(13.167600, 77.533267),
                new LatLng(13.167533, 77.533667),
                new LatLng(13.167525, 77.533625)
        ));
        put("R4", createPolygon(
                new LatLng(13.167500, 77.533500),
                new LatLng(13.167517, 77.533508),
                new LatLng(13.167483, 77.533483),
                new LatLng(13.167533, 77.533517)
        ));
        put("S4", createPolygon(
                new LatLng(13.167400, 77.533117),
                new LatLng(13.167383, 77.533133),
                new LatLng(13.167683, 77.533300),
                new LatLng(13.167542, 77.533442)
        ));
        put("U4", createPolygon(
                new LatLng(13.167233, 77.533300),
                new LatLng(13.167617, 77.533500),
                new LatLng(13.167650, 77.533533),
                new LatLng(13.167250, 77.533183)
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
