package com.am.mathhero.Acttivities;



import android.app.ProgressDialog;
import android.os.Bundle;


import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.am.mathhero.Adapter.RecyclerAdapter;
import com.am.mathhero.Modal.Model;
import com.am.mathhero.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoardA extends AppCompatActivity {

    Query reference;
    RecyclerView recyclerView;
    ArrayList<Model> list;
    RecyclerAdapter adapter;
    ProgressDialog  progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
     //   recyclerView.setLayoutManager( new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);




        reference = FirebaseDatabase.getInstance().getReference("Users").orderByChild("score");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Model>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Model p = dataSnapshot1.getValue(Model.class);
                    list.add(p);
                }
                adapter = new RecyclerAdapter(LeaderBoardA.this,list);
                recyclerView.setAdapter(adapter);
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LeaderBoardA.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        int r = RecyclerAdapter.getr();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.smoothScrollToPosition(r);

    }
}