package com.grocery.items;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Item extends Saleable
{
    private final Category category;
    private AtomicInteger openingStock = new AtomicInteger(0);
    private AtomicInteger currentStock = new AtomicInteger(0);

    public Item(String name, Category category)
    {
        super(name);
        this.category = category;
    }

    public int getOpeningStock()
    {
        return openingStock.get();
    }

    public int getCurrentStock()
    {
        return this.currentStock.get();
    }

    @Override
    public BigDecimal getDiscountPercentage()
    {
        if (super.getDiscountPercentage() != null)
        {
            return super.getDiscountPercentage();
        }
        else if (category.getDiscountPercentage() != null)
        {
            return category.getDiscountPercentage();
        }
        return null;
    }

    public Category getCategory()
    {
        return category;
    }

    public String getCategoryName()
    {
        return category.getCategoryName();
    }

    public void addStock(int stock)
    {
        this.openingStock.addAndGet(stock);
        this.currentStock.addAndGet(stock);
    }

    public void reduceCurrentStock(int quantity)
    {
        this.currentStock.addAndGet(-1 * quantity);
    }

    public void printSummary()
    {
        System.out.println(getName() + " | " + this.category.getCategoryName() + " | " + this.getOpeningStock() + " | " + this.getCurrentStock());
    }
}
