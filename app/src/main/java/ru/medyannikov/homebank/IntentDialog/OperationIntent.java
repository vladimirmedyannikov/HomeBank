package ru.medyannikov.homebank.IntentDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import de.greenrobot.event.EventBus;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.Model.Operation;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 15.10.2015.
 */
public class OperationIntent extends AppCompatActivity {
    private Spinner spinner;
    private EditText editValue;
    private EditText editAbout;
    private Button buttonOk;
    private Button buttonCancel;
    private SQLiteDataSource dataSource;
    private EventBus eventBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);

        setContentView(R.layout.activity_operation_intent);
        spinner = (Spinner) findViewById(R.id.editSpinerOperation);
        editValue = (EditText) findViewById(R.id.editOperationValue);
        editAbout = (EditText) findViewById(R.id.editOperationAbout);
        buttonOk = (Button) findViewById(R.id.butOk);
        buttonCancel = (Button) findViewById(R.id.butCancel);

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
                dataSource = new SQLiteDataSource(v.getContext());
                dataSource.openConnection();
                String about = editAbout.getText().toString();
                Double value = Double.valueOf(editValue.getText().toString());
                Operation newOperation = new Operation();
                newOperation.setAbout(about);
                newOperation.setValue(value);
                newOperation.setIdBill(Integer.valueOf(getIntent().getAction()));
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
