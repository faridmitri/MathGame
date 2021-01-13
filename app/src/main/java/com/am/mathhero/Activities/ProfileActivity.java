package com.am.mathhero.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.mathhero.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;



import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {

    private CircleImageView imageViewCircleProfile;
    private EditText  editTextUserNameProfile;
    private Button buttonUpdate;
    private TextView countryfirebase;
    ProgressBar progressBar,progressBar3;
    String country;
   CountryCodePicker ccp;
    String countryCode = "";



    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    Uri imageUri;
    boolean imageControl = false;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    String image;
    ImageView flagi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
   //     World.init(getApplicationContext());
        imageViewCircleProfile = findViewById(R.id.imageProfile);
        buttonUpdate = findViewById(R.id.buttonSignupSign);
        editTextUserNameProfile = findViewById(R.id.editTextUsername);
        setTitle("Update Profile");

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar3 = findViewById(R.id.progressBar3);
        flagi = findViewById(R.id.flag);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        countryfirebase = findViewById(R.id.country);

        ccp = findViewById(R.id.ccp1);

        getUserInfo();



        imageViewCircleProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    public void updateProfile()
    {    progressBar.setVisibility(View.VISIBLE);
        String userName = editTextUserNameProfile.getText().toString();

        countryfirebase.setText(ccp.getSelectedCountryName());
        country =  countryfirebase.getText().toString();
        reference.child("Users").child(firebaseUser.getUid()).child("userName").setValue(userName);
        reference.child("Users").child(firebaseUser.getUid()).child("country").setValue(country);
        check();

        if(imageControl)
        {
            UUID randomID = UUID.randomUUID();
            final String imageName = "images/"+randomID+".jpg";
            storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference myStorageRef = firebaseStorage.getReference(imageName);
                    myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String filePath = uri.toString();
                            reference.child("Users").child(auth.getUid()).child("image").setValue(filePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileActivity.this, "Write to database is successful.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileActivity.this, "Write to database is not successful.", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    });
                }
            });
        }
        else
        {
            reference.child("Users").child(auth.getUid()).child("image").setValue(image);
        }
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        intent.putExtra("userName",userName);
        startActivity(intent);
        finish();
    }


    public void getUserInfo()
    {
        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("userName").getValue().toString();
                String getCountry = snapshot.child("country").getValue().toString();
                image = snapshot.child("image").getValue().toString();
                progressBar3.setVisibility(View.INVISIBLE);

                editTextUserNameProfile.setText(name);
                countryfirebase.setText(getCountry);
             //   final int flag = World.getFlagOf(getCountry);
             //   flagi.setImageResource(flag);

                getCountryCode(getCountry);
                String path = "https://www.countryflags.io/"+countryCode+"/shiny/64.png";
                Picasso.get().load(path).into(flagi);




                if (image.equals("null"))
                {
                    imageViewCircleProfile.setImageResource(R.drawable.profile);
                }
                else
                {
                    Picasso.get().load(image).into(imageViewCircleProfile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void imageChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageViewCircleProfile);
            imageControl =  true;
        }
        else
        {
            imageControl = false;
        }
    }



    public void check() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Countries");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(country)) {
                    // run some code
                } else {
                    reference.child("Countries").child(country).setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getCountryCode(String countryName) {

        // Get all country codes in a string array.
        String[] isoCountryCodes = Locale.getISOCountries();

        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            if(name.equals(countryName))
            {
                countryCode = code;
                break;
            }
        }
        return countryCode;
    }
}