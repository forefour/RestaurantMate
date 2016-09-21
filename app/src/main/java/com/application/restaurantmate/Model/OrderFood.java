package com.application.restaurantmate.Model;

/**
 * Created by foremost on 9/21/16.
 */

public class OrderFood {
    public String foodId;
    public String status;

    public OrderFood() {
    }

    public OrderFood(String foodId, String status) {
        this.foodId = foodId;
        this.status = status;
    }
}
