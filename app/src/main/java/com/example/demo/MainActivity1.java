package com.example.demo;

import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity1 extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText mail, pass;
    private Button b1,b2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_activity1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mail = findViewById(R.id.et1);
        pass = findViewById(R.id.et2);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mail.getText().toString().trim();
                String passw = pass.getText().toString().trim();

                if (user.isEmpty()) {
                    mail.setError("Email cannot be empty");
                    return;
                }
                if (passw.isEmpty()) {
                    pass.setError("Password cannot be empty");
                    return;
                }

                // Use signInWithEmailAndPassword to log in existing users
                auth.signInWithEmailAndPassword(user, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity1.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity1.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mail.getText().toString().trim();
                String passw = pass.getText().toString().trim();

                if (user.isEmpty()) {
                    mail.setError("Email cannot be empty");
                    return;
                }
                if (passw.isEmpty()) {
                    pass.setError("Password cannot be empty");
                    return;
                }

                // Use signInWithEmailAndPassword to log in existing users
                auth.signInWithEmailAndPassword(user, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity1.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity1.this, MainActivity3.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity1.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
