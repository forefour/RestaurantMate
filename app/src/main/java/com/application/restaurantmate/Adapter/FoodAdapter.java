package com.application.restaurantmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.restaurantmate.Activity.FoodActivity;
import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Foremost on 31/8/2559.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {


    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView food;
        Button deleteMenuButton;


        FoodViewHolder(View itemView) {
            super(itemView);
            food = (TextView)itemView.findViewById(R.id.food_name);
            deleteMenuButton = (Button)itemView.findViewById(R.id.food_delete);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;
    String menuKey;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public FoodAdapter(Context c, List<DataSnapshot> dataSnapshots ,String menuKey){
        this.menuKey = menuKey;
        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_list, viewGroup, false);
        FoodViewHolder pvh = new FoodViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, final int position ) {
        foodViewHolder.food.setText(dataSnapshots.get(position).child("Name").getValue().toString());
        //delete menu
        foodViewHolder.deleteMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Menus").child(menuKey).child("Foods");
                menuRef.child(dataSnapshots.get(position).getKey()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {;
        return dataSnapshots.size();
    }




}
