package com.example.vazismart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vazismart.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        final Button nextButton = findViewById(R.id.buttonNext);

        nextButton.setOnClickListener(v -> {
            Intent nextActivity = new Intent(IntroActivity.this, SignInActivity.class);
            startActivity(nextActivity);
            finish();
        });
    }
}