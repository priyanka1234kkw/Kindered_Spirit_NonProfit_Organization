package com.example.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity3 extends AppCompatActivity {
public ImageButton ib2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });
            ib2 = (ImageButton) findViewById(R.id.medImage1);

            ib2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity3.this, ReceiverActivityMedicine.class);
                    startActivity(intent);
                }
            });

        ib2 = (ImageButton) findViewById(R.id.clothingImage);

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, stationary_receiver.class);
                startActivity(intent);
            }
        });

        ib2 = (ImageButton) findViewById(R.id.elecImage);

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, ReceiverActivity.class);
                startActivity(intent);
            }
        });

        ib2 = (ImageButton) findViewById(R.id.CImage);

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, ReceiverActivityCloth.class);
                startActivity(intent);
            }
        });

    }
}
