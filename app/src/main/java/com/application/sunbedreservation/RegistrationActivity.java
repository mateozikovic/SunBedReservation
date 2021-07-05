package com.application.sunbedreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.database.models.UserModel;

public class RegistrationActivity extends AppCompatActivity {

    EditText first_name, last_name, e_mail, password, city, address, country, postal_code;
    Button register_form_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        first_name = findViewById(R.id.first_name_textbox);
        last_name = findViewById(R.id.last_name_textbox);
        e_mail = findViewById(R.id.email_reg_textbox);
        password = findViewById(R.id.password_reg_textbox);
        city = findViewById(R.id.city_textbox);
        address = findViewById(R.id.address_textbox);
        country = findViewById(R.id.country_textbox);
        postal_code = findViewById(R.id.postal_code_textbox);

        String postalCode = postal_code.getText().toString();
        int postalCodeString = Integer.parseInt(postalCode);

        register_form_btn = findViewById(R.id.register_form_btn);

        register_form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel(-1, first_name.getText().toString(),
                                                        last_name.getText().toString(),
                                                        e_mail.getText().toString(),
                                                        password.getText().toString(),
                                                        city.getText().toString(),
                                                        address.getText().toString(),
                                                        country.getText().toString(),
                                                        postalCodeString);
            }
        });

        //TODO add forms and buttons for registration, and fix form in layout
    }
}