package com.am.mathhero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    public List<Model> mainRecycle_lists;
    private LinearLayout linearLayout;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uName, uscore;
        ImageView uprofile;


        public MyViewHolder(View view) {
            super(view);
            uName = (TextView) view.findViewById(R.id.lname);
            uscore  = view.findViewById(R.id.lscore);
            uprofile= view.findViewById(R.id.lmage_profile);
            linearLayout = view.findViewById(R.id.uLineare);

        }
    }
    public RecyclerAdapter(List<Model> mainRecycle_lists, Context context) {
        this.mainRecycle_lists = mainRecycle_lists;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leader, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Model recycleList = mainRecycle_lists.get(position);
        holder.uName.setText(recycleList.getName());
        holder.uscore.setText(recycleList.getScore());

        Glide.with(context).load(recycleList.getProfile()).into(holder.uprofile);


       /* linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_Recycle_list recycleList = mainRecycle_lists.get(position);

                String name = recycleList.getName();
                Toast.makeText(context,"User Name : "+name,Toast.LENGTH_SHORT).show();
            }

        });*/

    }
    @Override
    public int getItemCount() {
        return mainRecycle_lists.size();
    }

}