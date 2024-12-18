package com.example.universitymap;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);
        final LinearLayout mapLayout = findViewById(R.id.mapLayout);
        final LinearLayout settingsLayout = findViewById(R.id.settingsLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView profileImage = findViewById(R.id.profileImage);
        final ImageView mapImage = findViewById(R.id.mapImage);
        final ImageView settingsImage = findViewById(R.id.settingsImage);

        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView profileTxt = findViewById(R.id.profileTxt);
        final TextView mapTxt = findViewById(R.id.mapTxt);
        final TextView settingsTxt = findViewById(R.id.settingsTxt);

        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer,HomeFragment.class,null)
                        .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 1){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,HomeFragment.class,null)
                            .commit();

                    profileTxt.setVisibility(View.GONE);
                    mapTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    profileImage.setImageResource(R.drawable.profile_icon);
                    mapImage.setImageResource(R.drawable.map_icon);
                    settingsImage.setImageResource(R.drawable.settings_icon);

                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.home_selected_icon);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f, 1f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;

                }
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 2) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,ProfileFragment.class,null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    mapTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    mapImage.setImageResource(R.drawable.map_icon);
                    settingsImage.setImageResource(R.drawable.settings_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.profile_selected_icon);
//                    profileLayout.setBackgroundColor(R.drawable.round_back_profile_100);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }
            }
        });

        mapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 3){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,MapFragment.class,null)
                            .commit();
                    homeTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);
                    settingsTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);
                    settingsImage.setImageResource(R.drawable.settings_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    settingsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    mapTxt.setVisibility(View.VISIBLE);
                    mapImage.setImageResource(R.drawable.map_selected_icon);
//                    mapLayout.setBackgroundColor(R.drawable.round_back_map_100);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f, 1f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mapLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;

                }

            }
        });

        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 4) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer,SettingsFragment.class,null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);
                    mapTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);
                    mapImage.setImageResource(R.drawable.map_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    settingsTxt.setVisibility(View.VISIBLE);
                    settingsImage.setImageResource(R.drawable.settings_selected_icon);


                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    settingsLayout.startAnimation(scaleAnimation);

                    selectedTab = 4;
                }

                }
        });
    }
}