package com.example.MongooseApp;

import android.app.Activity;
import android.os.Bundle;
import java.net.*;
import java.io.*;

public class OrderActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Client client = new Client("localhost", 19999);
        client.SendBill();
        client.ReceiveMenu();
    }
}
