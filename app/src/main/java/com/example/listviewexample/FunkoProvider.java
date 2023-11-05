package com.example.listviewexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class FunkoProvider extends ContentProvider {


    public static final String DBNAME = "FunkoDB";
    public static final String TABLE_NAME = "FunkoEntries";
    public static final String COL_1 = "POP_NAME";
    public static final String COL_2 = "POP_NUMBER";
    public static final String COL_3 = "POP_TYPE";
    public static final String COL_4 = "FANDOM";
    public static final String COL_5 = "ON_BOOL";
    public static final String COL_6 = "ULTIMATE";
    public static final String COL_7 = "PRICE";
    public static final String AUTHORITY = "belmont.funko";
    public static final Uri contentURI = Uri.parse("content://" + AUTHORITY + "/" + DBNAME);
    private MainDatabaseHelper SQLHelper;

    private static String CREATE_DB_QUERY = "CREATE TABLE " + TABLE_NAME +
            "( _ID INTEGER PRIMARY KEY," +
            COL_1 + " TEXT," +
            COL_2 + " INTEGER," +
            COL_3 + " TEXT," +
            COL_4 + " TEXT," +
            COL_5 + " BOOLEAN," +
            COL_6 + " TEXT," +
            COL_7 + " DOUBLE)";
    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_DB_QUERY);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){
        }
    };
    public FunkoProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return SQLHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = SQLHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        return Uri.withAppendedPath(contentURI,"" + id);
    }

    @Override
    public boolean onCreate() {
        SQLHelper = new MainDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return SQLHelper.getReadableDatabase().query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return SQLHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
    }
}