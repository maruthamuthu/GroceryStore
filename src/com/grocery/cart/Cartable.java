package com.grocery.cart;

import com.grocery.operations.Bill;

public interface Cartable
{
    default void addToCart(String itemName)
    {
        addToCart(itemName, 1);
    }

    public void addToCart(String itemName, int quantity);

    public Bill checkout();
}
