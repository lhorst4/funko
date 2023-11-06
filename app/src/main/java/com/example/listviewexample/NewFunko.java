package com.example.listviewexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.LinkedList;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class NewFunko extends FragmentActivity {

    Cursor mCursor;
    EditText nameET;
    EditText numberET;
    EditText typeET;
    EditText fandomET;
    EditText ultimateET;
    EditText priceET;
    RadioGroup onRG;
    RadioButton onRB;
    Button addBT;
    Button collectionBT;

    View.OnClickListener addEntry = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = null;
            int num = 0;
            String type = null;
            String fandom = null;
            int on_int = 1;
            boolean on = true;
            String ultimate = null;
            double price = 0;
            LinkedList<String> errors = new LinkedList<>();

            try{
                name = nameET.getText().toString();
            }catch(Exception e){
                errors.add("Invalid name"); // for later implementation
            }
            try{
                num = Integer.valueOf(numberET.getText().toString());
            }catch(Exception e){
                errors.add("Invalid number");// for later implementation
            }
            try{
                type = typeET.getText().toString();
            }catch(Exception e){
                errors.add("Invalid type");// for later implementation
            }
            try{
                fandom = fandomET.getText().toString();
            }catch(Exception e){
                errors.add("Invalid fandom");// for later implementation
            }
            try{
                onRB = findViewById(onRG.getCheckedRadioButtonId());
                String choice = onRB.getText().toString();
                if(choice.equals("Off")){
                    on = false;
                    on_int = 0;
                }
            }catch(Exception e){
                errors.add("Invalid on/off choice");// for later implementation
            }
            try{
                ultimate = ultimateET.getText().toString();
            }catch(Exception e){
                errors.add("Invalid ulitmate");// for later implementation
            }try{
                price = Double.valueOf(priceET.getText().toString());
            }catch(Exception e){
                errors.add("Invalid price");// for later implementation
            }

            if(!errors.isEmpty()){
                Toast.makeText(getApplicationContext(), "Input Error.", Toast.LENGTH_LONG).show();
                return;
            }
            // check for duplicates
            mCursor = getContentResolver().query(FunkoProvider.contentURI, null, null, null, null);
            if(mCursor != null){
                mCursor.moveToFirst();
                if(mCursor.getCount() > 0){
                    while(mCursor.isAfterLast() == false){
                        int number = mCursor.getInt(2);
                        if(number == num){
                            Toast.makeText(getApplicationContext(), "Failed to add entry: number already in use.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        mCursor.moveToNext();
                    }
                }
            }

            ContentValues mNewValues = new ContentValues();
            mNewValues.put(FunkoProvider.COL_1, name);
            mNewValues.put(FunkoProvider.COL_2, num);
            mNewValues.put(FunkoProvider.COL_3, type);
            mNewValues.put(FunkoProvider.COL_4, fandom);
            mNewValues.put(FunkoProvider.COL_5, on);
            mNewValues.put(FunkoProvider.COL_6, ultimate);
            mNewValues.put(FunkoProvider.COL_7, price);
            getContentResolver().insert(FunkoProvider.contentURI, mNewValues);
            Toast.makeText(getApplicationContext(), "Entry has been added.", Toast.LENGTH_LONG).show();
        }
    };
    View.OnClickListener viewCollection = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_funko);

        nameET = findViewById(R.id.nameET);
        numberET = findViewById(R.id.numberET);
        typeET = findViewById(R.id.typeET);
        fandomET = findViewById(R.id.fandomET);
        ultimateET = findViewById(R.id.ultimateET);
        priceET = findViewById(R.id.priceET);

        onRG = findViewById(R.id.onRG);
        onRB = findViewById(R.id.onRB);

        addBT = findViewById(R.id.updateBT);
        collectionBT = findViewById(R.id.collectionBT);

        addBT.setOnClickListener(addEntry);
        collectionBT.setOnClickListener(viewCollection);
    }
}