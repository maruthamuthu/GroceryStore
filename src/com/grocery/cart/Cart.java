package com.grocery.cart;

import com.grocery.buyers.Buyer;
import com.grocery.items.Item;
import com.grocery.operations.Bill;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart
{
    private Map<Item, Integer> cartItemsVsQuantity = new HashMap<>(10);

    public void addToCart(Item item, int quantity)
    {
        int oldQty =  cartItemsVsQuantity.getOrDefault(item, 0);
        if (item.getCurrentStock() <  (oldQty + quantity))
        {
            throw new UnsupportedOperationException("Stock not available. Please try again latter.");
        }

        cartItemsVsQuantity.merge(item, quantity, (oldQuantity, newQuantity) -> (oldQuantity + newQuantity));
    }

    public Bill checkout(Buyer buyer)
    {
        Bill bill = new Bill();
        bill.setBuyer(buyer);
        for (Map.Entry<Item, Integer> itemDetails : cartItemsVsQuantity.entrySet())
        {
            int qty = itemDetails.getValue();
            Item item = itemDetails.getKey();
            if (item.getCurrentStock() < qty)
            {
                throw new UnsupportedOperationException("Stock not available. Please try again latter.");
            }
            bill.addItem(item, qty);
            item.reduceCurrentStock(qty);
        }
        BigDecimal oldTotal = bill.getTotal();
        bill.setBuyerLevelSpecialDiscountPercentage(buyer.getDiscountPercentage());
        bill.setTotal(buyer.applyDiscount(oldTotal));
        bill.setBuyerLevelSpecialDiscountAmt(oldTotal.subtract(bill.getTotal()));
        return bill;
    }
}
