package com.example.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {

    private EditText editNis, editName, editJK, editAsal;
    private Button btnSave;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editNis = findViewById(R.id.nis);
        editName = findViewById(R.id.name);
        editJK = findViewById(R.id.gender);
        editAsal = findViewById(R.id.asal);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(Edit.this);
        progressDialog.setTitle("Menunggu");
        progressDialog.setMessage("Menyimpan Data...");

        btnSave.setOnClickListener(v->{
            if (editNis.getText().length() > 0 && editName.getText().length() > 0 && editJK.getText().length() > 0 && editAsal.getText().length() > 0 && editAsal.getText().length() > 0){

                saveData(editNis.getText().toString(), editName.getText().toString(), editJK.getText().toString(), editAsal.getText().toString());
            }else {
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent!= null){
            id = intent.getStringExtra("id");
            editName.setText(intent.getStringExtra("name"));
            editJK.setText(intent.getStringExtra("gender"));
            editAsal.setText(intent.getStringExtra("asal"));
        }
    }

    private void saveData(String nis, String name, String gender, String asal) {
        Map<String, Object> user = new HashMap<>();
        user.put("nis", nis);
        user.put("name", name);
        user.put("gender", gender);
        user.put("asal", asal);

        progressDialog.show();

        if (id != null){

            db.collection("users").document(name)
                    .set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Edit.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Edit.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {

            db.collection("users").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(Edit.this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Edit.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}