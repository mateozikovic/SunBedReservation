package com.application.sunbedreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText first_name, last_name, e_mail, city, address, country, postal_code;
    Button register_form_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        register_form_btn = findViewById(R.id.register_form_btn);
        register_form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        //TODO add forms and buttons for registration, and fix form in layout
    }
}