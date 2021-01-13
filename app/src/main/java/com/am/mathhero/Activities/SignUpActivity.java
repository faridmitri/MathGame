package com.am.mathhero.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    EditText mail, password, username;
    Button signUp;
    ProgressBar progressBar;
    ImageView imageView, flagi;
    Uri imageUri;
    boolean imageControl = false;
    TextView countrytxt;

    String countryCode = "";
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Sign Up");


        mail = findViewById(R.id.editTextSignupMail);
        password = findViewById(R.id.editTextSignupPassword);
        signUp = findViewById(R.id.buttonSignupSign);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imageView = (ImageView) findViewById(R.id.imageProfile);
        username = findViewById(R.id.editTextUsername);
        flagi = findViewById(R.id.flag);
        countrytxt = findViewById(R.id.country);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
//country


        String countryname = getIntent().getStringExtra("EXTRA_country");
       // final int flag = World.getFlagOf(countryname);
        //flagi.setImageResource(flag);
        //country = World.getCountryFrom(countryname);
        getCountryCode(countryname);
        String path = "https://www.countryflags.io/"+countryCode+"/shiny/64.png";
        Picasso.get().load(path).into(flagi);



        countryfirebase = countryname;
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


                if (!userEmail.equals("") && !userPassword.equals("") && !userName.equals("")) {
                    signUpFirebase(userEmail, userPassword, userName);
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "Complete all fields.",
                            Toast.LENGTH_LONG).show();
                }
                signUp.setClickable(true);
            }
        });
    }


    public void signUpFirebase(String userEmail, String userPassword, String userName) {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                         reference.child("Users").child(auth.getUid()).child("userName").setValue(userName);
                           reference.child("Users").child(auth.getUid()).child("country").setValue(countryfirebase);
                          // reference.child("Users").child(auth.getUid()).child("countryScore").setValue(0);
                           reference.child("Users").child(auth.getUid()).child("diamons").setValue(1);
                           reference.child("Users").child(auth.getUid()).child("score").setValue(0);



                            if (imageControl) {
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
                                                        reference.child("Users").child(auth.getUid()).child("image").setValue("null");
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                progressBar.setVisibility(View.INVISIBLE);
                            } else {
                                reference.child("Users").child(auth.getUid()).child("image").setValue("null");
                            }
                            checkchild();

                        } else {
                            Toast.makeText(SignUpActivity.this,
                                    "There is a problem! Please try again later.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


    public void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
            imageControl = true;
        } else {
            imageControl = false;
        }
    }

    public void checkchild() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Countries");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(countryfirebase)) {
                    // run some code
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    rootRef.child(countryfirebase).setValue(0);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
            if (name.equals(countryName)) {
                countryCode = code;
                break;
            }
        }
        return countryCode;
    }
}