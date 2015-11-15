package ru.medyannikov.homebank.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.medyannikov.homebank.Adapter.RecycleAdapterApartaments;
import ru.medyannikov.homebank.Adapter.RecycleAdapterBill;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Model.Apartament;
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

        List<Apartament> apartaments = new ArrayList<Apartament>();
        apartaments.add(new Apartament(1,"Apartament 1", 100d));
        apartaments.add(new Apartament(2, "Apartament 2", 200d));
        recyclerView.setAdapter(new RecycleAdapterApartaments(apartaments));
    }

}
