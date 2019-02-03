package com.grocery.items;

import com.grocery.operations.Discountable;

import java.math.BigDecimal;

public class Saleable implements Discountable
{
    private final String name;
    private BigDecimal price;
    private BigDecimal discountPercentage;

    public Saleable(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
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
}
