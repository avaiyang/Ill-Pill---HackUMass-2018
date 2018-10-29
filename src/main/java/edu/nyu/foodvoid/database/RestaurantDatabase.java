package edu.nyu.foodvoid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import edu.nyu.foodvoid.pojo.RestaurantModel;

public class RestaurantDatabase {

    private static final String DB_NAME = "restaurant_db";
    private static final String TABLE_NAME = "restaurant_details";
    private static final String C_ID = "rid";
    private static final String C_NAME = "cname";
    private static final String C_ADDRESS = "caddr";
    private static final String C_PHONE = "cphone";
    private static final String C_LAT = "clat";
    private static final String C_LONG = "clong";
    private static final String C_ITEMS = "citems";
    private static final int VERSION = 2;

    private static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + C_ID + " VARCHAR NOT NULL PRIMARY KEY, " + C_NAME + " VARCHAR NOT NULL, " + C_ADDRESS + " VARCHAR NOT NULL, " + C_PHONE + " VARCHAR NOT NULL, " + C_LAT + " VARCHAR NOT NULL, " + C_LONG + " VARCHAR NOT NULL, " + C_ITEMS + " VARCHAR NOT NULL)";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    Context context;
    DBHelper mHelper;
    SQLiteDatabase mDatabase;

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(@Nullable Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_QUERY);
            onCreate(sqLiteDatabase);
        }
    }

    public RestaurantDatabase(Context c){
        context = c;
    }

    public RestaurantDatabase open(){
        mHelper = new DBHelper(context);
        mDatabase = mHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDatabase.close();
    }

    public long Insert(String id,String rName,String rAddr,String rPhone,String rLat,String rLong,String items){
        ContentValues cv = new ContentValues();
        cv.put(C_ID,id);
        cv.put(C_NAME,rName);
        cv.put(C_ADDRESS,rAddr);
        cv.put(C_PHONE,rPhone);
        cv.put(C_LAT,rLat);
        cv.put(C_LONG,rLong);
        cv.put(C_ITEMS,items);
        long result = mDatabase.insert(TABLE_NAME,null,cv);
        return result;
    }

    public ArrayList<RestaurantModel> getRestaurantData(String keyWord){
        ArrayList<RestaurantModel> restaurantData = new ArrayList<>();
        String[] columns = {C_NAME,C_ADDRESS,C_PHONE,C_ITEMS};

        Cursor c = mDatabase.query(TABLE_NAME,columns, C_ITEMS + " LIKE ?", new String[] {"%" + keyWord + "%"},null,null,null,null);
        int i_name = c.getColumnIndex(C_NAME);
        int i_address = c.getColumnIndex(C_ADDRESS);
        int i_phone = c.getColumnIndex(C_PHONE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            RestaurantModel restaurantModel = new RestaurantModel();
            restaurantModel.setrName(c.getString(i_name));
            restaurantModel.setrAddr(c.getString(i_address));
            restaurantModel.setrPhoneNumber(c.getString(i_phone));

            restaurantData.add(restaurantModel);
        }

        return restaurantData;
    }
}
