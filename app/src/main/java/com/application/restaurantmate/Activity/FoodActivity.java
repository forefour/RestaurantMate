package com.application.restaurantmate.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.restaurantmate.Adapter.FoodAdapter;
import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView ;
    private FoodAdapter adapter;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = (RecyclerView)findViewById(R.id.food_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new FoodAdapter(this, dataSnapshots,getIntent().getStringExtra("key_menu"));
        recyclerView.setAdapter(adapter);
        addChangedRemovedMenuListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String keyMenu = getIntent().getStringExtra("key_menu");
        Log.d("KEY_MENU => ",keyMenu);
        final DatabaseReference foodRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Menus").child(getIntent().getStringExtra("key_menu")).child("Foods");

        final EditText addFoodEditText = (EditText)findViewById(R.id.add_food_editText);
        final Button addFoodButton = (Button)findViewById(R.id.add_food_button);
        Log.d("Foremost","Foremost1");

        //add food
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addFoodEditText.getText().toString().equals("")){
                    foodRef.push().child("Name").setValue(addFoodEditText.getText().toString());
                    Log.d("Foremost","Foremost2");
                }
            }
        });

    }

    public void addChangedRemovedMenuListener(){
        final DatabaseReference foodRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Menus").child(getIntent().getStringExtra("key_menu")).child("Foods");
        foodRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("test_ChildListener","onChildAdded "+dataSnapshot.toString());
                dataSnapshots.add(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("test_ChildListener","onChildChanged "+dataSnapshot.toString());
                for(Iterator<DataSnapshot> it = dataSnapshots.iterator(); it.hasNext();){
                    DataSnapshot data = it.next();
                    if(data.getKey().equals(dataSnapshot.getKey())){
                        dataSnapshots.set(dataSnapshots.indexOf(data),dataSnapshot);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("test_ChildListener","onChildRemoved "+dataSnapshot.toString());
                for(Iterator<DataSnapshot> it = dataSnapshots.iterator(); it.hasNext();){
                    DataSnapshot data = it.next();
                    if(data.getKey().equals(dataSnapshot.getKey())){
                        it.remove();
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("test_ChildListener","onChildRemoved "+dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
