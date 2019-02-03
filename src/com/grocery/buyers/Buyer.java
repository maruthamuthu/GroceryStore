package com.grocery.buyers;

import com.grocery.cart.CartRegister;
import com.grocery.cart.Cartable;
import com.grocery.operations.Bill;
import com.grocery.operations.Discountable;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Buyer implements Discountable, Cartable
{
    private static final AtomicInteger BUYER_ID_GENERATOR = new AtomicInteger(0);
    private final int id;
    private final String name;
    private BigDecimal discountPercentage;

    public Buyer(String name)
    {
        this.id = BUYER_ID_GENERATOR.incrementAndGet();
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void setDiscountPercentage(BigDecimal discountPercentage)
    {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal getDiscountPercentage()
    {
        return this.discountPercentage;
    }

    @Override
    public void addToCart(String itemName, int quantity)
    {
        CartRegister.addToCart(getId(), itemName, quantity);
    }

    @Override
    public Bill checkout()
    {
        return CartRegister.checkout(getId());
    }
}
