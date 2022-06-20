package com.example.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<User> list = new ArrayList<>();
    private UserAdapter userAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        recyclerView = findViewById(R.id.recycler_view);
        btnAdd = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(Homepage.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil Data");
        userAdapter = new UserAdapter(getApplicationContext(), list);
        userAdapter.setDialog(new UserAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus", "Lihat"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Homepage.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), Edit.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nis", list.get(pos).getNis());
                                intent.putExtra("name", list.get(pos).getName());
                                intent.putExtra("gender", list.get(pos).getGender());
                                intent.putExtra("asal", list.get(pos).getAsal());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                            case 2:
                                Intent intent1 = new Intent(getApplicationContext(), Lihat.class);
                                intent1.putExtra("id", list.get(pos).getId());
                                intent1.putExtra("nis", list.get(pos).getNis());
                                intent1.putExtra("name", list.get(pos).getName());
                                intent1.putExtra("gender", list.get(pos).getGender());
                                intent1.putExtra("asal", list.get(pos).getAsal());
                                startActivity(intent1);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(userAdapter);

        btnAdd.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Edit.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){

        progressDialog.show();

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                list.clear();
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        User user = new User(document.getString("nis"), document.getString("name"), document.getString("gender"), document.getString("email"), document.getString("asal"));
                        user.setId(document.getId());
                        list.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to take Data!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void deleteData(String id) {
        progressDialog.show();
        db.collection("users").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(!task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Failed to Delete Data!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                getData();
            }
        });
    }
}