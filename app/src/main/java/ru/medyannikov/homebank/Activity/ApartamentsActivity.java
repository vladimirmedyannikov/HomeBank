package ru.medyannikov.homebank.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.melnykov.fab.FloatingActionButton;

import ru.medyannikov.homebank.Adapter.RecycleAdapterBill;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.R;

public class ApartamentsActivity extends Activity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartaments);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewApartament);
        fab = (FloatingActionButton) findViewById(R.id.fab_apartaments);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new RecycleAdapterBill(SQLiteDataSource.billList));
    }

}
