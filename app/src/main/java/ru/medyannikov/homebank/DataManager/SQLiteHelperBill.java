package ru.medyannikov.homebank.DataManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vladimir on 04.10.2015.
 */
public class SQLiteHelperBill extends SQLiteOpenHelper {

    public static SQLiteDatabase db;

    public static final String TABLE_BILL = "bills";
    public static final String BILL_ID = "_id";
    public static final String BILL_NAME = "bill_name";
    public static final String BILL_ABOUT = "bill_about";
    public static final String BILL_VALUE = "bill_value";
    public static final String BILL_DEPENDENCE = "bill_dependence";
    public static final String BILL_DATE = "bill_date";
    public static final String BILL_SYNC = "bill_sync";
    public static final String BILL_ID_SERVER = "bill_id_serv";

    public static final String TABLE_OPERATION = "operations";
    public static final String OPERATION_ID = "_id";
    public static final String OPERATION_BILL = "operat_bill";
    public static final String OPERATION_VALUE = "operat_value";
    public static final String OPERATION_TYPE = "operat_type";
    public static final String OPERATION_DATE = "operat_date";
    public static final String OPERATION_SYNC = "operat_sync";
    public static final String OPERATION_ID_SERVER = "operat_id_serv";
    public static final String OPERATION_ABOUT = "operat_about";

    public static final String TABLE_USERS = "users";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_DATE = "user_date";
    public static final String USER_SYNC = "user_sync";
    public static final String USER_ID_SERVER = "user_id_serv";

    public static final String DATABASE_NAME = "bill.db";
    public static final int DATABASE_VERSION = 5;

    public static final String CREATE_BILL =
            "create table "+ TABLE_BILL
            + " ("+BILL_ID + " integer primary key autoincrement, "
            +BILL_NAME + " TEXT not null, "
            +BILL_DATE + " integer, "
            +BILL_DEPENDENCE + " integer, "
            +BILL_ABOUT + " TEXT not null, "
            +BILL_VALUE + " REAL default 0.0, "
            +BILL_SYNC + " integer default 0, "
            +BILL_ID_SERVER + " integer default 0);";

    public static final String CREATE_OPERATION =
            "create table " + TABLE_OPERATION
            +" ("+OPERATION_ID + " integer primary key autoincrement, "
            +OPERATION_BILL + " integer not null, "
            +OPERATION_DATE + " integer, "
            +OPERATION_TYPE + " integer not null default 1, "
            +OPERATION_VALUE + " real default 0, "
            +OPERATION_SYNC + " integer default 0, "
            +OPERATION_ID_SERVER + " integer default 0, "
            +OPERATION_ABOUT + " TEXT );";

    public static final String CREATE_USERS =
            "create table " + TABLE_USERS
            + " ("+ USER_ID + " integer primary key autoincrement, "
            +USER_NAME + " TEXT not null, "
            +USER_EMAIL + " TEXT not null, "
            +USER_DATE + " integer, "
            +USER_TOKEN + " TEXT, "
            +USER_SYNC + " integer default 0, "
            +USER_ID_SERVER + " integer default 0);";


    public SQLiteHelperBill(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BILL);
        db.execSQL(CREATE_OPERATION);
        db.execSQL(CREATE_USERS);
    }
    public void onCreate(SQLiteDatabase db, int ver) {
        db.execSQL(CREATE_BILL);
        db.execSQL(CREATE_OPERATION);
        /*if (ver < DATABASE_VERSION)
            db.execSQL(CREATE_USERS);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATION);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
            onCreate(db,oldVersion);
        }
    }
}
