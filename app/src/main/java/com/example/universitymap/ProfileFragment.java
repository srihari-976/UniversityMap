package com.example.universitymap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private EditText etName, etEmail, etPhone, etAddress, etBio;
    private ImageButton imgEditPhone, imgEditAddress, imgEditBio;
    private Button btnSignOut, btnSave;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etAddress = view.findViewById(R.id.etAddress);
        etBio = view.findViewById(R.id.etBio);
        imgEditPhone = view.findViewById(R.id.imgEditPhone);
        imgEditAddress = view.findViewById(R.id.imgEditAddress);
        imgEditBio = view.findViewById(R.id.imgEditBio);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnSave = view.findViewById(R.id.btnSave);

        // Disable editing for default fields
        etName.setFocusable(false);
        etEmail.setFocusable(false);

        // Set the current user's email in the email field
        if (currentUser != null) {
            etEmail.setText(currentUser.getEmail());
        }

        fetchUserData();

        // Set click listeners for the edit buttons
        imgEditPhone.setOnClickListener(v -> enableEditing(etPhone));
        imgEditAddress.setOnClickListener(v -> enableEditing(etAddress));
        imgEditBio.setOnClickListener(v -> enableEditing(etBio));

        btnSave.setOnClickListener(v -> saveUpdatedDetails());

        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            // Navigate to LoginPage activity
            Intent intent = new Intent(getContext(), LoginPage.class);
            startActivity(intent);
            requireActivity().finish();  // Close the current activity (ProfileFragment)
        });

        return view;
    }

    private void fetchUserData() {
        if (userRef != null) {
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    DataSnapshot snapshot = task.getResult();

                    // Debugging: Print all data to logs
                    System.out.println("Fetched data: " + snapshot.getValue());

                    // Get name, phone, address, and bio
                    String name = snapshot.child("name").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String bio = snapshot.child("bio").getValue(String.class);

                    // Set the values in the respective EditText fields
                    if (name != null) etName.setText(name);
                    if (phone != null) etPhone.setText(phone);
                    if (address != null) etAddress.setText(address);
                    if (bio != null) etBio.setText(bio);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void enableEditing(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.requestFocus();
        Toast.makeText(getContext(), "You can now edit this field", Toast.LENGTH_SHORT).show();
    }

    private void saveUpdatedDetails() {
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        String bio = etBio.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(bio)) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> updates = new HashMap<>();
        updates.put("phone", phone);
        updates.put("address", address);
        updates.put("bio", bio);

        if (userRef != null) {
            userRef.updateChildren(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    disableEditing(etPhone);
                    disableEditing(etAddress);
                    disableEditing(etBio);
                } else {
                    Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void disableEditing(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}