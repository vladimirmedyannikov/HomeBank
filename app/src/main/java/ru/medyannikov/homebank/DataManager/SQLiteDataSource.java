package ru.medyannikov.homebank.DataManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.Model.Operation;

/**
 * Created by Vladimir on 06.10.2015.
 */
public class SQLiteDataSource {

    private SQLiteDatabase db;
    private SQLiteHelperBill sqLiteHelper;
    private String[] allColumsBill = {SQLiteHelperBill.BILL_ID,
            SQLiteHelperBill.BILL_ID_SERVER,
            SQLiteHelperBill.BILL_NAME,
            SQLiteHelperBill.BILL_ABOUT,
            SQLiteHelperBill.BILL_DATE,
            SQLiteHelperBill.BILL_VALUE,
            SQLiteHelperBill.BILL_DEPENDENCE,
            SQLiteHelperBill.BILL_SYNC};

    private String[] allColumnsOperation = {
            SQLiteHelperBill.OPERATION_ID,
            SQLiteHelperBill.OPERATION_ID_SERVER,
            SQLiteHelperBill.OPERATION_ABOUT,
            SQLiteHelperBill.OPERATION_BILL,
            SQLiteHelperBill.OPERATION_DATE,
            SQLiteHelperBill.OPERATION_SYNC,
            SQLiteHelperBill.OPERATION_TYPE,
            SQLiteHelperBill.OPERATION_VALUE
    };

    public final static List<Bill> billList = new ArrayList<Bill>();
    public final static List<Operation> operationList = new ArrayList<Operation>();

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
        cv.put(SQLiteHelperBill.BILL_NAME, name);
        cv.put(SQLiteHelperBill.BILL_ABOUT, about);
        cv.put(SQLiteHelperBill.BILL_VALUE,value);
        cv.put(SQLiteHelperBill.BILL_DEPENDENCE, dependence);
        cv.put(SQLiteHelperBill.BILL_DATE,date);
        cv.put(SQLiteHelperBill.BILL_SYNC,sync);
        cv.put(SQLiteHelperBill.BILL_ID_SERVER, id_serv);

        long insertId = db.insert(SQLiteHelperBill.TABLE_BILL, null, cv);
        /*getBills();*/
        Bill newItem = new Bill();
        return newItem;
    }

    public List<Bill> getBills()
    {
        billList.clear();
        Cursor cursor = db.query(SQLiteHelperBill.TABLE_BILL, allColumsBill, null, null, null, null, SQLiteHelperBill.BILL_ID + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            billList.add(cursorToBill(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return billList;
    }

    public List<Entry> getBillEntry(int id){
        List<Entry> entries = new ArrayList<Entry>();
        billList.get(id);
        for (Bill b:billList) {
            Entry entry = new Entry(b.getValue().floatValue(),b.get_id());
            entries.add(entry);
        }
        return entries;
    }

    public ArrayList<String> getBillXVlas(){
        ArrayList<String> list = new ArrayList<String>();
        for (Operation b:operationList) {
            list.add(b.getDate().toString());
        }
        return list;
    }

    public List<LineDataSet> getBillsDataset(){
        List<LineDataSet> dataSetList = new ArrayList<LineDataSet>();
        for (Bill b:billList) {
            ArrayList<Entry> listEntry = new ArrayList<Entry>();
            for (Operation op:operationList) {
                if (b.get_id() == op.getIdBill()) {
                    listEntry.add(new Entry(op.getPrev_value().floatValue(),operationList.indexOf(op)));
                }
            }
            LineDataSet set = new LineDataSet(listEntry, b.getName());
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(Color.rgb(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)));
            set.setCircleColor(Color.BLACK);
            set.setLineWidth(1f);
            set.setCircleSize(2f);
            set.setFillAlpha(65);
            set.setFillColor(Color.rgb(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)));
            set.setHighLightColor(Color.rgb(244, 117, 117));
            set.setDrawCircleHole(false);
            set.setValueTextColor(Color.BLACK);

            dataSetList.add(set);
        }
        return dataSetList;
    }

    public List<Operation> getOperations(){
        operationList.clear();

        Cursor cursor = db.rawQuery("Select "
                + SQLiteHelperBill.TABLE_OPERATION + "." + SQLiteHelperBill.OPERATION_ID + ","
                + SQLiteHelperBill.OPERATION_ID_SERVER + ","
                + SQLiteHelperBill.OPERATION_ABOUT + ","
                + SQLiteHelperBill.OPERATION_BILL + ","
                + SQLiteHelperBill.OPERATION_DATE + ","
                + SQLiteHelperBill.OPERATION_SYNC + ","
                + SQLiteHelperBill.OPERATION_TYPE + ","
                + SQLiteHelperBill.OPERATION_VALUE  + ","
                + SQLiteHelperBill.BILL_NAME + ","
                + SQLiteHelperBill.OPERATION_PREV_VALUE
                + " from " + SQLiteHelperBill.TABLE_OPERATION + " left join " + SQLiteHelperBill.TABLE_BILL
                + " on " + SQLiteHelperBill.TABLE_BILL +"."+ SQLiteHelperBill.BILL_ID + " = " + SQLiteHelperBill.OPERATION_BILL, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            operationList.add(cursorToOperation(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return operationList;
    }

    private Bill cursorToBill(Cursor cursor) {
        Bill result = new Bill(cursor.getInt(0),cursor.getString(2), cursor.getDouble(5), cursor.getInt(6),
                cursor.getLong(4), cursor.getString(3), cursor.getInt(7), cursor.getInt(1));
        return result;
    }

    private Operation cursorToOperation(Cursor cursor){
        Operation resulOperation = new Operation();

        resulOperation.setId(cursor.getInt(0));
        resulOperation.setAbout(cursor.getString(2));
        resulOperation.setDate(cursor.getString(4));
        resulOperation.setIdBill(cursor.getInt(3));
        resulOperation.setIdServ(cursor.getInt(1));
        resulOperation.setSync(cursor.getInt(5));
        resulOperation.setType(cursor.getInt(6));
        resulOperation.setValue(cursor.getDouble(7));
        resulOperation.setNameBill(cursor.getString(8));
        resulOperation.setPrev_value(cursor.getDouble(9));

        return resulOperation;
    }

    public Long insertOperation(Operation newOperation) {
        ContentValues cv = new ContentValues();

        cv.put(SQLiteHelperBill.OPERATION_ABOUT, newOperation.getAbout());
        cv.put(SQLiteHelperBill.OPERATION_BILL, newOperation.getIdBill());
        cv.put(SQLiteHelperBill.OPERATION_DATE, newOperation.getDate());
        //cv.put(SQLiteHelperBill.OPERATION_ID, newOperation.getId());
        cv.put(SQLiteHelperBill.OPERATION_ID_SERVER,newOperation.getIdServ());
        cv.put(SQLiteHelperBill.OPERATION_SYNC,newOperation.getSync());
        cv.put(SQLiteHelperBill.OPERATION_TYPE, newOperation.getType());
        cv.put(SQLiteHelperBill.OPERATION_VALUE, newOperation.getValue());

        Long idInsert = db.insert(SQLiteHelperBill.TABLE_OPERATION, null, cv);
        return idInsert;
    }
}
