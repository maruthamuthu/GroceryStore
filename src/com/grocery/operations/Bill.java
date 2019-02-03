package com.grocery.operations;

import com.grocery.Printable;
import com.grocery.buyers.Buyer;
import com.grocery.items.Item;
import com.grocery.items.SoldItem;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Bill implements Printable
{
    private static final AtomicInteger BILL_NUMBER_GENERATOR = new AtomicInteger(0);
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-mm-yyy HH:MM");

    private int billID;
    private Buyer buyer;
    private long dateAndTime;
    private List<SoldItem> items = new ArrayList<>();
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal totalItemLevelDiscountAmt = BigDecimal.ZERO;
    private BigDecimal buyerLevelSpecialDiscountPercentage = null;
    private BigDecimal buyerLevelSpecialDiscountAmt = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public Bill()
    {
        this.billID = BILL_NUMBER_GENERATOR.incrementAndGet();
        this.dateAndTime = System.currentTimeMillis();
    }

    public int getBillID()
    {
        return billID;
    }

    public Buyer getBuyer()
    {
        return buyer;
    }

    public List<SoldItem> getItems()
    {
        return items;
    }

    public BigDecimal getSubTotal()
    {
        return subTotal;
    }

    public BigDecimal getTotalItemLevelDiscountAmt()
    {
        return totalItemLevelDiscountAmt;
    }

    public BigDecimal getBuyerLevelSpecialDiscountAmt()
    {
        return buyerLevelSpecialDiscountAmt;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setBuyer(Buyer buyer)
    {
        this.buyer = buyer;
    }

    public void setSubTotal(BigDecimal subTotal)
    {
        this.subTotal = subTotal;
    }

    public void setTotalItemLevelDiscountAmt(BigDecimal totalItemLevelDiscountAmt)
    {
        this.totalItemLevelDiscountAmt = totalItemLevelDiscountAmt;
    }

    public void setBuyerLevelSpecialDiscountAmt(BigDecimal buyerLevelSpecialDiscountAmt)
    {
        this.buyerLevelSpecialDiscountAmt = buyerLevelSpecialDiscountAmt;
    }

    public BigDecimal getBuyerLevelSpecialDiscountPercentage()
    {
        return buyerLevelSpecialDiscountPercentage;
    }

    public void setBuyerLevelSpecialDiscountPercentage(BigDecimal buyerLevelSpecialDiscountPercentage)
    {
        this.buyerLevelSpecialDiscountPercentage = buyerLevelSpecialDiscountPercentage;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public long getDateAndTime()
    {
        return dateAndTime;
    }

    public void addItem(Item item, int qty)
    {
        SoldItem soldItem = new SoldItem(item.getName());
        soldItem.init(item, qty);
        this.subTotal = this.subTotal.add(soldItem.getTotalPrice());
        this.totalItemLevelDiscountAmt = this.totalItemLevelDiscountAmt.add(soldItem.getTotalPrice().subtract(soldItem.getDiscountedPrice()));
        this.total = this.total.add(soldItem.getDiscountedPrice());
        this.items.add(soldItem);
    }

    @Override
    public void print()
    {
        StringBuilder sb = new StringBuilder(100);
        sb.append("**********************************************************\n");
        sb.append("Bill No: ").append(getBillID()).append('\n');
        sb.append("Date and Time: ").append(DATE_FORMATTER.format(getDateAndTime())).append('\n');
        sb.append("Customer Name: ").append(buyer.getName()).append('\n');
        sb.append("Customer ID: ").append(buyer.getId()).append('\n');
        sb.append("Items: ").append('\n');
        sb.append("-----------------------------------------------------------\n");
        sb.append("Item Name | Category | Price | Qty | Sub Total | Dis.% | Total\n");
        sb.append("-----------------------------------------------------------");
        System.out.println(sb.toString());
        for (SoldItem soldItem : items)
        {
            soldItem.print();
        }
        sb = new StringBuilder(100);
        sb.append("-----------------------------------------------------------\n");
        sb.append("Sub Total                 - ").append(getSubTotal()).append('\n');
        sb.append("Total Item Discount       - ").append(getTotalItemLevelDiscountAmt()).append('\n');
        if (getBuyerLevelSpecialDiscountPercentage() != null)
        {
        sb.append("Special Discount (").append(getBuyerLevelSpecialDiscountPercentage()).append("%)    - ").append(getBuyerLevelSpecialDiscountAmt()).append('\n');
        }
        sb.append("Total                     - ").append(getTotal()).append('\n');
        sb.append("**********************************************************\n");
        System.out.println(sb.toString());
    }

    public void printOverview()
    {
        StringBuilder sb = new StringBuilder(100);
        sb.append(DATE_FORMATTER.format(getDateAndTime()));
        sb.append(" | ");
        sb.append(getBillID());
        sb.append(" | ");
        sb.append(buyer.getId());
        sb.append(" | ");
        sb.append(buyer.getName());
        sb.append(" | ");
        sb.append(getTotal());
        System.out.println(sb.toString());
    }
}
