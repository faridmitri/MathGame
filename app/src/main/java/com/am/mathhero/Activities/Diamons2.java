package com.am.mathhero.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.am.mathhero.R;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class Diamons2 extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    Button diamonds5;
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diamons2);
         bp = new BillingProcessor(this, NULL, this);

        diamonds5 = findViewById(R.id.diamonds5);
        diamonds5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(Diamons2.this, "diamond5");
                bp.consumePurchase("\n" +
                        "5diamonds");

            }
        });


    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast .makeText(this,"Success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast .makeText(this,"Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}