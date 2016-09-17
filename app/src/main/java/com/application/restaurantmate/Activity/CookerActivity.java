package com.application.restaurantmate.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CookerActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooker);
    }

    @Override
    public void onStart(){
        super.onStart();
        final EditText addMenuEditText = (EditText)findViewById(R.id.add_menu_editText);
        final Button addMenuButton = (Button)findViewById(R.id.add_menu_button);
        Log.d("Foremost","Foremost1");

        //add menu
        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addMenuEditText.getText().toString().equals("")){
                    DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Menus");
                    menuRef.push().child("Name").setValue(addMenuEditText.getText().toString());
                    Log.d("Foremost","Foremost2");
                }
            }
        });
    }
}
