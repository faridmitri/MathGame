package com.am.mathhero.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.mathhero.Activities.MainActivity;
import com.am.mathhero.Adapter.RecyclerAdapterUser;
import com.am.mathhero.Modal.Model;
import com.am.mathhero.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserScores extends Fragment {

    public static UserScores newInstance()
    {
        return new UserScores();
    }
int i,pos;

    Query reference;
    RecyclerView recyclerView;
    ArrayList<Model> list;
    RecyclerAdapterUser adapter;
   ProgressBar progressBarLeader;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_scores,container,false);


        progressBarLeader = view.findViewById(R.id.progressBarLeader);
        recyclerView = view.findViewById(R.id.recycler);

        //   recyclerView.setLayoutManager( new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);




        reference = FirebaseDatabase.getInstance().getReference("Users").orderByChild("score");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Model>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                     i = i + 1;
                     String ui = dataSnapshot1.getKey();
                     String uu = MainActivity.user();
                     if (ui.equals(uu) ) {
                         pos = i;
                     }



                     Model p = dataSnapshot1.getValue(Model.class);
                    list.add(p);
                }
                adapter = new RecyclerAdapterUser(getActivity(),list);
                recyclerView.setLayoutManager(layoutManager);
                // int size = adapter.getItemCount()+1;
              //   pos = size - pos;
                 recyclerView.scrollToPosition(pos);
                recyclerView.setAdapter(adapter);
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