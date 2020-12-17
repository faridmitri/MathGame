package com.am.mathhero;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blongho.country_data.Country;
import com.blongho.country_data.World;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    EditText mail,password,username;
    Button signUp;
    ProgressBar progressBar;
    ImageView imageView,flagi;
    Uri imageUri;
    boolean imageControl = false;
    TextView countrytxt;
    Country country;

    String countryfirebase;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        World.init(getApplicationContext());

        mail = findViewById(R.id.editTextSignupMail);
        password = findViewById(R.id.editTextSignupPassword);
        signUp = findViewById(R.id.buttonSignupSign);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imageView = (ImageView) findViewById(R.id.imageViewCircle);
        username = findViewById(R.id.editTextUsername);
        flagi = findViewById(R.id.flag);
        countrytxt = findViewById(R.id.country);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
//country

        // use alpha2
    //    String countryname = getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry();
        String   countryname = getIntent().getStringExtra("EXTRA_country");
        final int flag = World.getFlagOf(countryname);
        flagi.setImageResource(flag);
        country = World.getCountryFrom(countryname);
        countryfirebase =  countryname;
        countrytxt.setText(countryfirebase);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp.setClickable(false);
                String userEmail = mail.getText().toString();
                String userPassword = password.getText().toString();
                String userName = username.getText().toString();


                if (!userEmail.equals("") && !userPassword.equals("") && !userName.equals(""))
                {
                    signUpFirebase(userEmail, userPassword,userName);
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,
                            "Complete all fields.",
                            Toast.LENGTH_LONG).show();
                }
                signUp.setClickable(true);
            }
        });
    }



    public void signUpFirebase(String userEmail, String userPassword,String userName)
    {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            reference.child("Users").child(auth.getUid()).child("userName").setValue(userName);
                          //  reference.child("Users").child(auth.getUid()).child("country").setValue(countryfirebase);
                            reference.child("country").child(auth.getUid()).setValue(countryfirebase);

                            if(imageControl) {
                                UUID randomID = UUID.randomUUID();
                                final String imageName = "images/" + randomID + ".jpg";
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
                                                        Toast.makeText(SignUpActivity.this, "Write to database is successful.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SignUpActivity.this, "Write to database is not successful.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                             else
                                {
                                    reference.child("Users").child(auth.getUid()).child("image").setValue("null");
                                }

                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        else
                        {
                            Toast.makeText(SignUpActivity.this,
                                    "There is a problem! Please try again later.",
                                    Toast.LENGTH_LONG).show();
                        }

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
            Picasso.get().load(imageUri).into(imageView);
            imageControl =  true;
        }
        else
        {
            imageControl = false;
        }
    }
}