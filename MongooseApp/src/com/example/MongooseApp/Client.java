package com.example.MongooseApp;

import android.app.ActionBar;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joshua on 18/02/16.
 */
public class Client
{
//    public String[][] menu;
    private String host = "192.168.43.224";
    private int port = 19999;
    private InetAddress inet;
    private static Socket serverSocket;

    public Client() {
        try
        {
            System.out.print("Trying to create socket to " + host + " on port " + port + "...");
            this.inet = InetAddress.getByName(host);
            this.serverSocket = new Socket(this.inet, this.port);
            System.out.print("OK!\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Host name :  " + inet.getHostName());
        System.out.println("IP Address:  " + inet.getHostAddress());
    }

    public void connect() {
        SendTest();
    }

    public void Disconnect() {
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

    public static String[][] ReceiveMenu() {
        /**
         * Runs on connect to server and pulls most recent menu as 2D array.
         */

        String[][] menu;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            System.out.println("Connection established");
            out.write("SEND_MENU");
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            int int1 = in.read(); //Get array dimensions
            int int2 = in.read();

            menu = new String[int1][int2];

            for (int i = 0; i < menu.length; i++) {
                for (int j = 0; j < 3; j++) {
                    menu[i][j] = in.readLine(); //Read in menu contents

                }
            }
            return menu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[][] SortedMenu(String[][] menu) {
        final Map<String, Integer> dict = new HashMap<String, Integer>();
        dict.put("starters", 0);
        dict.put("mains", 1);
        dict.put("desserts", 2);
        dict.put("drinks", 3);

        for (int i = 0; i < menu.length - 1; i++)
        {
            int course1 = dict.get(menu[i][1]);
            int course2 = dict.get(menu[i + 1][1]);

            if (course1 > course2)
            {
                String tmpRow[] = menu[i];
                menu[i] = menu[i + 1];
                menu[i + 1] = tmpRow;
            }
        }

//        Arrays.sort(menu, new Comparator<String[]>() {
//            @Override
//            public int compare(String[] item1, String[] item2) {
//                int course1 = dict.get(item1[1]);
//                int course2 = dict.get(item2[1]);
//                return course1.compareTo(course2);
//            }
//        });

        for (String[] s : menu) {
            System.out.println(s[0] + " " + s[1] + " " + s[2]);
        }
        return menu;
    }

    public void SendBill(String[] items, int tableNo) {
        /**
         * Method used for sending order details to server application.
         *
         * @param items Item array holding order data.
         * @param tableNo Integer holding table number to associate order with any open orders
         */

        try //Informs server of incoming data and sends byte array containing image
        {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            System.out.println("Connection established");
            out.write("SENDING_BILL");

            System.out.println("Order size: " + items.length);

            System.out.print("Sending order data...");
            out.write(tableNo);
            out.write(items.length);

            for (int i = 0; i < items.length; i++)
            {
                out.write(items[i]);
            }
            System.out.print("OK!\n");

            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

    public void SendTest() {
        /**
         * Test procedure for connecting to server and transmitting string array.
         */

        try //Informs server of incoming data and sends byte array containing image
        {
            String[] items = new String[5];
            items[0] = "Test string one\n";
            items[1] = "Test string two\n";
            items[2] = "Test string three\n";
            items[3] = "Test string four\n";
            items[4] = "Test string five\n";

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            System.out.println("Connection established.");
            out.write("SENDING_TEST");

            System.out.println(items.length);
            out.write(items.length);

            for (int i = 0; i < items.length; i++)
            {
                out.write(items[i]);
            }

            System.out.print("Confirming transmission...");
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            String confirmed = in.readLine();
            if (confirmed == "OK")
            {
                System.out.print("OK!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
