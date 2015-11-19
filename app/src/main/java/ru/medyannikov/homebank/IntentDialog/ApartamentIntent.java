package ru.medyannikov.homebank.IntentDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Eventbus.ApartamentChangeEvent;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 19.11.2015.
 */
public class ApartamentIntent extends AppCompatActivity {
    private TextView apartamentName;
    private TextView apartamentAbout;
    private Button buttonOk;
    private Button buttonCancel;
    private SQLiteDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartament_intent);
        dataSource = new SQLiteDataSource(this);

        apartamentName = (TextView) findViewById(R.id.editApartamentName);
        apartamentAbout = (TextView) findViewById(R.id.editApartementAbout);
        buttonCancel = (Button) findViewById(R.id.butCancel);
        buttonOk = (Button) findViewById(R.id.butOk);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.openConnection();
                dataSource.insertApartament(apartamentName.getText().toString(), apartamentAbout.getText().toString());
                BusProvider.getInstance().post(new ApartamentChangeEvent());
                finish();
            }
        });


    }
}
