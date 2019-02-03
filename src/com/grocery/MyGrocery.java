package com.grocery;

import com.grocery.buyers.Buyer;
import com.grocery.operations.Bill;

public class MyGrocery
{
    public static void main(String[] args)
    {
        GroceryStore store = new GroceryStore("My Store");
        store.init();
        Buyer buyer = store.getBuyer(1);
        buyer.addToCart("Avin", 3);
        buyer.addToCart("Red Bull", 2);
        buyer.addToCart("Dairy Milk");
        Bill bill = buyer.checkout();
        bill.print();

        Buyer buyer2 = store.getBuyer(4);
        buyer2.addToCart("KitKat", 3);
        buyer2.addToCart("7 UP", 1);
        buyer2.addToCart("Thirumala");
        Bill bill2 = buyer2.checkout();
        bill2.print();

        store.printStockInfo();
        store.printSalesInfo();
    }

/*
SAMPLE OUTPUT:

**********************************************************
Bill No: 1
Date and Time: 03-42-2019 23:02
Customer Name: Varun
Customer ID: 1
Items:
-----------------------------------------------------------
Item Name | Category | Price | Qty | Sub Total | Dis.% | Total
-----------------------------------------------------------
Avin | Milk | 20 | 3 | 60 | 10 | 54.0
Red Bull | Driks | 99 | 2 | 198 | 5 | 188.10
Dairy Milk | Chocalate | 25 | 1 | 25 | 10 | 22.5
-----------------------------------------------------------
Sub Total                 - 283
Total Item Discount       - 18.40
Special Discount (10%)    - 26.460
Total                     - 238.140
**********************************************************

**********************************************************
Bill No: 2
Date and Time: 03-42-2019 23:02
Customer Name: Vasanth
Customer ID: 4
Items:
-----------------------------------------------------------
Item Name | Category | Price | Qty | Sub Total | Dis.% | Total
-----------------------------------------------------------
KitKat | Chocalate | 10 | 3 | 30 | 15 | 25.50
Thirumala | Milk | 20 | 1 | 20 | 5 | 19.00
7 UP | Driks | 30 | 1 | 30 |  nil  | 30
-----------------------------------------------------------
Sub Total                 - 80
Total Item Discount       - 5.50
Special Discount (25%)    - 18.6250
Total                     - 55.8750
**********************************************************

***********************************************************************
*                         Item Stock Report                           *
***********************************************************************
Name | Category Name | Opening Stock | Current Stock
-----------------------------------------------------------------------
KitKat | Chocalate | 40 | 37
Avin | Milk | 60 | 57
Thirumala | Milk | 60 | 59
Dairy Milk | Chocalate | 50 | 49
7 UP | Driks | 25 | 24
Red Bull | Driks | 30 | 28
-----------------------------------------------------------------------

***********************************************************************
*                           Sale Report                               *
***********************************************************************
Date | Bill ID | Buyer ID | Buyer Name | Total
-----------------------------------------------------------------------
03-42-2019 23:02 | 1 | 1 | Varun | 238.140
03-42-2019 23:02 | 2 | 4 | Vasanth | 55.8750
-----------------------------------------------------------------------

*/

}
