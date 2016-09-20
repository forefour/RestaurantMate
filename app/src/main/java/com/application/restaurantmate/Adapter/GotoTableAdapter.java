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

import com.application.restaurantmate.Activity.CookerActivity;
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
public class GotoTableAdapter extends RecyclerView.Adapter<GotoTableAdapter.GotoTableViewHolder> {



    public static class GotoTableViewHolder extends RecyclerView.ViewHolder {

        TextView gotoTable;
        Button gotoTableButton;

        GotoTableViewHolder(View itemView) {
            super(itemView);
            gotoTable = (TextView)itemView.findViewById(R.id.goto_table_name);
            gotoTableButton = (Button)itemView.findViewById(R.id.goto_table_button);
        }
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context c;

    List<DataSnapshot> dataSnapshots = new ArrayList<>();

    public GotoTableAdapter(Context c, List<DataSnapshot> dataSnapshots){

        this.c = c;
        this.dataSnapshots = dataSnapshots;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GotoTableViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goto_table_list, viewGroup, false);
        GotoTableViewHolder pvh = new GotoTableViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final GotoTableViewHolder gotoTableViewHolder, final int position ) {
        gotoTableViewHolder.gotoTable.setText(dataSnapshots.get(position).child("Name").getValue().toString());
        //delete menu
        gotoTableViewHolder.gotoTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrderActivity.class);
                intent.putExtra("key_table", dataSnapshots.get(position).getKey());
                v.getContext().startActivity(intent);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                DatabaseReference orderRef = mDatabase.child("Restaurants").child(mAuth.getCurrentUser().getUid()).child("Orders");
                orderRef.push().child("Table").child(dataSnapshots.get(position).getKey()).setValue(true);
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
