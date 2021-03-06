package com.application.restaurantmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.restaurantmate.Activity.AppActivity;
import com.application.restaurantmate.Activity.FoodActivity;
import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Foremost on 31/8/2559.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView menu;
        Button deleteMenuButton;
        Button goFoodButton;

        MenuViewHolder(View itemView) {
            super(itemView);
            menu = (TextView)itemView.findViewById(R.id.menu_name);
            deleteMenuButton = (Button)itemView.findViewById(R.id.menu_delete);
            goFoodButton = (Button)itemView.findViewById(R.id.menu_foods);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public MenuAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_list, viewGroup, false);
        MenuViewHolder pvh = new MenuViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder menuViewHolder, final int position ) {
        menuViewHolder.menu.setText(dataSnapshots.get(position).child("Name").getValue().toString());
        //delete menu
        menuViewHolder.deleteMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Menus");
                menuRef.child(dataSnapshots.get(position).getKey()).removeValue();
            }
        });
        //go to foods
        menuViewHolder.goFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("key menu Go FOOD => ",dataSnapshots.get(position).getKey());
                Intent intent = new Intent(v.getContext(), FoodActivity.class);
                intent.putExtra("key_menu", dataSnapshots.get(position).getKey());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {;
        return dataSnapshots.size();
    }

    public void clear(){
        dataSnapshots.clear();
        notifyDataSetChanged();
    }

    public void remove(int positon){
        dataSnapshots.remove(positon);
        notifyDataSetChanged();
    }


}
