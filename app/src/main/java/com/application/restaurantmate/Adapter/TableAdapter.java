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
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {



    public static class TableViewHolder extends RecyclerView.ViewHolder {

        TextView table;
        Button deleteTableButton;

        TableViewHolder(View itemView) {
            super(itemView);
            table = (TextView)itemView.findViewById(R.id.table_name);
            deleteTableButton = (Button)itemView.findViewById(R.id.table_delete);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public TableAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_list, viewGroup, false);
        TableViewHolder pvh = new TableViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final TableViewHolder tableViewHolder, final int position ) {
        tableViewHolder.table.setText(dataSnapshots.get(position).child("Name").getValue().toString());
        //delete menu
        tableViewHolder.deleteTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference menuRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Tables");
                menuRef.child(dataSnapshots.get(position).getKey()).removeValue();
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
