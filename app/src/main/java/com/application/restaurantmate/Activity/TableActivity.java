package com.application.restaurantmate.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.restaurantmate.Adapter.MenuAdapter;
import com.application.restaurantmate.Adapter.TableAdapter;
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

public class TableActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private RecyclerView recyclerView ;
    private LinearLayoutManager linearLayoutManager;
    private TableAdapter adapter;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        recyclerView = (RecyclerView)findViewById(R.id.table_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new TableAdapter(this, dataSnapshots);
        recyclerView.setAdapter(adapter);
        addChangedRemovedTableListener();
    }

    @Override
    public void onStart(){
        super.onStart();

        final EditText addTableEditText = (EditText)findViewById(R.id.add_table_editText);
        final Button addTableButton = (Button)findViewById(R.id.add_table_button);

        //add menu
        addTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addTableEditText.getText().toString().equals("")){
                    DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Tables");
                    menuRef.push().child("Name").setValue(addTableEditText.getText().toString());
                }
            }
        });


    }

    //Listener at table child
    public void addChangedRemovedTableListener(){
        DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Tables");
        menuRef.addChildEventListener(new ChildEventListener() {
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
