package com.example.MongooseApp;

import android.app.ActionBar;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Created by joshua on 18/02/16.
 */
public class Client
{
    private static String host;
    private static int port;
    private static InetAddress inet;
    private static Socket serverSocket;
    private com.example.MongooseApp.Menu menu;

    public Client(String host, int port)
    {
        this.host = host; //Initialise connection parameters
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

    /**
     * Runs on connect to server and pulls most recent menu as 2D array.
     */
    public static void ReceiveMenu() //UNDER CONSTRUCTION
    {
        try {
            String[][] menu;
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            int int1 = in.read(); //Get array dimensions
            int int2 = in.read();

            menu = new String[int1][int2];

            for (int i = 0; i < menu.length; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    menu[i][j] = in.readLine(); //Read in menu contents
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method used for sending order details to server application.
     *
     * @param items Item array holding order data.
     * @param tableNo Integer holding table number to associate order with any open orders
     */
    public void SendBill(String[] items, int tableNo) //UNDER CONSTRUCTION
    {

        try //Informs server of incoming data and sends byte array containing image
        {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            out.write("SENDING_BILL");
            System.out.println("Order size: " + items.length);
            out.write(tableNo);
            out.write(items.length);

            for (int i = 0; i < items.length; i++)
            {
                out.write(items[i]);
            }

            out.flush();
            out.close();

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
        Client connection = new Client(host, port);
        ReceiveMenu();

//        JFrame ui = new ClientUI(connection, fileList);
//        ui.setVisible(true);
    }

    public static void SendTest()
    {
        try //Informs server of incoming data and sends byte array containing image
        {
            String[] items = new String[5];
            items[0] = "Test string one\n";
            items[1] = "Test string two\n";
            items[2] = "Test string three\n";
            items[3] = "Test string four\n";
            items[4] = "Test string five\n";

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            out.write("SENDING_TEST");
            System.out.println(items.length);
            out.write(items.length);

            for (int i = 0; i < items.length; i++)
            {
                out.write(items[i]);
            }
            out.flush();
            out.close();

            System.out.println("Sent order data.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
