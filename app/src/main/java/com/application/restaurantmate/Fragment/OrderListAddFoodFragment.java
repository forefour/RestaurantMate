package com.application.restaurantmate.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.restaurantmate.Activity.OrderActivity;
import com.application.restaurantmate.Adapter.OrderAddFoodAdapter;
import com.application.restaurantmate.Adapter.OrderShowCatAdapter;
import com.application.restaurantmate.Adapter.OrderShowFoodAdapter;
import com.application.restaurantmate.Model.OrderFoodDashBoard;
import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListAddFoodFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private RecyclerView recyclerView ;
    private LinearLayoutManager linearLayoutManager;
    private OrderAddFoodAdapter adapter;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();


    public OrderListAddFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list_add_food, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        recyclerView = (RecyclerView)getView().findViewById(R.id.add_food_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new OrderAddFoodAdapter(getContext(), dataSnapshots);
        recyclerView.setAdapter(adapter);
        final OrderActivity orderActivity = (OrderActivity)getActivity();
        addChangedRemovedMenuListener(orderActivity.getOrderId());

        OrderActivity orderActivity1 = (OrderActivity)getActivity();
        Button finishOrderButton = (Button)getView().findViewById(R.id.finishOrderButton);
        Button nowOrderButton = (Button)getView().findViewById(R.id.nowOrderButton);
        finishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference orderFoodStatus = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("OrderFoods");
                DatabaseReference orderFef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Orders").child(orderActivity.getOrderId()).child("Foods");
                orderFef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {
                        for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                            snapshot.getRef().child("status").setValue("Finished");

                            orderFoodStatus.child(snapshot.getKey()).child("status").setValue("Finished");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        nowOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference orderFoodStatus = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("OrderFoods");
                DatabaseReference orderFef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Orders").child(orderActivity.getOrderId()).child("Foods");
                orderFef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {
                        for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                            snapshot.getRef().child("status").setValue("SendToCooker");


                            orderFoodStatus.child(snapshot.getKey()).child("status").setValue("SendToCooker");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void addChangedRemovedMenuListener(String orderId){
        DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Orders").child(orderId).child("Foods");
        menuRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("test_ChildListener","onChildAdded "+dataSnapshot.toString());
                dataSnapshots.add(dataSnapshot);
                adapter.notifyDataSetChanged();
                Log.d("gg","add= "+dataSnapshot.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("test_ChildListener","onChildChanged "+dataSnapshot.toString());
                for(Iterator<DataSnapshot> it = dataSnapshots.iterator(); it.hasNext();){
                    DataSnapshot data = it.next();
                    if(data.getKey().equals(dataSnapshot.getKey())){
                        dataSnapshots.set(dataSnapshots.indexOf(data),dataSnapshot);
                        adapter.notifyDataSetChanged();
                        Log.d("gg","changed= "+dataSnapshot.toString());
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
                        Log.d("gg","remove= "+dataSnapshot.toString());
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
