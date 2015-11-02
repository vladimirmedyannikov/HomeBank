package ru.medyannikov.homebank.IntentDialog;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.Model.Operation;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 15.10.2015.
 */
public class OperationIntent extends AppCompatActivity {
    private Spinner spinner;
    private Spinner spinnerBill;
    private EditText editValue;
    private EditText editAbout;
    private Button buttonOk;
    private Button buttonCancel;
    private SQLiteDataSource dataSource;
    private EventBus eventBus;
    private List<Bill> billList;
    private boolean res = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new SQLiteDataSource(this);
        dataSource.openConnection();



        BusProvider.getInstance().register(this);

        setContentView(R.layout.activity_operation_intent);
        spinner = (Spinner) findViewById(R.id.editSpinerOperation);
        editValue = (EditText) findViewById(R.id.editOperationValue);
        editAbout = (EditText) findViewById(R.id.editOperationAbout);
        buttonOk = (Button) findViewById(R.id.butOk);
        buttonCancel = (Button) findViewById(R.id.butCancel);
        spinnerBill = (Spinner) findViewById(R.id.editSpinerBill);

        Bundle extast = getIntent().getExtras();
        res = extast.getBoolean(ClassUtils.INTENT_ADD_OPERATION);

        if (res) {
            spinnerBill.setVisibility(View.VISIBLE);
            billList = dataSource.getBills();
            List<String> str = new ArrayList<String>();
            /*for (Bill b:billList) {
                str.add(b.getName());
            }
            String[] arrayStr = str.toArray(new String[str.size()]);*/
            /*ArrayAdapter<String> adapterBill = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayStr);
            adapterBill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
            ArrayAdapter adapterBill = new ArrayAdapter(this,android.R.layout.simple_spinner_item,billList);
            spinnerBill.setAdapter(adapterBill);
        }

        Toast.makeText(this,getIntent().getAction(),Toast.LENGTH_SHORT).show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String about = editAbout.getText().toString();
                Double value = Double.valueOf(editValue.getText().toString());
                Operation newOperation = new Operation();
                newOperation.setAbout(about);
                if (spinner.getSelectedItemPosition() == 0){
                newOperation.setValue(value);}else newOperation.setValue(value*(-1));
                if (res){
                    newOperation.setIdBill(((Bill) spinnerBill.getSelectedItem()).get_id());
                }
                else newOperation.setIdBill(Integer.valueOf(getIntent().getAction()));
                /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                simpleDateFormat.format(simpleDateFormat);
                newOperation.setDate(new Date().);*/
                newOperation.setType(spinner.getSelectedItemPosition());



                dataSource.insertOperation(newOperation);
                dataSource.closeConnetion();

                BusProvider.getInstance().post(new OperationChangeEvent());
                finish();
            }
        });


        /*ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,R.array.operation,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,new String[]{"addition","substraction"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    @Subscribe
    public void onEvent(OperationChangeEvent event){

    }
}
