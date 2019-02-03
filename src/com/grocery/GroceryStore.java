package com.grocery;

import com.csvreader.CsvReader;
import com.grocery.buyers.Buyer;
import com.grocery.buyers.Customer;
import com.grocery.buyers.Employee;
import com.grocery.cart.Cart;
import com.grocery.cart.CartRegister;
import com.grocery.items.Category;
import com.grocery.items.Item;
import com.grocery.operations.Bill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GroceryStore
{
    private final String storeName;
    private HashMap<String, Item> itemsNameVsItem = new HashMap<>(10);
    private HashMap<Integer, Buyer> idVsBuyers = new HashMap<>(10);
    private HashMap<String, Category> categoryNameVsCategory = new HashMap<>(10);
    private List<Bill> salesList = new ArrayList<>(10);

    private final ConcurrentHashMap<Integer, Cart> BUYER_ID_VS_CART = new ConcurrentHashMap<>();

    public GroceryStore(String name)
    {
        this.storeName = name;
    }

    public String getName()
    {
        return this.storeName;
    }

    public Category getCategory(String categoryName)
    {
        return categoryNameVsCategory.get(categoryName);
    }

    public Buyer getBuyer(int id)
    {
        return idVsBuyers.get(id);
    }

    public Item getItem(String itemName)
    {
        return itemsNameVsItem.get(itemName);
    }

    public void init()
    {
        try
        {
            CsvReader reader = new CsvReader("./resources/category.csv");
            reader.readHeaders();
            while (reader.readRecord())
            {
                Category category = new Category(reader.get("Category Name"));
                String discount = reader.get("Discount");
                if (discount != null && !discount.isEmpty())
                {
                    category.setDiscountPercentage(new BigDecimal(discount));
                }
                categoryNameVsCategory.put(category.getCategoryName(), category);
            }

            reader = new CsvReader("./resources/items.csv");
            reader.readHeaders();
            while (reader.readRecord())
            {
                String itemName = reader.get("Item Name");
                String categoryName = reader.get("Category");
                Item item = new Item(itemName, getCategory(categoryName));
                String openningStock = reader.get("Openning Stock");
                item.addStock(Integer.parseInt(openningStock));
                item.setPrice(new BigDecimal(reader.get("Price")));
                String discount = reader.get("Discount");
                if (discount != null && !discount.isEmpty())
                {
                    item.setDiscountPercentage(new BigDecimal(discount));
                }
                itemsNameVsItem.put(item.getName(), item);
            }

            reader = new CsvReader("./resources/buyers.csv");
            reader.readHeaders();
            while (reader.readRecord())
            {
                String name = reader.get("Name");
                String type = reader.get("Type");
                Buyer buyer;
                if ("Customer".equals(type))
                {
                    buyer = new Customer(name);
                }
                else if ("Employee".equals(type))
                {
                    buyer = new Employee(name);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid Buyer Type");
                }
                String discount = reader.get("Discount");
                if (discount != null && !discount.isEmpty())
                {
                    buyer.setDiscountPercentage(new BigDecimal(discount));
                }
                idVsBuyers.put(buyer.getId(), buyer);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        CartRegister.setStoreInstance(this);
    }

    public void addToCart(int buyerID, String itemName, int quantity)
    {
        Cart cart = BUYER_ID_VS_CART.get(buyerID);
        if (cart == null)
        {
            cart = new Cart();
            BUYER_ID_VS_CART.put(buyerID, cart);
        }
        cart.addToCart(getItem(itemName), quantity);
    }

    public Bill checkout(int buyerID)
    {
        Cart cart = BUYER_ID_VS_CART.get(buyerID);
        if (cart == null)
        {
            throw new UnsupportedOperationException("Your cart is empty. Please add some items to checkout.");
        }
        Bill bill = cart.checkout(getBuyer(buyerID));
        cart = null;
        BUYER_ID_VS_CART.remove(buyerID);
        salesList.add(bill);
        return bill;
    }

    public void printStockInfo()
    {
        System.out.println("***********************************************************************");
        System.out.println("*                         Item Stock Report                           *");
        System.out.println("***********************************************************************");
        System.out.println("Name | Category Name | Opening Stock | Current Stock");
        System.out.println("-----------------------------------------------------------------------");
        itemsNameVsItem.values().forEach(Item::printSummary);
        System.out.println("-----------------------------------------------------------------------\n");
    }

    public void printSalesInfo()
    {
        System.out.println("***********************************************************************");
        System.out.println("*                           Sale Report                               *");
        System.out.println("***********************************************************************");
        StringBuilder sb = new StringBuilder(100);
        sb.append("Date");
        sb.append(" | ");
        sb.append("Bill ID");
        sb.append(" | ");
        sb.append("Buyer ID");
        sb.append(" | ");
        sb.append("Buyer Name");
        sb.append(" | ");
        sb.append("Total");
        System.out.println(sb.toString());
        System.out.println("-----------------------------------------------------------------------");
        salesList.forEach(Bill::printOverview);
        System.out.println("-----------------------------------------------------------------------\n");
    }
}
