package com.example.booksharing_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    private EditText firstName, lastName, age, mobile, email, password, confirmPassword;
    private CheckBox termsCheckBox;
    private Button saveButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.editText_firstName);
        lastName = findViewById(R.id.editText_lastName);
        age = findViewById(R.id.editText_age);
        mobile = findViewById(R.id.editText_mobile);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        confirmPassword = findViewById(R.id.editText_confirmPassword);
        termsCheckBox = findViewById(R.id.checkBox_terms);
        saveButton = findViewById(R.id.button_save);
        backButton = findViewById(R.id.button_back);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();

                if (pass.equals(confirmPass)) {
                    if (termsCheckBox.isChecked()) {
                        Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signup.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Signup.this, "You must accept the terms and conditions", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

