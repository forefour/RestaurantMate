package com.application.restaurantmate.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.restaurantmate.Activity.OrderActivity;
import com.application.restaurantmate.Fragment.OrderShowFoodFragment;
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
public class OrderAddFoodAdapter extends RecyclerView.Adapter<OrderAddFoodAdapter.OrderAddFoodViewHolder> {


    public static class OrderAddFoodViewHolder extends RecyclerView.ViewHolder {

        TextView food;
        TextView status;


        OrderAddFoodViewHolder(View itemView) {
            super(itemView);
            food = (TextView)itemView.findViewById(R.id.order_add_food_name);
            status = (TextView)itemView.findViewById(R.id.order_add_food_status);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public OrderAddFoodAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public OrderAddFoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_add_food_list, viewGroup, false);
        OrderAddFoodViewHolder pvh = new OrderAddFoodViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final OrderAddFoodViewHolder orderAddFoodViewHolder, final int position ) {
        //debug
        if(dataSnapshots.get(position).child("Status").getValue() != null){
            orderAddFoodViewHolder.status.setText(dataSnapshots.get(position).child("Status").getValue().toString());
        }
        else{
            orderAddFoodViewHolder.status.setText("");
            //Log.d("gg1",dataSnapshots.get(position).toString());
        }
        orderAddFoodViewHolder.food.setText(dataSnapshots.get(position).child("FoodId").getValue().toString());
        //Log.d("gg",dataSnapshots.get(position).toString());
        //delete menu


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
