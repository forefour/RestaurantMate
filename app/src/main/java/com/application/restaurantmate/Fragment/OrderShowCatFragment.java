package com.application.restaurantmate.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.restaurantmate.Adapter.MenuAdapter;
import com.application.restaurantmate.Adapter.OrderShowCatAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderShowCatFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private RecyclerView recyclerView ;
    private LinearLayoutManager linearLayoutManager;
    private OrderShowCatAdapter adapter;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public OrderShowCatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_show_cat, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        recyclerView = (RecyclerView)getView().findViewById(R.id.show_cat_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new OrderShowCatAdapter(getContext(), dataSnapshots);
        recyclerView.setAdapter(adapter);
        addChangedRemovedMenuListener();
    }

    public void addChangedRemovedMenuListener(){
        DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Menus");
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
