package ru.medyannikov.homebank.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.medyannikov.homebank.Adapter.RecycleAdapterApartaments;
import ru.medyannikov.homebank.Adapter.RecycleAdapterBill;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.DecorationRecycler.LineDivinerRecycler;
import ru.medyannikov.homebank.Eventbus.ApartamentChangeEvent;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.IntentDialog.ApartamentIntent;
import ru.medyannikov.homebank.Model.Apartament;
import ru.medyannikov.homebank.R;

public class ApartamentsActivity extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager layoutManager;
    private SQLiteDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apartaments,container,false);
        dataSource = new SQLiteDataSource(view.getContext());
        dataSource.openConnection();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewApartament);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_apartaments);

        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Drawable raw = getContext().getDrawable(R.drawable.line_diviner);
        recyclerView.addItemDecoration(new LineDivinerRecycler(raw));

        updateApartaments();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ApartamentIntent.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onEvent(ApartamentChangeEvent event){
        updateApartaments();
    }

    private void updateApartaments() {
        recyclerView.setAdapter(new RecycleAdapterApartaments(dataSource.getApartaments()));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
