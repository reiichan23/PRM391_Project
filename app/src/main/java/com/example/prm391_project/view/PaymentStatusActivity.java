package com.example.prm391_project.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm391_project.R;

public class PaymentStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        TextView statusTxt = findViewById(R.id.paymentStatusTxt);


        // Get the status and detail from the intent
        String status = getIntent().getStringExtra("status");


        statusTxt.setText(status);

    }

    public void closeActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}