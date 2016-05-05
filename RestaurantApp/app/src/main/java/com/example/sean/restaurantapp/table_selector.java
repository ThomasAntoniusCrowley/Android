package com.example.sean.restaurantapp;

/**
 * this class manages the launch screen for the app, the user clicks a table. the table number is logged
 * and sent as a message to the next activity, where the table number is displayed at the top of the menu
 *
 * @author Thomas Crowley & Sean O'Connor
 */
 
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class table_selector extends AppCompatActivity {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    public final static String EXTRA_MESSAGE = "table 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selector);

    }

//this handles the button clicks and send the relevant table number to the next activivty 

    public void handleButtonClick(View view) {

        Intent intent = new Intent(table_selector.this, menu_selector.class);


        switch (view.getId()) {

            case R.id.table1:
                String text1 = "1";
                intent.putExtra(EXTRA_MESSAGE, text1);
                startActivity(intent);
                break;
            case R.id.table2:
                String text2 = "2";
                intent.putExtra(EXTRA_MESSAGE, text2);
                startActivity(intent);
                break;
            case R.id.table3:
                String text3 = "3";
                intent.putExtra(EXTRA_MESSAGE, text3);
                startActivity(intent);
                break;
            case R.id.table4:
                String text4 = "4";
                intent.putExtra(EXTRA_MESSAGE, text4);
                startActivity(intent);
                break;
            case R.id.table5:
                String text5 = "5";
                intent.putExtra(EXTRA_MESSAGE, text5);
                startActivity(intent);
                break;
            case R.id.table6:
                String text6 = "6";
                intent.putExtra(EXTRA_MESSAGE, text6);
                startActivity(intent);
                break;
            case R.id.table7:
                String text7 = "7";
                intent.putExtra(EXTRA_MESSAGE, text7);
                startActivity(intent);
                break;
            case R.id.table8:
                String text8 = "8";
                intent.putExtra(EXTRA_MESSAGE, text8);
                startActivity(intent);
                break;
            case R.id.table9:
                String text9 = "9";
                intent.putExtra(EXTRA_MESSAGE, text9);
                startActivity(intent);
                break;


        }
    }
}