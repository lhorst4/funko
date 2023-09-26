package com.example.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

import model.Pet;

public class MainActivity extends AppCompatActivity {
    ListView list;
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Toast.makeText(getApplicationContext(), list.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.petLV);
        list.setOnItemClickListener(listener);

        int selection = 2;
        populateListView(selection);
    }
    private void populateListView(int selection) {
        switch (selection) {
            case 1:
                useStringResource();
                break;
            case 2:
                usingDataArray();
                break;
        }
    }
    /**
     * This method populates the list view using a string resource containing
     * the content of a strings array resource
     */
    private void useStringResource() {
        String[] values = getResources().getStringArray(R.array.breeds);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
    }
    /**
     * This method populates the list view using a string resource containing the list of a data array
     */
    private void usingDataArray() {
        LinkedList<Pet> list2 = new LinkedList<Pet>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list2);
        list2.add(new Pet("name", "breed", 5));
        list2.add(new Pet("jeff", "Lab", 3));
        list2.add(new Pet("bartholemeu", "Yorki", 15));
        list2.add(new Pet("forest", "Chihuahua", 2));
        list.setAdapter(adapter);
    }
}