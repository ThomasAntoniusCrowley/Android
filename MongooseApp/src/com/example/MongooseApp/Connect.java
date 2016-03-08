package com.example.MongooseApp;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by joshua on 18/02/16.
 */
public class Connect
{
    private String host;
    private int port;
    private InetAddress inet;
    private Socket serverSocket;
    private Menu menu;

    public Connect(String host, int port)
    {
        this.host = host;
        this.port = port;
        try
        {
            this.inet = InetAddress.getByName(host);
            this.serverSocket = new Socket(this.inet, this.port);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Host name :  " + inet.getHostName());
        System.out.println("IP Address:  " + inet.getHostAddress());
        System.out.println("Connection established.");
    }

    public void Disconnect()
    {
        try //Disconnect from server
        {
            serverSocket.close();
            System.out.println("Connection closed.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void ReceiveMenu() //UNDER CONSTRUCTION
    {
        try //Receive serverside menu on start
        {
            ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream());
            menu = new Menu();
            menu = (Menu) ois.readObject();
            System.out.println("Acquired menu.");

//            for (int i = 0; i < fileList.length; i++)  Maybe append menu items to list?
//            {
//                InitialPanel.listModel.addElement(fileList[i].getName());
//            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method used for sending order details to server application.
     *
     * @param order Item array holding order data.
     */
    public void SendBill(Item[] order) //UNDER CONSTRUCTION
    {

        try //Informs server of incoming data and sends byte array containing image
        {
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            out.println("SENDING_BILL");

            //POPULATE BILL
            Bill bill;
            bill = OrderItems.createOrder(order);

            System.out.println("Ready for transfer.");
            ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
            oos.writeObject(order);
            System.out.println("Sent order data.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String host = "localhost";
        int port = 19999;
        Connect connection = new Connect(host, port);
//        connection.ReceiveMenu();
//        SendBill();
//        JFrame ui = new ClientUI(connection, fileList);
//        ui.setVisible(true);
    }
}
