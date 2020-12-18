package com.am.mathhero;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Map;

import static java.nio.file.Paths.get;

public class LeaderBoardActivity extends Activity {

    DatabaseReference reference;
   // FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    //recycleView
    private ArrayList<Model> mRecycler = new ArrayList<Model>();
    private RecyclerView recyclerView;
    public RecyclerAdapter mainAdapter;
    ProgressDialog progressDialog;
    String name = null,score= null,country=null;
    String mProfileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();


       reference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        recyclerView = findViewById(R.id.recyclerView1);
        mainAdapter = new RecyclerAdapter(mRecycler,getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mainAdapter);
        recyclerData();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

    }

    private void recyclerData(){
        mRecycler.clear();
        reference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               allListData(dataSnapshot);
                mainAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    @SuppressLint("NewApi")
    public void allListData(final DataSnapshot dataSnapshot){
       if(dataSnapshot.exists() ) {

            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
            if (map.get("userName") != null) {
                name = dataSnapshot.child("userName").getValue().toString();
            }
            if (map.get("score") != null) {
                score = map.get("score").toString();
             //   System.out.print("phone" + name);
            }

            if (map.get("country") != null) {
                country = map.get("country").toString();
                //   System.out.print("phone" + name);
            }

            if (map.get("image") != null){
                mProfileImageUrl = map.get("profileImageUrl").toString();
                //Glide.with(getApplication()).load(mProfileImageUrl).into(profileImage);
            }
       }

        progressDialog.dismiss();

        mRecycler.add(new Model(name,mProfileImageUrl,score,country));
   }



}