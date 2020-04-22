package com.example.d_safe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.d_safe.ui.login.LoginActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        final Button loginButton = findViewById(R.id.btnLogin);
        final TextView regButton = findViewById(R.id.lnkLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupSuccessHome;
                signupSuccessHome = new Intent(RegistrationActivity.this, LoginActivity.class);
                signupSuccessHome.putExtra("reqFrom", "login");
                startActivity(signupSuccessHome);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupSuccessHome;
                signupSuccessHome = new Intent(RegistrationActivity.this, LoginActivity.class);
                signupSuccessHome.putExtra("reqFrom", "login");
                startActivity(signupSuccessHome);
            }
        });
    }
}
