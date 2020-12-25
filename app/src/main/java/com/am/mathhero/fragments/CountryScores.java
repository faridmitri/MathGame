package com.am.mathhero.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.mathhero.Adapter.RecyclerAdapterCountry;
import com.am.mathhero.Adapter.RecyclerAdapterUser;
import com.am.mathhero.Modal.Model;
import com.am.mathhero.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CountryScores extends Fragment {

    public static CountryScores newInstance()
    {
        return new CountryScores();
    }


    Query reference;
    RecyclerView recyclerView;
    ArrayList<Model> list;
    RecyclerAdapterCountry adapter;
    ProgressBar progressBarLeader;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_scores,container,false);


        progressBarLeader = view.findViewById(R.id.progressBarLeader);
        recyclerView = view.findViewById(R.id.recycler);
        //   recyclerView.setLayoutManager( new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);




        reference = FirebaseDatabase.getInstance().getReference("Users").orderByChild("countryScore") ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Model>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Model p = dataSnapshot1.getValue(Model.class);
                    list.add(p);
                }
                adapter = new RecyclerAdapterCountry(getActivity(),list);
                recyclerView.setAdapter(adapter);
                int r = RecyclerAdapterUser.getr();
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.smoothScrollToPosition(r);
                progressBarLeader.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                progressBarLeader.setVisibility(View.INVISIBLE);
            }
        });


        return view;
    }
}