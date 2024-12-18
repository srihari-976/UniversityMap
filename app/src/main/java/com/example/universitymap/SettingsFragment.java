package com.example.universitymap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // UI components
    private CheckBox campusEventsCheckBox;
    private CheckBox buildingUpdatesCheckBox;
    private CheckBox emergencyAlertsCheckBox;
    private Switch locationSharingSwitch;
    private Switch dataUsageSwitch;
    private TextView appVersionTextView;
    private Button clearCacheButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI components
        campusEventsCheckBox = view.findViewById(R.id.campusEvents);
        buildingUpdatesCheckBox = view.findViewById(R.id.buildingUpdates);
        emergencyAlertsCheckBox = view.findViewById(R.id.emergencyAlerts);
        locationSharingSwitch = view.findViewById(R.id.locationSharing);
        dataUsageSwitch = view.findViewById(R.id.dataUsage);
        appVersionTextView = view.findViewById(R.id.appVersion);
        clearCacheButton = view.findViewById(R.id.clearCacheButton);

        // Set app version text
        appVersionTextView.setText(getString(R.string.app_version_1_0_0));

        // Set listeners for checkboxes
        campusEventsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), isChecked ? "Campus Events Notifications Enabled" : "Campus Events Notifications Disabled", Toast.LENGTH_SHORT).show();
        });

        buildingUpdatesCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), isChecked ? "Building Updates Notifications Enabled" : "Building Updates Notifications Disabled", Toast.LENGTH_SHORT).show();
        });

        emergencyAlertsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), isChecked ? "Emergency Alerts Enabled" : "Emergency Alerts Disabled", Toast.LENGTH_SHORT).show();
        });

        // Set listeners for switches
        locationSharingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), isChecked ? "Location Sharing Enabled" : "Location Sharing Disabled", Toast.LENGTH_SHORT).show();
        });

        dataUsageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), isChecked ? "Data Usage Saving Enabled" : "Data Usage Saving Disabled", Toast.LENGTH_SHORT).show();
        });

        // Set listener for clear cache button
        clearCacheButton.setOnClickListener(v -> {
            // Implement cache clearing logic here
            Toast.makeText(getContext(), "Cache Cleared Successfully", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
