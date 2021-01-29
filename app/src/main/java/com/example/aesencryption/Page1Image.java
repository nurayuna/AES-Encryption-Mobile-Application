package com.example.aesencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Page1Image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1_image);

        Button encI = findViewById(R.id.encI);
        Button decI = findViewById(R.id.decI);

        encI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(Page1Image.this, ImageEncryptActivity.class);
                startActivity(int1);
            }
        });

        decI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2 = new Intent(Page1Image.this, ImageDecryptActivity.class);
                startActivity(int2);
            }
        });
    }
}
