package com.example.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    ListView funkoLV;
    Button newEntryBT;
    Button updateBT;
    Cursor mCursor;
    LinkedList<Funko> funkoList;

    View.OnClickListener newEntry = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), NewFunko.class);
            startActivity(intent);
        }
    };
    View.OnClickListener updateEntry = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), UpdateFunko.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        funkoLV = findViewById(R.id.funkoLV);
        newEntryBT = findViewById(R.id.newentryBT);
        updateBT = findViewById(R.id.updateBT);

        newEntryBT.setOnClickListener(newEntry);
        updateBT.setOnClickListener(updateEntry);

        makeList();
        setCursorAdapter();
        registerForContextMenu(funkoLV);
    }

    public void makeList(){
        mCursor = getContentResolver().query(FunkoProvider.contentURI, null, null, null, null);
        funkoList = new LinkedList<>();

        if(mCursor != null){
            mCursor.moveToFirst();
            if(mCursor.getCount() > 0){
                while(mCursor.isAfterLast() == false){
                    String name = mCursor.getString(1);
                    int number = mCursor.getInt(2);
                    String type = mCursor.getString(3);
                    String fandom = mCursor.getString(4);
                    int on_off = mCursor.getInt(5);
                    boolean on = true;
                    String ultimate = mCursor.getString(6);
                    double price = mCursor.getDouble(7);
                    if(on_off == 0){
                        on = false;
                    }

                    funkoList.add(new Funko(name, number, type, fandom, on, ultimate, price));
                    mCursor.moveToNext();
                }
            }
        }
    }

    public void setCursorAdapter(){
        mCursor = getContentResolver().query(FunkoProvider.contentURI, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() == 0) {
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[] {});
                funkoLV.setAdapter(adapter);
                return;
            }
            /*SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    mCursor,
                    new String[] { FunkoProvider.COL_1, FunkoProvider.COL_2},
                    new int[] { android.R.id.text1, android.R.id.text2 });*/

            CursorAdapter adapter = new CursorAdapter(getApplicationContext(), mCursor) {
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                    return LayoutInflater.from(context).inflate(R.layout.adapter_display, viewGroup, false);
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    TextView numberTV = view.findViewById(R.id.numberTV);
                    TextView nameTV = view.findViewById(R.id.nameTV);
                    TextView typeTV = view.findViewById(R.id.typeTV);
                    TextView fandomTV = view.findViewById(R.id.fandomTV);
                    TextView on_offTV = view.findViewById(R.id.on_off_tv);
                    TextView ultimateTV = view.findViewById(R.id.ultimateTV);
                    TextView priceTV = view.findViewById(R.id.priceTV);

                    String name = cursor.getString(cursor.getColumnIndexOrThrow(FunkoProvider.COL_1));
                    int number = cursor.getInt(cursor.getColumnIndexOrThrow(FunkoProvider.COL_2));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(FunkoProvider.COL_3));
                    String fandom = cursor.getString(cursor.getColumnIndexOrThrow(FunkoProvider.COL_4));
                    int on_off = cursor.getInt(cursor.getColumnIndexOrThrow(FunkoProvider.COL_5));
                    String ultimate = cursor.getString(cursor.getColumnIndexOrThrow(FunkoProvider.COL_6));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FunkoProvider.COL_7));

                    numberTV.setText(String.valueOf(number));
                    nameTV.setText(name);
                    typeTV.setText(type);
                    fandomTV.setText(fandom);
                    ultimateTV.setText(ultimate);
                    priceTV.setText("$" + price);
                    if (on_off == 0) {
                        on_offTV.setText("off");
                    }else{
                        on_offTV.setText("on");
                    }
                }
            };

            funkoLV.setAdapter(adapter);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Deleting Item", Toast.LENGTH_LONG).show();
            Funko f = funkoList.get(position);
            funkoList.remove(position);
            String mSelectedClause = FunkoProvider.COL_1 + " = ? " + " AND " +
                    FunkoProvider.COL_2 + " = ? " + " AND " +
                    FunkoProvider.COL_3 + " = ? " + " AND " +
                    FunkoProvider.COL_4 + " = ? " + " AND " +
                    //FunkoProvider.COL_5 + " = ? " + " AND " +
                    FunkoProvider.COL_6 + " = ? ";// + " AND " +
                    //FunkoProvider.COL_7 + " = ? ";
            String[] mSelectionArgs = {f.getName(), String.valueOf(f.getNumber()), f.getType(), f.getFandom(),
                    /*String.valueOf(f.isOn_off()),*/ f.getUltimate()};//, String.valueOf(f.getPrice())};

            int mRowsDeleted = getContentResolver().delete(FunkoProvider.contentURI, mSelectedClause, mSelectionArgs);
        }
        makeList();
        setCursorAdapter();
        return true;
    }
}