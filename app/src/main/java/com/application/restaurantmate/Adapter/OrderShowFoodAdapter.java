package com.application.restaurantmate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.restaurantmate.Activity.OrderActivity;
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
public class OrderShowFoodAdapter extends RecyclerView.Adapter<OrderShowFoodAdapter.OrderShowFoodViewHolder> {


    public static class OrderShowFoodViewHolder extends RecyclerView.ViewHolder {

        TextView food;
        Button selectFoodButton;


        OrderShowFoodViewHolder(View itemView) {
            super(itemView);
            food = (TextView)itemView.findViewById(R.id.order_show_food_name);
            selectFoodButton = (Button)itemView.findViewById(R.id.order_show_food_select);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public OrderShowFoodAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public OrderShowFoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_show_food_list, viewGroup, false);
        OrderShowFoodViewHolder pvh = new OrderShowFoodViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final OrderShowFoodViewHolder orderShowFoodViewHolder, final int position ) {
        orderShowFoodViewHolder.food.setText(dataSnapshots.get(position).child("Name").getValue().toString());
        //delete menu
        orderShowFoodViewHolder.selectFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    OrderActivity orderActivity = (OrderActivity)c;

                Log.d("KEY_foremostSF",orderActivity.getOrderId());
                //Log.d("KEY_foremostSF",orderActivity.getTableId());
                Log.d("KEY_foremostSF",dataSnapshots.get(position).getKey());
                DatabaseReference ordersRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Orders").child(orderActivity.getOrderId());
                ordersRef = ordersRef.child("Foods").push();
                ordersRef.child("FoodId").setValue(dataSnapshots.get(position).getKey());
                ordersRef.child("Status").setValue("w2order");



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
