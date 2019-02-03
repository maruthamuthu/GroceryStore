package com.grocery.operations;

import java.math.BigDecimal;

public interface Discountable
{
    BigDecimal HUNDRED = new BigDecimal("100");

    void setDiscountPercentage(BigDecimal discountPercentage);

    BigDecimal getDiscountPercentage();

    // We can a pre-defined discount
    default BigDecimal applyDiscount(BigDecimal amount)
    {
        return applyDiscount(amount, getDiscountPercentage());
    }

    // We can apply the on demand or on the fly discounts
    default BigDecimal applyDiscount(BigDecimal amount, BigDecimal percentage)
    {
        if (percentage != null && amount.compareTo(BigDecimal.ZERO) != 0)
        {
            BigDecimal discountAmount = amount.multiply(percentage.divide(HUNDRED));
            return amount.subtract(discountAmount);
        }
        return amount;
    }
}
