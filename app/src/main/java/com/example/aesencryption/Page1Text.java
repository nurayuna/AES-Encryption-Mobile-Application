package com.example.aesencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Page1Text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1_text);

        Button encd = findViewById(R.id.encd);
        Button decd = findViewById(R.id.decd);

        encd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(Page1Text.this, TextEncryptActivity.class);
                startActivity(int1);
            }
        });

        decd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2 = new Intent(Page1Text.this, TextDecryptActivity.class);
                startActivity(int2);
            }
        });
    }
}
