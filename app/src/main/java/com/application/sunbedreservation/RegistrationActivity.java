package com.application.sunbedreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/*
    Activity for registering a user into the Firebase db
* */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerButton, banner;
    private EditText fullNameEditText, registerEmailEditText, editTextPhone, registerPasswordEditText;
    private ProgressBar progressBar;

    //
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Firebase instance
        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        fullNameEditText = (EditText) findViewById(R.id.fullNameEditText);
        registerEmailEditText = (EditText) findViewById(R.id.registerEmailEditText);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        registerPasswordEditText = (EditText) findViewById(R.id.editTextPhone);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = registerEmailEditText.getText().toString().trim();
        String password = registerPasswordEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            fullNameEditText.setError("Full name is required!");
            fullNameEditText.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            registerEmailEditText.setError("E-mail is required!");
            registerEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmailEditText.setError("Please provide a valid e-mail.");
            registerEmailEditText.requestFocus();
        }

        if (password.isEmpty()) {
            registerPasswordEditText.setError("Password is required!");
            registerPasswordEditText.requestFocus();
        }

        if (password.length() < 6) {
            registerPasswordEditText.setError("Min password length is 6!");
            registerPasswordEditText.requestFocus();
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone number is required!");
            editTextPhone.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        // Create User in Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, email, phone);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegistrationActivity.this, "User has been registered succesfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                Log.i("login", "Succesfull");
                                                // redirect to login layout!
                                            } else {
                                                Toast.makeText(RegistrationActivity.this, "Failed to register... Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Failed to register... Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}