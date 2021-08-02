package com.someshswami9.onlineshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button mainLogInButton, signUpButton;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLogInButton =(Button) findViewById(R.id.mainLogInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        mainLogInButton.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
        });
        signUpButton.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}