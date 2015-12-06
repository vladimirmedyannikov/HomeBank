package ru.medyannikov.homebank.IntentDialog;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Eventbus.BillChangeEvent;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;

public class BillIntent extends AppCompatActivity {
    private Button buttonOk;
    private Button buttonCancel;
    private EditText editName;
    private EditText editAbout;
    private SQLiteDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_intent);

        buttonOk = (Button) findViewById(R.id.butOk);
        buttonCancel = (Button) findViewById(R.id.butCancel);
        editName = (EditText) findViewById(R.id.editBillName);
        editAbout = (EditText) findViewById(R.id.editBillAbout);

        buttonOk.setOnClickListener(new buttonOkListener());
        buttonCancel.setOnClickListener(new buttonCancelListener());

    }

    private class buttonOkListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dataSource = new SQLiteDataSource(BillIntent.this);
            dataSource.openConnection();
            dataSource.insertBill(editName.getText().toString(), editAbout.getText().toString(), 0.0, 0L, new Date().getTime(), 0, 0);
            Snackbar.make(v, "Bill " + editName.getText().toString() + " success added!", Snackbar.LENGTH_SHORT).show();
            BusProvider.getInstance().post(new BillChangeEvent());
            finish();
        }
    }

    private class buttonCancelListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
