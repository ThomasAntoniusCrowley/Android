package com.example.MongooseApp;

/**
 * Created by joshua on 23/02/16.
 */
public class OrderItems
{
    /**
     * Invoke on client GUI when making an order. Will return bill object to be sent to server application.
     *
     * @param order
     * @return
     */
    public static Bill createOrder(Item[] order)
    {
        int orderSize = order.length;
        Bill bill = new Bill(orderSize);

        for (int i = 0; i < orderSize; i++)
        {
            bill.addItem(order[i]);
        }

        return bill;
    }
}
