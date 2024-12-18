package com.example.universitymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SigninPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // Firestore instance
    private EditText nameField, emailField, passwordField, confirmPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Check if the user is already logged in
        if (mAuth.getCurrentUser() != null) {
            goToMainMap();
            return;
        }

        // Initialize views
        nameField = findViewById(R.id.editTextName);
        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword);

        Button signUpButton = findViewById(R.id.button2);
        Button loginButton = findViewById(R.id.login);

        // Set button listeners
        signUpButton.setOnClickListener(view -> signUpUser());
        loginButton.setOnClickListener(view -> goToLogin());
    }

    private void signUpUser() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String confirmPassword = confirmPasswordField.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty()) {
            showToast("Name must not be empty");
            return;
        }

        if (email.isEmpty()) {
            showToast("Email must not be empty");
            return;
        }

        if (password.isEmpty()) {
            showToast("Password must not be empty");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return;
        }

        if (password.length() < 6) {
            showToast("Password must be at least 6 characters");
            return;
        }

        // Create user in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast("Account created successfully!");
                        saveUserToFirestore(name, email);
                    } else {
                        showToast("Account creation failed. " + task.getException().getMessage());
                    }
                });
    }

    private void saveUserToFirestore(String name, String email) {
        String userId = mAuth.getCurrentUser().getUid();

        // Create a user map to store in Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        // Save user details in Firestore
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    showToast("User details saved!");
                    goToMainMap();
                })
                .addOnFailureListener(e -> showToast("Failed to save user details: " + e.getMessage()));
    }

    private void goToLogin() {
        Intent loginIntent = new Intent(SigninPage.this, LoginPage.class);
        startActivity(loginIntent);
        finish();
    }

    private void goToMainMap() {
        Intent mainMapIntent = new Intent(SigninPage.this, MainActivity.class);
        startActivity(mainMapIntent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(SigninPage.this, message, Toast.LENGTH_SHORT).show();
    }
}
