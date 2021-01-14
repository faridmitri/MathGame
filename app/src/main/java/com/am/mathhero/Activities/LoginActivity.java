package com.am.mathhero.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.mathhero.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.hbb20.CountryCodePicker;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1001;
    GoogleSignInClient googleSignInClient;


    EditText mail;
    EditText password;
    Button signIn,signUp;
    TextView forgotPassword;
    ProgressBar progressBarSignin;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String address;

    CountryCodePicker ccp;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        mail = findViewById(R.id.editTextLoginEmail);
        password = findViewById(R.id.editTextLoginPassword);
        signIn = findViewById(R.id.buttonLoginSignin);

        signUp = findViewById(R.id.buttonSignUp);
        forgotPassword = findViewById(R.id.textViewLoginForgotPassword);
        progressBarSignin = findViewById(R.id.progressBarSignin);

        ccp=findViewById(R.id.ccp1);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email =  mail.getText().toString();
                String Password = password.getText().toString();

                if (!Email.equals("") && !Password.equals(""))
                {
                    signin(Email,Password);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please enter an email and password.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                address = ccp.getSelectedCountryName();
                if (address == null) {
                    address = "undetected";
                }
                intent.putExtra("EXTRA_country", address);
                startActivity(intent);

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                // Configure Google Client
                configureGoogleClient();
                // Launch Sign In
                signInToGoogle();
            }
        });


    }

    public void signin(String email,String password)
    {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Sign in is successful.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Sign in is not successful.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    private void configureGoogleClient() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
    }

    public void signInToGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(LoginActivity.this, "Google Sign in Succeeded ",Toast.LENGTH_SHORT).show();

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(LoginActivity.this, "Google Sign in Failed " + e,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            exist();

                            //   Log.d(TAG, "signInWithCredential:success: currentUser: " + user.getEmail());
                            Toast.makeText(LoginActivity.this, "Google authentication succeed",Toast.LENGTH_SHORT).show();
                            // launchMainActivity(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Firebase Authentication failed:" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void exist() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Users").child(uid);
        FirebaseUser user = auth.getCurrentUser();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //create new user
                    rootRef.child("Users").child(auth.getUid()).child("userName").setValue(user.getDisplayName());
                    address = ccp.getSelectedCountryName();
   /*                     TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                        address = tm.getSimCountryIso();
                       loc = new Locale("",address);
                    address = loc.getDisplayCountry();  */

                    if (address == null)
                    {rootRef.child("Users").child(auth.getUid()).child("country").setValue("undetected");}
                    else{ rootRef.child("Users").child(auth.getUid()).child("country").setValue(address);}
                    rootRef.child("Users").child(auth.getUid()).child("diamons").setValue(1);
                    rootRef.child("Users").child(auth.getUid()).child("score").setValue(0);
                    if (user != null &&  !user.equals("null"))
                    { rootRef.child("Users").child(auth.getUid()).child("image").setValue(user.getPhotoUrl().toString());

                    } else {
                        rootRef.child("Users").child(auth.getUid()).child("image").setValue("null");}
                    checkcountry();
                } else {checkcountry();}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(eventListener);
    }

    public void checkcountry() {
        address = ccp.getSelectedCountryName();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Countries/" + address);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // run some code
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                    finish();
                } else {
                    FirebaseDatabase.getInstance().getReference("Countries").child(address).setValue(0);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
