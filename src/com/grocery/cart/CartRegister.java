package com.grocery.cart;

import com.grocery.GroceryStore;
import com.grocery.operations.Bill;

public class CartRegister
{
    private static GroceryStore STORE_INSTANCE;

    public static void setStoreInstance(GroceryStore store)
    {
        STORE_INSTANCE = store;
    }

    public static void addToCart(int buyerID, String itemName, int quantity)
    {
        STORE_INSTANCE.addToCart(buyerID, itemName, quantity);
    }

    public static Bill checkout(int buyerID)
    {
        return STORE_INSTANCE.checkout(buyerID);
    }
}
