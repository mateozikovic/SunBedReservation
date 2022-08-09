package com.application.sunbedreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/*
 * Activity for logging in, with login button, registration button and forgot password Button.
 * */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerTextView = (TextView) findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerTextView:
                startActivity(new Intent(this, RegistrationActivity.class));
        }
    }
}