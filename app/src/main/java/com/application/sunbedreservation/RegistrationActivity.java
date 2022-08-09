package com.application.sunbedreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView registerButton, banner;
    private EditText fullNameEditText, registerEmailEditText, editTextPhone, registerPasswordEditText;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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
        switch (view.getId()){
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

        if(fullName.isEmpty()){
            fullNameEditText.setError("Full name is required!");
            fullNameEditText.requestFocus();
            return;
        }

        if(email.isEmpty()){
            registerEmailEditText.setError("E-mail is required!");
            registerEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmailEditText.setError("Please provide a valid e-mail.");
            registerEmailEditText.requestFocus();
        }

        if(password.isEmpty()){
            registerPasswordEditText.setError("Password is required!");
            registerPasswordEditText.requestFocus();
        }

        if(password.length() < 6 ){
            registerPasswordEditText.setError("Min password length is 6!");
            registerPasswordEditText.requestFocus();
        }

        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required!");
            editTextPhone.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

    }
}