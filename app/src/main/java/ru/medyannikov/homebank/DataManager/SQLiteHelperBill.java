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
    public static final String OPERATION_PREV_VALUE = "operat_prev_val";
    public static final String OPERATION_TRIGGER_INSERT = "trig_insert_operat";

    public static final String TABLE_USERS = "users";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_DATE = "user_date";
    public static final String USER_SYNC = "user_sync";
    public static final String USER_ID_SERVER = "user_id_serv";

    public static final String TABLE_APARTAMENTS = "apartaments";
    public static final String APART_ID = "_id";
    public static final String APART_NAME = "apart_name";
    public static final String APART_VALUE = "apart_value";

    public static final String TABLE_APART_DETAILS = "apart_details";
    public static final String APART_DETAILS_ID = "_id";
    public static final String APART_DETAILS_HOT = "details_hot";
    public static final String APART_DETAILS_COLD = "details_cold";
    public static final String APART_DETAILS_HEATING = "details_heating";
    public static final String APART_DETAILS_L_D = "details_light_day";
    public static final String APRAT_DETAILS_L_N = "details_light_night";
    public static final String APART_DETAILS_CONTENT = "details_content";
    public static final String APART_DETAILS_WATER = "details_water";
    public static final String APART_DETAILS_INTERCOM = "details_intercom";
    public static final String APART_DETAILS_YEAR = "details_year";
    public static final String APART_DETAILS_MONTH = "details_month";
    public static final String APART_DETAILS_OTHER = "details_other";
    public static final String APART_DETAILS_REPAIR = "details_repair";

    public static final String DATABASE_NAME = "bill.db";
    public static final int DATABASE_VERSION = 22;

    public static final String CREATE_APARTAMENTS =
            "create table " + TABLE_APARTAMENTS
            + " (" + APART_ID + " integer primary key autoincrement, "
            + APART_NAME + " TEXT not null, "
            + APART_VALUE + " REAL default 0.0);";

    public static final String CREATE_APART_DETAILS =
            "create table " + TABLE_APART_DETAILS
            + " (" + APART_DETAILS_ID + " integer primary key autoincrement, "
            + APART_DETAILS_HOT + " real default 0.0, "
            + APART_DETAILS_COLD + " real default 0.0, "
            + APART_DETAILS_HEATING + " real default 0.0, "
            + APART_DETAILS_L_D + " real default 0.0, "
            + APRAT_DETAILS_L_N + " real default 0.0, "
            + APART_DETAILS_CONTENT + " real default 0.0, "
            + APART_DETAILS_WATER + " real default 0.0, "
            + APART_DETAILS_INTERCOM + " real default 0.0, "
            + APART_DETAILS_YEAR + " integer default 2015, "
            + APART_DETAILS_MONTH + " integer default 1, "
            + APART_DETAILS_OTHER + " real default 0.0, "
            + APART_DETAILS_REPAIR + " real default 0.0);";

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
            +OPERATION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            +OPERATION_TYPE + " integer not null default 1, "
            +OPERATION_VALUE + " real default 0, "
            +OPERATION_SYNC + " integer default 0, "
            +OPERATION_ID_SERVER + " integer default 0, "
            +OPERATION_PREV_VALUE + " real default 0.0, "
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

   /* public static final String CREATE_TRIGGER_INSERT_OPERATION =
            "create trigger "+ OPERATION_TRIGGER_INSERT
            + " after insert on " + TABLE_OPERATION
            + " BEGIN "
            + " UPDATE " + TABLE_BILL + " SET " + BILL_VALUE + " = (select sum(case "+ OPERATION_TYPE +" when " + OPERATION_TYPE + " = 0 then " + OPERATION_VALUE
                    + " else 0 end) from "+ TABLE_OPERATION +" where " + OPERATION_BILL + " = " + TABLE_BILL +"."+ BILL_ID + ");"
            + " END;";*/
    public static final String CREATE_TRIGGER_INSERT_OPERATION =
            "create trigger "+ OPERATION_TRIGGER_INSERT
                    + " after insert on " + TABLE_OPERATION //last_insert_rowid()
                    + " BEGIN "

                    + " UPDATE " + TABLE_BILL + " SET " + BILL_VALUE + " = (select sum( " + OPERATION_VALUE
                    + " ) from "+ TABLE_OPERATION +" where " + OPERATION_BILL + " = " + TABLE_BILL +"."+ BILL_ID + "); "
                    + " UPDATE " + TABLE_OPERATION + " SET " + OPERATION_PREV_VALUE + " = (select " + BILL_VALUE + " from " + TABLE_BILL + " where "+ BILL_ID +" = " + TABLE_OPERATION+"."+OPERATION_BILL+") where " + OPERATION_ID +" = last_insert_rowid();"
                    + " END;";

    public SQLiteHelperBill(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BILL);
        db.execSQL(CREATE_OPERATION);
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_APARTAMENTS);
        db.execSQL(CREATE_APART_DETAILS);
    }
    public void onCreate(SQLiteDatabase db, int ver) {
        db.execSQL(CREATE_BILL);
        db.execSQL(CREATE_OPERATION);
        db.execSQL(CREATE_APARTAMENTS);
        db.execSQL(CREATE_APART_DETAILS);
        /*if (ver < DATABASE_VERSION)
            db.execSQL(CREATE_USERS);*/
        db.execSQL(CREATE_TRIGGER_INSERT_OPERATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion)
        {
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APART_DETAILS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APARTAMENTS);

            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
            db.execSQL("DROP TRIGGER IF EXISTS " + OPERATION_TRIGGER_INSERT );
            onCreate(db,oldVersion);
        }
    }
}
