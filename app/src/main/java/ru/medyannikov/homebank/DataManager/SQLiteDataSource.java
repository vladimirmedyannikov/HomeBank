package ru.medyannikov.homebank.DataManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.Model.Operation;

/**
 * Created by Vladimir on 06.10.2015.
 */
public class SQLiteDataSource {

    private SQLiteDatabase db;
    private SQLiteHelperBill sqLiteHelper;
    private String[] allColums = {SQLiteHelperBill.BILL_ID,
            SQLiteHelperBill.BILL_ID_SERVER,
            SQLiteHelperBill.BILL_NAME,
            SQLiteHelperBill.BILL_ABOUT,
            SQLiteHelperBill.BILL_DATE,
            SQLiteHelperBill.BILL_VALUE,
            SQLiteHelperBill.BILL_DEPENDENCE,
            SQLiteHelperBill.BILL_SYNC};

    public final static List<Bill> billList = new ArrayList<Bill>();

    public SQLiteDataSource(Context context)
    {
        sqLiteHelper = new SQLiteHelperBill(context);
    }

    public void openConnection() throws SQLiteException
    {
        db = sqLiteHelper.getWritableDatabase();
    }

    public void closeConnetion(){
        sqLiteHelper.close();
    }

    public Bill insertBill(String name, String about, Double value, Long dependence, Long date, Integer sync, Integer id_serv)
    {
        ContentValues cv = new ContentValues();
        cv.put(SQLiteHelperBill.BILL_NAME,name);
        cv.put(SQLiteHelperBill.BILL_ABOUT, about);
        cv.put(SQLiteHelperBill.BILL_VALUE,value);
        cv.put(SQLiteHelperBill.BILL_DEPENDENCE, dependence);
        cv.put(SQLiteHelperBill.BILL_DATE,date);
        cv.put(SQLiteHelperBill.BILL_SYNC,sync);
        cv.put(SQLiteHelperBill.BILL_ID_SERVER, id_serv);

        long insertId = db.insert(SQLiteHelperBill.TABLE_BILL, null, cv);
        getBills();
        Bill newItem = new Bill();
        return newItem;
    }

    public List<Bill> getBills()
    {
        billList.clear();
        Cursor cursor = db.query(SQLiteHelperBill.TABLE_BILL, allColums,null,null,null,null, SQLiteHelperBill.BILL_ID + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            billList.add(cursorToBill(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return billList;
    }

    private Bill cursorToBill(Cursor cursor) {
        Bill result = new Bill(cursor.getLong(0),cursor.getString(2), cursor.getDouble(5), cursor.getInt(6),
                cursor.getLong(4), cursor.getString(3), cursor.getInt(7), cursor.getInt(1));
        return result;
    }

    public void insertOperation(Operation newOperation) {
    }
}
