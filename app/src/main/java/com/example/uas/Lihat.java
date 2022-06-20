package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class Lihat extends AppCompatActivity {

    private TextView nis, name, gender, asal;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);

        nis = findViewById(R.id.tvnis);
        name = findViewById(R.id.tvname);
        gender = findViewById(R.id.tvgender);
        asal = findViewById(R.id.tvasal);

        progressDialog = new ProgressDialog(Lihat.this);
        progressDialog.setTitle("Menunggu");
        progressDialog.setMessage("Sedang Mengambil Data...");

        Intent intent = getIntent();
        if (intent != null){
            nis.setText(intent.getStringExtra("nis"));
            name.setText(intent.getStringExtra("name"));
            gender.setText(intent.getStringExtra("gender"));
            asal.setText(intent.getStringExtra("asal"));
        }
    }
}