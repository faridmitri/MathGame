package com.am.mathhero.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.mathhero.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
    private TextInputEditText editTextForget;
    private Button buttonForget;
    private ProgressBar progressBar;
    ConstraintLayout constraintLayout;


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Forget Password");

        editTextForget = findViewById(R.id.editTextForget);
        buttonForget = findViewById(R.id.buttonForget);
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        constraintLayout = findViewById(R.id.constraint);

        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextForget.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar snackbar = Snackbar.make(constraintLayout,"Check your email",Snackbar.LENGTH_INDEFINITE)
                                  .setAction("Close", new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                      }
                                  })
                                        ;
                                snackbar.show();
                            } else {
                                Snackbar snackbar = Snackbar.make(constraintLayout,"Error try later or contact us",Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Close", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                            }
                                        })
                                        ;
                                snackbar.show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }

        });


    }
}