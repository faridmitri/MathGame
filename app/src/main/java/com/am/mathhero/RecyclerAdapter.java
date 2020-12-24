package com.am.mathhero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.blongho.country_data.World;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Model> models;
    int i = 0;

    public RecyclerAdapter(Context c, ArrayList<Model> p) {
        context = c;
        models = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.leader, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userName.setText(models.get(position).getuserName());
        holder.score.setText("" +models.get(position).getScore());
        //holder.country.setText(models.get(position).getCountry());
        if (models.get(position).getimage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.profile);
        }
        else
        {
            Picasso.get().load(models.get(position).getimage()).into(holder.image);
        }

        World.init(context.getApplicationContext());
       final int flag = World.getFlagOf(models.get(position).getCountry());
        holder.flagi.setImageResource(flag);
        i += 1;
        holder.rank.setText("Rank: "+i);


    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName,score,rank;
        ImageView image,flagi;

        // Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.lname);
            score = (TextView) itemView.findViewById(R.id.lscore);
            flagi = (ImageView) itemView.findViewById(R.id.lflag);

            rank = (TextView) itemView.findViewById(R.id.rank);
            image = (ImageView) itemView.findViewById(R.id.lmage_profile);
            //     btn = (Button) itemView.findViewById(R.id.checkDetails);
        }
       /* public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }  */
    }
}