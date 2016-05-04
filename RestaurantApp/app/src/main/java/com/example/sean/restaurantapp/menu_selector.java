package com.example.sean.restaurantapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class menu_selector extends AppCompatActivity
{
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


    private ArrayList<String> order = new ArrayList<String>();
    private ArrayList<String> testList =new ArrayList<String>();
    private ArrayList<String> currentOrder =new ArrayList<String>();
    int foo;
    String value;
    final ArrayList<String> list = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selector);

        tableNumberHandler();
        listViewHandler();
        orderButtonListener();

    }


    private class MyListAdapter extends ArrayAdapter<String>
    {
        private int layout;
        private List<String> mObjects;
        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();


                convertView.setTag(viewHolder);
            }
            mainViewholder.item.setText(getItem(position));

            return convertView;
        }
    }


    public class ViewHolder {


        Button button;
        TextView item;

    }




    public void tableNumberHandler()
    {
        Bundle extra = getIntent().getExtras();
        value = extra.getString(table_selector.EXTRA_MESSAGE);
        TextView tableView = (TextView) findViewById(R.id.table_no);
        tableView.setText(value);

    }

    public void listViewHandler()
    {
        ListView lv = (ListView) findViewById(R.id.listView);
        Client client = new Client();
        System.out.println("Hi");
        String[][] bob = client.ReceiveMenu();

        String[][] bobSorted = client.SortedMenu(bob);
        System.out.println("Hi");

        for(String[] array : bobSorted) {
            list.addAll(Arrays.asList(array[0]));
        }

        System.out.println("Hi");
        ArrayAdapter<String> listAdapter = null;
        listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listAdapter.notifyDataSetChanged();
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                String item;
                item = list.get(position);
                currentOrder.add(item);
                System.out.println(currentOrder.toString());

                foo = Integer.parseInt(value);
            }
        });




    }

    private void orderButtonListener()
    {
        Button order_button= (Button) findViewById(R.id.order_button);
        order_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Client client = new Client();

                String[] stockArr = new String[currentOrder.size()];
                for (int i = 0; i < currentOrder.size(); i++) {
                    stockArr[i] = currentOrder.get(i).toString();
                }
                System.out.println(foo);

                client.SendBill(stockArr, foo);
            }
        });
    }

}