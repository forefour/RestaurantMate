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
            }
        });
    }
}
