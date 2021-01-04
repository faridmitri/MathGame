package com.am.mathhero.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.am.mathhero.Modal.Model;
import com.am.mathhero.R;
import com.blongho.country_data.World;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapterCountry extends RecyclerView.Adapter<RecyclerAdapterCountry.MyViewHolder> {



    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    Context context;
    ArrayList<String> models;
    int i = 0;
    public static int r;

    public RecyclerAdapterCountry(Context c, ArrayList<String> p) {
        context = c;
        models = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.country_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.country.setText("" +models.get(position));

      holder.country.setText(models.get(position));
        World.init(context.getApplicationContext());
        final int flag = World.getFlagOf(models.get(position));
        holder.flagi.setImageResource(flag);
        i += 1;
        holder.pos.setText(""+i);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        reference.child("Countries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String scores = snapshot.child("" +models.get(position)).getValue().toString();
                holder.score.setText(scores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView score,pos,country;
        ImageView flagi;

        // Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);

            country = (TextView) itemView.findViewById(R.id.cname);
            score = (TextView) itemView.findViewById(R.id.cscore);
            flagi = (ImageView) itemView.findViewById(R.id.cflag);

            pos = (TextView) itemView.findViewById(R.id.rank);

        }

    }
    public static int getr(){

        return r;
    }
}