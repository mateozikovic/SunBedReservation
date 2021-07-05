package com.application.sunbedreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.database.DataBaseHelper;
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

        register_form_btn = findViewById(R.id.register_form_btn);

        register_form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel userModel;

                try {
                            int pcode = Integer.parseInt(postal_code.getText().toString());

                            userModel = new UserModel(-1, first_name.getText().toString(),
                            last_name.getText().toString(),
                            e_mail.getText().toString(),
                            password.getText().toString(),
                            city.getText().toString(),
                            address.getText().toString(),
                            country.getText().toString(),
                            pcode);
                }
                catch (Exception e){
                    Toast.makeText(RegistrationActivity.this, "Error creating user!", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1, "error", "error", "error", "error", "error", "error", "error", 1 );
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(RegistrationActivity.this);

                boolean success = dataBaseHelper.addOne(userModel);

                Toast.makeText(RegistrationActivity.this, "Success" + success, Toast.LENGTH_SHORT).show();
            }
        });

    }
}