package com.grocery.items;

import com.grocery.Printable;

import java.math.BigDecimal;

public class SoldItem extends Saleable implements Printable
{
    private static final int DISCOUNT_TYPE_ITEM = 0;
    private static final int DISCOUNT_TYPE_CATEGORY = 1;

    private int discountType;
    private String categoryName;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal discountedPrice;

    public SoldItem(String name)
    {
        super(name);
    }

    public void init(Item item, int qty)
    {
        this.categoryName = item.getCategoryName();
        this.quantity = qty;
        setPrice(item.getPrice());
        this.totalPrice = item.getPrice().multiply(new BigDecimal(qty));
        this.discountedPrice = item.applyDiscount(getTotalPrice(), item.getDiscountPercentage());
        setDiscountPercentage(null);
        if (item.getDiscountPercentage() != null )
        {
            this.discountType = DISCOUNT_TYPE_ITEM;
            setDiscountPercentage(item.getDiscountPercentage());
        }
        else if (item.getCategory().getDiscountPercentage() != null)
        {
            this.discountType = DISCOUNT_TYPE_CATEGORY;
            setDiscountPercentage(item.getCategory().getDiscountPercentage());
        }
    }

    public int getQuantity()
    {
        return quantity;
    }

    public BigDecimal getTotalPrice()
    {
        return totalPrice;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal amount, BigDecimal percentage)
    {
        throw new UnsupportedOperationException("You can't apply the discount after the sale.");
    }

    public int getDiscountType()
    {
        return discountType;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public BigDecimal getDiscountedPrice()
    {
        return discountedPrice;
    }

    public void setDiscountType(int discountType)
    {
        this.discountType = discountType;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice)
    {
        this.discountedPrice = discountedPrice;
    }

    @Override
    public void print()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append(" | ");
        sb.append(getCategoryName());
        sb.append(" | ");
        sb.append(getPrice());
        sb.append(" | ");
        sb.append(getQuantity());
        sb.append(" | ");
        sb.append(getTotalPrice());
        sb.append(" | ");
        if (getDiscountPercentage() != null)
        {
            sb.append(getDiscountPercentage());
        }
        else
        {
            sb.append(" nil ");
        }
        sb.append(" | ");
        sb.append(getDiscountedPrice());
        System.out.println(sb.toString());
    }
}
