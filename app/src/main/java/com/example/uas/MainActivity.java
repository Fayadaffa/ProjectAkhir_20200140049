package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edEmail, edPass;
    String email,password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin=findViewById(R.id.btsignin);
        edEmail = findViewById(R.id.edEmail);
        edPass=findViewById(R.id.edPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edEmail.getText().toString();
                password = edPass.getText().toString();

                String name = "Admin";
                String pass = "admin123";

                if (email.isEmpty() || password.isEmpty()) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Username dan Password wajib diisi!!!",
                            Toast.LENGTH_LONG);
                } else {
                    if (email.equals(name) && password.equals(pass)) {
                        Toast t = Toast.makeText(getApplicationContext(),
                                "Login Sukses",
                                Toast.LENGTH_LONG);
                        t.show();

                        Intent i = new Intent(getApplicationContext(), Homepage.class);
                        startActivity(i);

                    } else {
                        if (email.isEmpty() || password.equals(pass)) {
                            Toast t = Toast.makeText(getApplicationContext(),
                                    "Username salah",
                                    Toast.LENGTH_LONG);
                            t.show();
                        } else {
                            if (email.equals(name)) {
                                Toast t = Toast.makeText(getApplicationContext(),
                                        "Password salah",
                                        Toast.LENGTH_LONG);
                                t.show();
                            }
                        }


                    }
                }
            }
        });
    }
}