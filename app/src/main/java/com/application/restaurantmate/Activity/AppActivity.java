package com.application.restaurantmate.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;

public class AppActivity extends AppCompatActivity {
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(AppActivity.this, "Signing out",
                        "Please wait..", true);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AppActivity.this, LoginActivity.class);
                startActivity(intent);
                progress.dismiss();
            }
        });

        Button cooker = (Button)findViewById(R.id.cooker_ativity);
        cooker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, CookerActivity.class);
                startActivity(intent);
            }
        });

        Button table = (Button)findViewById(R.id.table_activity);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });

        Button customer = (Button)findViewById(R.id.customer_activity);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });

        Button dashBoardOrder = (Button)findViewById(R.id.dashboard_order_activity);
        dashBoardOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, DashBoardOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
