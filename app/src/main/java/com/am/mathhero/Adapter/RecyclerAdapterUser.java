package com.am.mathhero.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.am.mathhero.Activities.MainActivity;
import com.am.mathhero.Modal.Model;
import com.am.mathhero.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;


public class RecyclerAdapterUser extends RecyclerView.Adapter<RecyclerAdapterUser.MyViewHolder> {


//    FirebaseDatabase database;
    //  DatabaseReference reference;
    //FirebaseAuth auth;
    //FirebaseUser firebaseUser;

    String countryCode = "";
    Context context;
    ArrayList<Model> models;
    int i = 0;
    public static int r;

    public RecyclerAdapterUser(Context c, ArrayList<Model> p) {
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
        String user = models.get(position).getuserName().trim();
        holder.score.setText("" + models.get(position).getScore());
        i = getItemCount() - position;
        holder.rank.setText("Rank: " + i);
        //holder.country.setText(models.get(position).getCountry());
        if (models.get(position).getimage().equals("null")) {
            holder.image.setImageResource(R.drawable.profile);
        } else {
            Picasso.get().load(models.get(position).getimage()).into(holder.image);
        }

      //  World.init(context.getApplicationContext());
        //final int flag = World.getFlagOf(models.get(position).getCountry());
        //holder.flagi.setImageResource(flag);
        getCountryCode(models.get(position).getCountry());
        String path = "https://www.countryflags.io/"+countryCode+"/shiny/64.png";
        Picasso.get().load(path).into(holder.flagi);




       /* String used = MainActivity.name().trim();

        if (user.equals(used) ) {


            r = position;
          getpos();
        }


      /*  database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String name = snapshot.child("userName").getValue().toString();
                if (name == models.get(position).getuserName()) {
                    r = i;
                    getr();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });  */


    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, score, rank;
        ImageView image, flagi;


        // Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.cname);
            score = (TextView) itemView.findViewById(R.id.cscore);
            flagi = (ImageView) itemView.findViewById(R.id.lflag);

            rank = (TextView) itemView.findViewById(R.id.rank);
            image = (ImageView) itemView.findViewById(R.id.cflag);


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

    /* public static int getpos(){

         return r;
     }*/
    public String getCountryCode(String countryName) {

        // Get all country codes in a string array.
        String[] isoCountryCodes = Locale.getISOCountries();

        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            if (name.equals(countryName)) {
                countryCode = code;
                break;
            }
        }
        return countryCode;
    }
}