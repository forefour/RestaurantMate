package com.application.restaurantmate.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.application.restaurantmate.Fragment.OrderListAddFoodFragment;
import com.application.restaurantmate.Fragment.OrderShowCatFragment;
import com.application.restaurantmate.Fragment.OrderShowFoodFragment;
import com.application.restaurantmate.R;

public class OrderActivity extends AppCompatActivity {
    private OrderShowCatFragment orderShowCatFragment;
    private OrderShowFoodFragment orderShowFoodFragment;
    private OrderListAddFoodFragment orderListAddFoodFragment;

    private String tableId;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        tableId = intent.getStringExtra("key_table");
        orderId = intent.getStringExtra("key_order");
        Log.d("KEY_foremost",intent.getStringExtra("key_table"));
        Log.d("KEY_foremost",intent.getStringExtra("key_order"));

        //orderShowFoodFragment = (OrderShowFoodFragment)getSupportFragmentManager().findFragmentById(R.id.order_show_food_frag);
    }

    public String getTableId() {
        return tableId;
    }

    public String getOrderId() {
        return orderId;
    }

    //sent menu id to showfoodFragment
//    public void setFood(String key){
//        orderShowFoodFragment.clearList();
//        orderShowFoodFragment.addChangedRemovedMenuListener(key);
//    }
}
