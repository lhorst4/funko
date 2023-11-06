package com.example.listviewexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class UpdateFunko extends FragmentActivity {
    Cursor mCursor;
    EditText nameET;
    Spinner numberSP;
    EditText typeET;
    EditText fandomET;
    EditText ultimateET;
    EditText priceET;
    RadioGroup onRG;
    RadioButton onRB;
    Button addBT;
    Button collectionBT;
    View.OnClickListener updateEntry = new View.OnClickListener() {
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
                num = Integer.valueOf(numberSP.getSelectedItem().toString());
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
            ContentValues mNewValues = new ContentValues();
            mNewValues.put(FunkoProvider.COL_1, name);
            mNewValues.put(FunkoProvider.COL_2, num);
            mNewValues.put(FunkoProvider.COL_3, type);
            mNewValues.put(FunkoProvider.COL_4, fandom);
            mNewValues.put(FunkoProvider.COL_5, on);
            mNewValues.put(FunkoProvider.COL_6, ultimate);
            mNewValues.put(FunkoProvider.COL_7, price);

            String mSelectedClause = FunkoProvider.COL_2 + " = ?";
            String[] mSelectionArgs = { numberSP.getSelectedItem().toString() };

            getContentResolver().update(FunkoProvider.contentURI, mNewValues, mSelectedClause, mSelectionArgs);

            Toast.makeText(getApplicationContext(), "Entry has been updated.", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_update_funko);

        nameET = findViewById(R.id.nameET);
        numberSP = findViewById(R.id.numberSP);
        typeET = findViewById(R.id.typeET);
        fandomET = findViewById(R.id.fandomET);
        ultimateET = findViewById(R.id.ultimateET);
        priceET = findViewById(R.id.priceET);

        onRG = findViewById(R.id.onRG);
        onRB = findViewById(R.id.onRB);

        addBT = findViewById(R.id.updateBT);
        collectionBT = findViewById(R.id.collectionBT);

        addBT.setOnClickListener(updateEntry);
        collectionBT.setOnClickListener(viewCollection);

        setSpinner();
    }
    public void setSpinner(){
        mCursor = getContentResolver().query(FunkoProvider.contentURI, null, null, null, null);
        LinkedList<Integer> nums = new LinkedList<>();
        int sizeOfDb = 0;
        if(mCursor != null){
            mCursor.moveToFirst();
            if(mCursor.getCount() > 0){
                while(mCursor.isAfterLast() == false){

                    int number = mCursor.getInt(2);

                    nums.add(number);

                    sizeOfDb++;
                    mCursor.moveToNext();
                }
            }
        }
        String[] listOfNumbers = new String[sizeOfDb];
        for(int i = 0; i < sizeOfDb; i++){
            listOfNumbers[i] = String.valueOf(nums.get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listOfNumbers);
        numberSP.setAdapter(adapter);
    }
}