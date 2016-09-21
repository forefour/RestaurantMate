package com.application.restaurantmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.application.restaurantmate.Activity.FoodActivity;
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
public class OrderShowCatAdapter extends RecyclerView.Adapter<OrderShowCatAdapter.OrderShowCatViewHolder> {


    public static class OrderShowCatViewHolder extends RecyclerView.ViewHolder {

        TextView menu;
        Button selectMenuButton;


        OrderShowCatViewHolder(View itemView) {
            super(itemView);
            menu = (TextView)itemView.findViewById(R.id.order_show_cat_menu_name);
            selectMenuButton = (Button)itemView.findViewById(R.id.order_show_cat_select);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public OrderShowCatAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public OrderShowCatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_show_cat_list, viewGroup, false);
        OrderShowCatViewHolder pvh = new OrderShowCatViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final OrderShowCatViewHolder orderShowCatViewHolder, final int position ) {
        orderShowCatViewHolder.menu.setText(dataSnapshots.get(position).child("Name").getValue().toString());
        //delete menu
        orderShowCatViewHolder.selectMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OrderActivity orderActivity = (OrderActivity)c;
                //sent catkey and setfood
                //orderActivity.setFood(dataSnapshots.get(position).getKey().toString());

                OrderShowFoodFragment orderShowFoodFragment = new OrderShowFoodFragment();
                orderShowFoodFragment.setCatKey(dataSnapshots.get(position).getKey().toString());

                OrderActivity orderActivity = (OrderActivity)c;
                FragmentTransaction ft = orderActivity.getSupportFragmentManager().beginTransaction();
                //orderShowFoodFragment.addChangedRemovedMenuListener(dataSnapshots.get(position).getKey().toString());
                ft.replace(R.id.fragment_container, orderShowFoodFragment);
                //ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                Log.d("Fore-Dubug","menu id+ "+dataSnapshots.get(position).toString());
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
