package com.grocery.items;

import com.grocery.operations.Discountable;

import java.math.BigDecimal;

public class Category implements Discountable
{
    private final String categoryName;
    private BigDecimal discountPercentage;

    public Category(String category)
    {
        this.categoryName = category;
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

    public String getCategoryName()
    {
        return categoryName;
    }

}
