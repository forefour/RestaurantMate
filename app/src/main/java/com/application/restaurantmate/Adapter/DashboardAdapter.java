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
import com.application.restaurantmate.Model.OrderFood;
import com.application.restaurantmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Foremost on 31/8/2559.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {


    public static class DashboardViewHolder extends RecyclerView.ViewHolder {

        TextView foodOrderName;
        TextView status;
        Button changeStatusButton;

        DashboardViewHolder(View itemView) {
            super(itemView);
            foodOrderName = (TextView)itemView.findViewById(R.id.dashboard_order_food_name);
            status = (TextView)itemView.findViewById(R.id.dashboard_order_food_status);
            changeStatusButton = (Button)itemView.findViewById(R.id.dashboard_cooking_status);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public DashboardAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_order_list, viewGroup, false);
        DashboardViewHolder pvh = new DashboardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final DashboardViewHolder dashboardViewHolder, final int position ) {
        dashboardViewHolder.foodOrderName.setText(dataSnapshots.get(position).child("foodId").getValue().toString());
        dashboardViewHolder.status.setText(dataSnapshots.get(position).child("status").getValue().toString());
        if(!dataSnapshots.get(position).child("status").getValue().equals("SendToCooker"))
        {
            dashboardViewHolder.changeStatusButton.setVisibility(View.INVISIBLE);
        }
        //set status 2 places
        final DatabaseReference orderFoodStatus = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("OrderFoods");
        final DatabaseReference ordersRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Orders").child(dataSnapshots.get(position).child("orderID").getValue().toString());
        dashboardViewHolder.changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderFoodStatus.child(dataSnapshots.get(position).getKey()).child("status").setValue("Cooking");
                ordersRef.child("Foods").child(dataSnapshots.get(position).getKey()).child("status").setValue("Cooking");
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
